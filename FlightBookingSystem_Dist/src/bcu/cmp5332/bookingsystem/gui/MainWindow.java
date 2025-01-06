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
import java.util.List;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

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

    private FlightBookingSystem fbs;

    public MainWindow(FlightBookingSystem fbs) {
        initialize();
        this.fbs = fbs;
    }
    
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle exceptions
        }

        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Admin menu
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);
        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // Flights menu
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
            flightsMenu.getItem(i).addActionListener(this);
        }

        // Bookings menu
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

        // Customers menu
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

        setSize(800, 500);
        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }	

    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);			
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
        } else if (ae.getSource() == flightsDel) {
            // Handle delete flight
        } else if (ae.getSource() == bookingsIssue) {
            // Handle booking issue
        } else if (ae.getSource() == bookingsCancel) {
            // Handle booking cancel
        } else if (ae.getSource() == custView) {
            displayCustomers();
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
        } else if (ae.getSource() == custDel) {
            // Handle delete customer
        } else if (ae.getSource() == flightsViewPassengers) {
            new ViewPassengersWindow(this);
        }
    }

    public void displayCustomers() {
        List<Customer> customerList = fbs.getCustomers();
        String[] columns = {"Customer ID", "Name", "Email Address", "Booking Count"};

        Object[][] data = new Object[customerList.size()][4];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAddress(); // email address
            data[i][3] = customer.getBookingSize();
        }

        JTable table = new JTable(data, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection

        // Add a listener to handle row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Check if selection is completed
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Customer selectedCustomer = customerList.get(selectedRow);
                        displayBookingDetailsForCustomer(selectedCustomer);
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));  // Add the table to the frame
        this.revalidate();  // Revalidate the UI to show the new content
    }

    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = {"Flight No", "Origin", "Destination", "Departure Date"};

        Object[][] data = new Object[flightsList.size()][4];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public void displayBookingDetailsForCustomer(Customer customer) {
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "No customer selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Booking> bookings = customer.getBookings();
        String[] columns = {"Booking ID", "Flight Number", "Booking Date"};
        Object[][] data = new Object[bookings.size()][3];

        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            data[i][0] = booking.getBookingID();
            data[i][1] = booking.getFlight().getFlightNumber();
            data[i][2] = booking.getBookingDate();
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame popup = new JFrame("Booking Details for Customer: " + customer.getName());
        popup.setSize(500, 300);
        popup.add(scrollPane);
        popup.setVisible(true);
    }
}
