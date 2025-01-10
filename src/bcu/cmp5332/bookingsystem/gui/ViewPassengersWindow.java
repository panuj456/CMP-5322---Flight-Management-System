package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class creates a window (GUI) to view the list of passengers for a specific flight.
 */
public class ViewPassengersWindow extends JFrame implements ActionListener {

    // Reference to the main window to access the flight booking system
    private MainWindow mw;

    // Input field for the flight number
    private JTextField flightNoText = new JTextField();

    // Buttons for searching and closing the window
    private JButton searchBtn = new JButton("Search"); // Button to search for passengers
    private JButton closeBtn = new JButton("Close"); // Button to close the window

    // Text area to display the search results
    private JTextArea resultArea = new JTextArea();

    /**
     * Constructor: Sets up the ViewPassengersWindow and connects it to the main window.
     */
    public ViewPassengersWindow(MainWindow mw) {
        this.mw = mw; // Save the reference to the main window
        initialize(); // Set up the user interface
    }

    /**
     * Initializes the user interface (UI) for the ViewPassengersWindow.
     */
    private void initialize() {
        setTitle("View Passengers by Flight"); // Title for the window
        setSize(400, 300); // Set the size of the window
        setLayout(new BorderLayout()); // Use BorderLayout for organizing components

        // Top panel for flight number input and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2)); // 2 rows, 2 columns layout

        // Add the flight number label and input field
        topPanel.add(new JLabel("Flight Number: "));
        topPanel.add(flightNoText);

        // Add the search and close buttons
        topPanel.add(searchBtn);
        topPanel.add(closeBtn);

        // Link buttons to their respective actions
        searchBtn.addActionListener(this); // Handle "Search" button clicks
        closeBtn.addActionListener(this); // Handle "Close" button clicks

        // Center panel for displaying results
        resultArea.setEditable(false); // Make the result area read-only
        JScrollPane scrollPane = new JScrollPane(resultArea); // Add scrolling capability

        // Add panels to the window
        this.getContentPane().add(topPanel, BorderLayout.NORTH); // Add top panel at the top
        this.getContentPane().add(scrollPane, BorderLayout.CENTER); // Add result area at the center

        setLocationRelativeTo(mw); // Center this window relative to the main window
        setVisible(true); // Make the window visible
    }

    /**
     * Handles actions when buttons are clicked.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchBtn) {
            // If the "Search" button is clicked, search for passengers
            viewPassengers();
        } else if (ae.getSource() == closeBtn) {
            // If the "Close" button is clicked, close this window
            this.setVisible(false);
        }
    }

    /**
     * Searches for passengers on the flight with the given flight number and displays the results.
     */
    private void viewPassengers() {
        // Get the flight number entered by the user
        String flightNo = flightNoText.getText().trim();

        // Get the flight booking system from the main window
        FlightBookingSystem fbs = mw.getFlightBookingSystem();

        // Find the flight based on the flight number
        Flight flight = fbs.getFlightByNumber(flightNo);

        // If no flight is found, display an error message
        if (flight == null) {
            resultArea.setText("No flight found with number: " + flightNo);
            return;
        }

        // Get the list of passengers on the flight
        List<Customer> passengers = flight.getPassengers();

        // Check if there are any passengers
        if (passengers.isEmpty()) {
            // If no passengers are found, display a message
            resultArea.setText("No passengers found for flight: " + flightNo);
        } else {
            // If passengers are found, format and display the list
            StringBuilder sb = new StringBuilder("Passengers for flight " + flightNo + ":\n");
            for (Customer customer : passengers) {
                // Add each passengers name and ID to the list
                sb.append("- ").append(customer.getName()).append(" (ID: ").append(customer.getId()).append(")\n");
            }
            resultArea.setText(sb.toString()); // Show the formatted list in the result area
        }
    }
}
