package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

// Main GUI window class for the Flight Booking Management System
public class MainWindow extends JFrame implements ActionListener {

    // Menu bar and menu items for various options
    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    // Menu items for different features
    private JMenuItem adminExit;
    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    private JMenuItem flightsHide;
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;
    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;
    private JMenuItem custHide;
    private JMenuItem flightsViewPassengers;

    private FlightBookingSystem fbs; // Main data model for managing flights, bookings, and customers

    // Constructor: Sets up the main window and initializes the GUI components
    public MainWindow(FlightBookingSystem fbs) {
        initialize();  // Set up the GUI
        this.fbs = fbs; // Store the FlightBookingSystem instance
    }
    
    // Getter for the FlightBookingSystem instance
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    // Initializes the GUI components (menus, layout, etc.)
    private void initialize() {
        try {
            // Set the system look and feel to make the GUI look native to the OS
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore exceptions related to setting the look and feel
        }

        setTitle("Flight Booking Management System"); // Set the title of the window

        menuBar = new JMenuBar(); // Create the menu bar
        setJMenuBar(menuBar); // Add the menu bar to the window

        // Admin menu with an exit option
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);
        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this); // Add action listener for the exit button

        // Flights menu with options to view, add, delete, hide, and view passengers
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);
        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsHide = new JMenuItem("Hide");
        flightsViewPassengers = new JMenuItem("View Passengers");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        flightsMenu.add(flightsHide);
        flightsMenu.add(flightsViewPassengers);
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this); // Attach listeners to all menu items
        }

        // Bookings menu with options to issue, update, and cancel bookings
        bookingsMenu = new JMenu("Bookings");
        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        // Customers menu with options to view, add, delete, and hide customers
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);
        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");
        custHide = new JMenuItem("Hide");
        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        customersMenu.add(custHide);
        for (int i = 0; i < customersMenu.getItemCount(); i++) {
            customersMenu.getItem(i).addActionListener(this);
        }

        // Set the size of the window and its behavior
        setSize(800, 500); // Set the window size
        setVisible(true); // Make the window visible
        setAutoRequestFocus(true); // Auto-focus the window
        toFront(); // Bring the window to the front
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the program when the window is closed
    }

    // Main entry point of the program
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load(); // Load data into the system
        new MainWindow(fbs); // Create and show the main window
    }

    // Handle menu actions (what happens when a menu item is clicked)
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs); // Save the system data when exiting
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0); // Exit the program
        } else if (ae.getSource() == flightsView) {
            displayFlights(); // Show the list of flights
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this); // Open the window to add a new flight
        } else if (ae.getSource() == flightsDel) {
            // Handle delete flight (to be implemented)
        } else if (ae.getSource() == flightsHide) {
            hideFlight(); // Hide a flight
        } else if (ae.getSource() == bookingsIssue) {
            // Handle issue booking (to be implemented)
        } else if (ae.getSource() == bookingsCancel) {
            // Handle cancel booking (to be implemented)
        } else if (ae.getSource() == custView) {
            displayCustomers(); // Show the list of customers
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this); // Open the window to add a new customer
        } else if (ae.getSource() == custDel) {
            // Handle delete customer (to be implemented)
        } else if (ae.getSource() == custHide) {
            hideCustomer(); // Hide a customer
        } else if (ae.getSource() == flightsViewPassengers) {
            new ViewPassengersWindow(this); // Open the window to view passengers for a flight
        }
    }
    
    /**
     * Hides a customer from the list of displayed customer
     * 
     * 
     * this method prompts the user to input the customer id of the customer they want
     * if the number is valid then the attritbute within the customers will be set as not visible in the system
     * after that the list of flights is checked to only display the customer that are not hidden 
     * 
     * 
     * 
     * 
     * if user adds a non existent customer then the nothing will change 
     * 

     * @see Customer
     * @see FlightBookingSystem#getCustomers()
     * @see Customer#setInView(boolean)
     */
     
   


    	private void hideCustomer() {
    	    // Prompt the user to input the customer ID to hide
    	    String customerIdInput = JOptionPane.showInputDialog(this, "Enter the Customer ID to hide:", "Hide Customer", JOptionPane.PLAIN_MESSAGE);

    	    if (customerIdInput == null || customerIdInput.trim().isEmpty()) {
    	        // If no input is provided, do nothing
    	        return;
    	    }

    	    int customerId;
    	    try {
    	        customerId = Integer.parseInt(customerIdInput.trim()); // Parse input to integer
    	    } catch (NumberFormatException e) {
    	        JOptionPane.showMessageDialog(this, "Invalid Customer ID. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
    	        return;
    	    }

    	    List<Customer> customerList = fbs.getCustomers(); // Get the list of all customers
    	    for (Customer customer : customerList) {
    	        if (customer.getId() == customerId) {
    	            // Set the customer to not be in view
    	            customer.setInView(false);
    	            break;
    	        }
    	    }

    	    // Refresh the display to show only customers that are still in view
    	    displayCustomers();
    	}

    	
    	
    	
    	/**
     * Hides a flights from the list of displayed flights
     * 
     * 
     * this method prompts the user to input the flight id of the customer they want
     * if the number is valid then the attritbute within the flight will be set as not visible in the system
     * after that the list of flights is checked to only display the customer that are not hidden 
     * 
     * 
     * 
     * 
     * if user adds a non existent flight then the nothing will change 
     * 
     * @see Flight
     * @see FlightBookingSystem#getFlights()
     * @see Flight#setFlightInView(boolean)
     */
    		
	private void hideFlight() {
		// Prompt the user to input the flight number to hide
	    String flightNo = JOptionPane.showInputDialog(this, "Enter the Flight Number to hide:", "Hide Flight", JOptionPane.PLAIN_MESSAGE);

	    if (flightNo == null || flightNo.trim().isEmpty()) {
	        // If no input is provided, do nothing
	        return;
	    }

	    List<Flight> flightList = fbs.getFlights(); // Get the list of all flights
	    for (Flight flight : flightList) {
	        if (flight.getFlightNumber().equalsIgnoreCase(flightNo)) {
	            // Set the flight to not be in view
	            flight.setFlightInView(false);
	            break;
	        }
	    }

	    // Refresh the display to show only flights that are still in view
	    displayFlights();
	}
		
	
	
	
	
	
	

	// Display the list of customers in a table
    public void displayCustomers() {
        List<Customer> customerList = fbs.getCustomers(); // Get all customers
        List<Customer> temp = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getInView()) {
                temp.add(customer);
            }
        }
        String[] columns = {"Customer ID", "Name", "Email Address", "Booking Count"}; // Define column headers

        Object[][] data = new Object[temp.size()][4]; // Create a 2D array to hold customer data
        for (int i = 0; i < temp.size(); i++) {
            Customer customer = temp.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAddress(); // Email address
            data[i][3] = customer.getBookingSize(); // Number of bookings
        }

        JTable table = new JTable(data, columns); // Create a table to display the data
        this.getContentPane().removeAll(); // Clear the existing content in the frame
        this.getContentPane().add(new JScrollPane(table)); // Add the table to the frame
        this.revalidate(); // Refresh the UI to display the table
    }

    // Display the list of flights in a table
    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights(); // Get all flights
        List<Flight> temp = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (flight.getFlightInView()) {
                temp.add(flight);
            }
        }
        String[] columns = {"Flight No", "Origin", "Destination", "Departure Date"}; // Define column headers

        Object[][] data = new Object[temp.size()][4]; // Create a 2D array to hold flight data
        for (int i = 0; i < temp.size(); i++) {
            Flight flight = temp.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
        }

        JTable table = new JTable(data, columns); // Create a table to display the data
        this.getContentPane().removeAll(); // Clear the existing content in the frame
        this.getContentPane().add(new JScrollPane(table)); // Add the table to the frame
        this.revalidate(); // Refresh the UI to display the table
    }

 // Show the booking details for a specific customer
    public void displayBookingDetailsForCustomer(Customer customer) {
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "No customer selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Booking> bookings = customer.getBookings(); // Get all bookings for the customer
        String[] columns = {"Booking ID", "Flight Number", "Booking Date"}; // Define column headers
        Object[][] data = new Object[bookings.size()][3]; // Create a 2D array to hold booking data

        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            data[i][0] = booking.getBookingID();
            data[i][1] = booking.getFlight().getFlightNumber();
            data[i][2] = booking.getBookingDate();
        }

        JTable table = new JTable(data, columns); // Create a table to display the data
        JScrollPane scrollPane = new JScrollPane(table); // Add the table to a scroll pane

        // Create a popup window to display the bookings
        JFrame popup = new JFrame("Booking Details for Customer: " + customer.getName());
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the popup when the user clicks the close button
        popup.setSize(600, 400); // Set the size of the popup window
        popup.add(scrollPane); // Add the scroll pane (and table) to the popup
        popup.setLocationRelativeTo(this); // Center the popup relative to the main window
        popup.setVisible(true); // Make the popup visible
    }
}