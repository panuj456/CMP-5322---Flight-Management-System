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

public class AddCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField(); // New field for phone
    private JTextField addressText = new JTextField(); // Renamed from emailText

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        setTitle("Add a New Customer");

        setSize(350, 300); // Updated size to accommodate extra fields
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2)); // Updated to 4 rows
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Phone : ")); // Label for phone
        topPanel.add(phoneText);
        topPanel.add(new JLabel("Address : ")); // Renamed to Address
        topPanel.add(addressText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addCustomer();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addCustomer() {
        try {
            String name = nameText.getText();
            String phone = phoneText.getText(); // Get phone input
            String address = addressText.getText(); // Get address input
            // Create and execute the AddCustomer Command
            Command addCustomer = new AddCustomer(name, phone, address);
            addCustomer.execute(mw.getFlightBookingSystem());
            // Refresh the customer list
            mw.displayCustomers();
            // Hide (close) the AddCustomerWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
