package bcu.cmp5332.bookingsystem.main;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.*;

public class FlightBookingSystemTest {

    public FlightBookingSystem flightBookingSystem;
    public Customer customer;
    public Flight flight;
	
    
    @Before //Panu
    public void setUp() throws FlightBookingSystemException, IOException {
        // Initialize the FlightBookingSystem
        FlightBookingSystem flightBookingSystemTest = FlightBookingSystemData.load();
        
        String line = "addcustomertest John-Doe 07482473829";
        Command command = CommandParser.parse(line);
        command.execute(flightBookingSystemTest); //this works not sure how to implement across all yet

        // Create a sample customer (Sherlock Holmes)
        Customer customerTest = new Customer(Integer.parseInt("10"), "Sherlock Holmes", "07890123456", "sherlock.holmes@spectaclestreet.com");


        // Create a sample flight
        Flight flightTest = new Flight(Integer.parseInt("101"), "LNY 3568", "London", "New York", LocalDate.of(2024, 12, 31), Integer.parseInt("10"), Double.parseDouble("500.0"));

		String customerCommand = "addcustomertest Sherlock-Holmes 07890123456";
		Command addCustomerCommand = CommandParser.parse(customerCommand);
		addCustomerCommand.execute(flightBookingSystemTest);
		
		String flightCommand = "addflight LNY3568 London New York 2024-12-31 10 500.0";
		Command addFlightCommand = CommandParser.parse(flightCommand);
		addFlightCommand.execute(flightBookingSystemTest);

        // Clear the files before each test to ensure a clean state
        clearFiles();
    }

    private void clearFiles() throws IOException {
        // Clear the customer, flight, and booking files
        Files.write(Paths.get("customers.txt"), new byte[0]);
        Files.write(Paths.get("flights.txt"), new byte[0]);
        Files.write(Paths.get("bookings.txt"), new byte[0]);
    }

    @Test
    public void testAddCustomer() throws FlightBookingSystemException, IOException {
        AddCustomer addCustomer = new AddCustomer("Kostas Vlachos", "07596454545", "KV@gmail.com");
        addCustomer.execute(flightBookingSystem);

        Customer retrievedCustomer = flightBookingSystem.getCustomerByID(2);
        assertEquals("Kostas Vlachos", retrievedCustomer.getName());
        assertEquals("07596454545", retrievedCustomer.getPhone());

        List<String> customerLines = Files.readAllLines(Paths.get("customers.txt"));
        assertTrue(customerLines.stream().anyMatch(line -> line.contains("Kostas Vlachos")));
    }

    @Test
    public void testAddFlight() throws FlightBookingSystemException, IOException {
        AddFlight addFlight = new AddFlight("MX24124", "Manchester", "Lisbon", LocalDate.of(2024, 11, 24), 70, 50.99);
        addFlight.execute(flightBookingSystem);

        Flight retrievedFlight = flightBookingSystem.getFlightByID(2);
        assertEquals(2, retrievedFlight.getFlightNumber());
        assertEquals("Manchester", retrievedFlight.getOrigin());
        assertEquals("Lisbon", retrievedFlight.getDestination());

        List<String> flightLines = Files.readAllLines(Paths.get("flights.txt"));
        assertTrue(flightLines.stream().anyMatch(line -> line.contains("Manchester")));
    }

    @Test
    public void testAddBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(9, 1, LocalDate.of(2024, 12, 28));
        addBooking.execute(flightBookingSystem);

        assertTrue(flight.getPassengers().stream().anyMatch(c -> c.getId() == 9));
        assertTrue(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(1, flightBookingSystem.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertTrue(bookingLines.stream().anyMatch(line -> line.contains("9::1")));
    }

    @Test
    public void testCancelBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(9, 1, LocalDate.of(2024, 12, 28));
        addBooking.execute(flightBookingSystem);

        CancelBooking cancelBooking = new CancelBooking(9, 1);
        cancelBooking.execute(flightBookingSystem);

        assertFalse(flight.getPassengers().stream().anyMatch(c -> c.getId() == 9));
        assertFalse(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(0, flightBookingSystem.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertFalse(bookingLines.stream().anyMatch(line -> line.contains("9::1")));
    }
}
