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
	
	/*
    @Before //Panu
    public void setUp() throws FlightBookingSystemException, IOException {
        // Initialize the FlightBookingSystem
        FlightBookingSystem flightBookingSystemTest = FlightBookingSystemData.load();
        
        String line = "addcustomertest John-Doe 07482473829";
        Command command = CommandParser.parse(line);
        command.execute(flightBookingSystemTest); //this works not sure how to implement across all yet

        // Create a sample customer (Sherlock Holmes)
        Customer customerTest = new Customer(Integer.parseInt("1"), "John Doe", "123-456-7890", "JohnDoe@gmail.com");

        // Create a sample flight
        Flight flightTest = new Flight(Integer.parseInt("101"), "LNY 3568", "London", "New York", LocalDate.of(2024, 12, 31), Integer.parseInt("10"), Double.parseDouble("500.0"));

        // Add the sample customer and flight to the system
        //new AddCustomer("John Doe", "07482473829"); //this does not work as it does not recognise FBS - only seen as null
        //flightBookingSystemTest.addCustomer(customerTest);   (dosent work, dosent know where its coming from)
        flightBookingSystemTest.addFlight(flightTest);

        // Clear the files before each test to ensure a clean state
        clearFiles();
    }
    */

    @Before
    public void setUp() throws FlightBookingSystemException, IOException {
        flightBookingSystem = FlightBookingSystemData.load();
        customer = new Customer(1, "Abdel-Rahman Tawil", "07555555555", "ART@gmail.com");
        flight = new Flight(1, "LH2560", "Birmingham", "Munich", LocalDate.of(2022, 11, 25), 120, 70.99);
        flightBookingSystem.addCustomer(customer);
        flightBookingSystem.addFlight(flight);
        clearFiles();
    }

    private void clearFiles() throws IOException {
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