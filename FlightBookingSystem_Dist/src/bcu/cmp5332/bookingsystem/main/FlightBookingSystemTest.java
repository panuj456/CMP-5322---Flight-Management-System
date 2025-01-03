package bcu.cmp5332.bookingsystem.main;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.*;

public class FlightBookingSystemTest extends Main{
    public FlightBookingSystem flightBookingSystem;
    public Customer customer;
    public Flight flight;

    @Before
    public void setUp() throws FlightBookingSystemException, IOException {
        // Initialize the FlightBookingSystem
        FlightBookingSystem flightBookingSystemTest = FlightBookingSystemData.load();
        
        String line = "addcustomertest John-Doe 07482473829";
        Command command = CommandParser.parse(line);
        command.execute(flightBookingSystemTest); //this works not sure how to implement across all yet

        // Create a sample customer (Sherlock Holmes)
        Customer customerTest = new Customer(Integer.parseInt("1"), "John Doe", "123-456-7890");

        // Create a sample flight
        Flight flightTest = new Flight(Integer.parseInt("101"), "LNY 3568", "London", "New York", LocalDate.of(2024, 12, 31), Integer.parseInt("10"), Double.parseDouble("500.0"));

        // Add the sample customer and flight to the system
        new AddCustomer("John Doe", "07482473829"); //this does not work as it does not recognise FBS - only seen as null
        //flightBookingSystemTest.addCustomer(customerTest);
        flightBookingSystemTest.addFlight(flightTest);

        // Clear the files before each test to ensure a clean state
        clearFiles();
    }

    private void clearFiles() throws IOException {
        Files.write(Paths.get("customers.txt"), new byte[0]);
        Files.write(Paths.get("flights.txt"), new byte[0]);
        Files.write(Paths.get("bookings.txt"), new byte[0]);
    }

    @Test
    public void testAddCustomer() throws FlightBookingSystemException, IOException {
        AddCustomer addCustomer = new AddCustomer("John Watson", "07294633251");
        addCustomer.execute(flightBookingSystem);

        Customer retrievedCustomer = flightBookingSystem.getCustomerByID(1);
        assertEquals("John Watson", retrievedCustomer.getName());
        assertEquals("07294633251", retrievedCustomer.getPhone());

        List<String> customerLines = Files.readAllLines(Paths.get("customers.txt"));
        assertTrue(customerLines.stream().anyMatch(line -> line.contains("John Watson")));
    }

    @Test
    public void testAddFlight() throws FlightBookingSystemException, IOException {
        AddFlight addFlight = new AddFlight("PTY 350", "Paris", "Tokyo", LocalDate.of(2025, 1, 1), 20, 750.0);
        addFlight.execute(flightBookingSystem);

        Flight retrievedFlight = flightBookingSystem.getFlightByID(102);
        assertEquals(102, retrievedFlight.getFlightNumber());
        assertEquals("Paris", retrievedFlight.getOrigin());
        assertEquals("Tokyo", retrievedFlight.getDestination());

        List<String> flightLines = Files.readAllLines(Paths.get("flights.txt"));
        assertTrue(flightLines.stream().anyMatch(line -> line.contains("Paris")));
    }

    @Test
    public void testAddBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(1, 101, LocalDate.now());
        addBooking.execute(flightBookingSystem);
        //int addBookingFlightId = addBooking.g need the ID

        assertTrue(flight.getPassengers().contains(customer));
        assertTrue(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(1, flightBookingSystem.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertTrue(bookingLines.stream().anyMatch(line -> line.contains("1::101")));
    }

    @Test
    public void testCancelBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(1, 101, LocalDate.now());
        addBooking.execute(flightBookingSystem);

        CancelBooking cancelBooking = new CancelBooking(1, 101);
        cancelBooking.execute(flightBookingSystem);

        assertFalse(flight.getPassengers().contains(customer));
        assertFalse(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(0, flightBookingSystem.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertFalse(bookingLines.stream().anyMatch(line -> line.contains("1::101")));
    }
}