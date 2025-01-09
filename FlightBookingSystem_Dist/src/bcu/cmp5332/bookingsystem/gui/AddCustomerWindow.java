package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * This class creates a window (GUI) for adding a new customer to the system.
 */
public class AddCustomerWindow extends JFrame implements ActionListener {

    // Reference to the main window to access the flight booking system
    private MainWindow mw;

    // Input fields for customer details
    private JTextField nameText = new JTextField();       // Field for entering the customers name
    private JTextField phoneText = new JTextField();      // Field for entering the customers phone number
    private JTextField addressText = new JTextField();    // Field for entering the customers address

    // Buttons for adding a customer or canceling the operation
    private JButton addBtn = new JButton("Add");          // Button to confirm adding the customer
    private JButton cancelBtn = new JButton("Cancel");    // Button to close the window

    /**
     * Constructor: Links this window to the main window and initializes the UI.
     */
    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw; // Save reference to the main window
        initialize(); // Set up the user interface
    }

    /**
     * Initializes the user interface (UI) for adding a new customer.
     */
    private void initialize() {
        try {
            // Set the look and feel of the window to match the operating system
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore exceptions for simplicity
        }

        setTitle("Add a New Customer"); // Title of the window

        setSize(350, 300); // Size of the window
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2)); // Grid layout with 4 rows and 2 columns

        // Add input fields with labels to the top panel
        topPanel.add(new JLabel("Name : "));       // Label for customers name
        topPanel.add(nameText);                   // Input field for customers name
        topPanel.add(new JLabel("Phone : "));     // Label for customer's phone number
        topPanel.add(phoneText);                  // Input field for customers phone number
        topPanel.add(new JLabel("Address : "));   // Label for customer's address
        topPanel.add(addressText);                // Input field for customers address

        // Bottom panel for action buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3)); // Single row with 3 columns
        bottomPanel.add(new JLabel("     "));        // Empty label for spacing
        bottomPanel.add(addBtn);                    // Add button
        bottomPanel.add(cancelBtn);                 // Cancel button

        // Link buttons to their action handlers
        addBtn.addActionListener(this);             // "Add" button listener
        cancelBtn.addActionListener(this);          // "Cancel" button listener

        // Add panels to the window
        this.getContentPane().add(topPanel, BorderLayout.CENTER); // Add top panel in the center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel at the bottom
        setLocationRelativeTo(mw); // Center the window relative to the main window

        setVisible(true); // Make the window visible
    }

    /**
     * Handles button clicks: either adds a customer or cancels the operation.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            // If "Add" button is clicked, call the method to add a customer
            addCustomer();
        } else if (ae.getSource() == cancelBtn) {
            // If "Cancel" button is clicked, close the window
            this.setVisible(false);
        }
    }

    /**
     * Collects input from the user, validates it, and adds a new customer to the system.
     */
    private void addCustomer() {
        try {
            // Get input values from the text fields
            String name = nameText.getText();          // Customers name
            String phone = phoneText.getText();        // Customers phone number
            String address = addressText.getText();    // Customers address

            // Create and execute the AddCustomer command
            Command addCustomer = new AddCustomer(name, phone, address);
            addCustomer.execute(mw.getFlightBookingSystem()); // Execute the command to add the customer

            // Refresh the customer list in the main window
            mw.displayCustomers();

            // Close this window after adding the customer
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            // If there is an error, show it in a dialog box
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
