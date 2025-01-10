package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * This class creates a window (GUI) where the user can add a new flight.
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    // Reference to the main window to access and update the flight booking system
    private MainWindow mw;

    // Text fields for user input
    private JTextField flightNoText = new JTextField(); // Field to enter flight number
    private JTextField originText = new JTextField(); // Field to enter flight origin
    private JTextField destinationText = new JTextField(); // Field to enter flight destination
    private JTextField depDateText = new JTextField(); // Field to enter departure date (YYYY-MM-DD)
    private JTextField capacityText = new JTextField(); // Field to enter flight capacity
    private JTextField priceText = new JTextField(); // Field to enter ticket price

    // Buttons for user actions
    private JButton addBtn = new JButton("Add"); // Button to add the flight
    private JButton cancelBtn = new JButton("Cancel"); // Button to cancel and close the window

    /**
     * Constructor: Sets up the AddFlightWindow and connects it to the main window.
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw; // Save the reference to the main window
        initialize(); // Set up the user interface
    }

    /**
     * Initializes the user interface (UI) for the AddFlightWindow.
     */
    private void initialize() {
        try {
            // Makes the UI look consistent with the system's style
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Print errors (if any)
        }

        setTitle("Add a New Flight"); // Title for the window

        setSize(400, 300); // Set the size of the window

        // Create a panel for input fields (text boxes) and labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); // Arrange inputs in 6 rows and 2 columns

        // Add labels and input fields to the panel
        topPanel.add(new JLabel("Flight No : "));
        topPanel.add(flightNoText);
        topPanel.add(new JLabel("Origin : "));
        topPanel.add(originText);
        topPanel.add(new JLabel("Destination : "));
        topPanel.add(destinationText);
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD) : "));
        topPanel.add(depDateText);
        topPanel.add(new JLabel("Capacity : "));
        topPanel.add(capacityText);
        topPanel.add(new JLabel("Price : "));
        topPanel.add(priceText);

        // Create a panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3)); // Arrange buttons in 1 row and 3 columns

        // Add buttons to the panel (with spacing)
        bottomPanel.add(new JLabel("     ")); // Add spacing
        bottomPanel.add(addBtn); // Add button
        bottomPanel.add(cancelBtn); // Cancel button

        // Link buttons to actions
        addBtn.addActionListener(this); // Add button action
        cancelBtn.addActionListener(this); // Cancel button action

        // Add the panels to the window
        this.getContentPane().add(topPanel, BorderLayout.CENTER); // Input fields in the center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); // Buttons at the bottom

        setLocationRelativeTo(mw); // Center this window relative to the main window
        setVisible(true); // Make the window visible
    }

    /**
     * Handles what happens when a button is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            // If the "Add" button is clicked, try to add a new flight
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            // If the "Cancel" button is clicked, close this window
            this.setVisible(false);
        }
    }

    /**
     * Adds a flight based on the user's input.
     */
    private void addBook() {
        try {
            // Get the data entered by the user
            String flightNumber = flightNoText.getText(); // Flight number
            String origin = originText.getText(); // Origin
            String destination = destinationText.getText(); // Destination
            LocalDate departureDate = null; // Variable to store departure date

            // Parse the departure date (and check for errors in the format)
            try {
                departureDate = LocalDate.parse(depDateText.getText());
            } catch (DateTimeParseException dtpe) {
                // Show an error if the date format is wrong
                throw new FlightBookingSystemException("Date must be in YYYY-MM-DD format");
            }

            int capacity; // Variable to store capacity
            double price; // Variable to store ticket price

            // Try to convert capacity and price to numbers (and check for errors)
            try {
                capacity = Integer.parseInt(capacityText.getText()); // Convert capacity to an integer
                price = Double.parseDouble(priceText.getText()); // Convert price to a double
            } catch (NumberFormatException nfe) {
                // Show an error if capacity or price is invalid
                throw new FlightBookingSystemException("Capacity must be an integer and price must be a number.");
            }

            // Create an AddFlight command with the data entered by the user
            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, capacity, price);

            // Execute the command to add the flight to the system
            addFlight.execute(mw.getFlightBookingSystem());

            // Update the main window to show the new flight
            mw.displayFlights();

            // Close the AddFlightWindow after the flight is added
            this.setVisible(false);

        } catch (FlightBookingSystemException ex) {
            // If there's an error, show a message box with the error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
