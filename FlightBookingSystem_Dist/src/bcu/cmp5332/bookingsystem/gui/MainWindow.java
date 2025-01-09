package bcu.cmp5332.bookingsystem.gui;

// Importing necessary packages and classes
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
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;
    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;
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

        // Flights menu with options to view, add, delete, and view passengers
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);
        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsViewPassengers = new JMenuItem("View Passengers");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
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

        // Customers menu with options to view, add, and delete customers
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);
        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");
        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        // Set the size of the window and its behavior
        setSize(800, 500); // Set the window size
        setVisible(true); // Make the window visible
        setAutoRequestFocus(true); // Auto-focus the window
        toFront(); // Bring the window to the front
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the program when the window is closed
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Dispose of resources when closed
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
        } else if (ae.getSource() == flightsViewPassengers) {
            new ViewPassengersWindow(this); // Open the window to view passengers for a flight
        }
    }

    // Display the list of customers in a table
    public void displayCustomers() {
        List<Customer> customerList = fbs.getCustomers(); // Get all customers
        String[] columns = {"Customer ID", "Name", "Email Address", "Booking Count"}; // Define column headers

        Object[][] data = new Object[customerList.size()][4]; // Create a 2D array to hold customer data
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAddress(); // Email address
            data[i][3] = customer.getBookingSize(); // Number of bookings
        }

        JTable table = new JTable(data, columns); // Create a table to display the data
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection

        // Add a listener to handle row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Ensure the selection is finalized
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Customer selectedCustomer = customerList.get(selectedRow);
                        displayBookingDetailsForCustomer(selectedCustomer); // Show the bookings for the selected customer
                    }
                }
            }
        });

        this.getContentPane().removeAll(); // Clear the existing content in the frame
        this.getContentPane().add(new JScrollPane(table)); // Add the table to the frame
        this.revalidate(); // Refresh the UI to display the table
    }

    // Display the list of flights in a table
    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights(); // Get all flights
        String[] columns = {"Flight No", "Origin", "Destination", "Departure Date"}; // Define column headers

        Object[][] data = new Object[flightsList.size()][4]; // Create a 2D array to hold flight data
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
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
        JFrame popup = new JFrame("Booking Details for Customer: " + customer.getName()); // Create a popup window
        popup.setSize(500, 300); // Set the size of the popup
        popup.add(scrollPane); // Add the scroll pane to the popup
        popup.setVisible(true); // Show the popup
    }
}
