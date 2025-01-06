package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPassengersWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JButton searchBtn = new JButton("Search");
    private JButton closeBtn = new JButton("Close");
    private JTextArea resultArea = new JTextArea();

    public ViewPassengersWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("View Passengers by Flight");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Top panel for search
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Flight Number: "));
        topPanel.add(flightNoText);
        topPanel.add(searchBtn);
        topPanel.add(closeBtn);

        searchBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        // Center panel for results
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(mw);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchBtn) {
            viewPassengers();
        } else if (ae.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void viewPassengers() {
        String flightNo = flightNoText.getText().trim();
        FlightBookingSystem fbs = mw.getFlightBookingSystem(); 

        // Find the flight
        Flight flight = fbs.getFlightByNumber(flightNo);  

        if (flight == null) {
            resultArea.setText("No flight found with number: " + flightNo);
            return;
        }

        // Get passengers on the flight
        List<Customer> passengers = flight.getPassengers();
        if (passengers.isEmpty()) {
            resultArea.setText("No passengers found for flight: " + flightNo);
        } else {
            StringBuilder sb = new StringBuilder("Passengers for flight " + flightNo + ":\n");
            for (Customer customer : passengers) {
                sb.append("- ").append(customer.getName()).append(" (ID: ").append(customer.getId()).append(")\n");
            }
            resultArea.setText(sb.toString());
        }
    }
}
