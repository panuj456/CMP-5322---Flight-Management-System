package bcu.cmp5332.bookingsystem.main;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.*;

public class FlightBookingSystemTest {

    public FlightBookingSystem flightBookingSystemTest;

    @Before
    public void setUp() throws FlightBookingSystemException, IOException {
        flightBookingSystemTest = new FlightBookingSystem();
        clearFiles();

        Customer customer = new Customer(2, "Kostas Vlachos", "07596454545", "kostas.vlachos@gmail.com", true);
        flightBookingSystemTest.addCustomer(customer);

        Flight flight = new Flight(2, "MX24124", "Manchester", "Lisbon", LocalDate.of(2024, 11, 24), 70, 50.99, true);
        flightBookingSystemTest.addFlight(flight);

        FlightBookingSystemData.store(flightBookingSystemTest);
    }

    private void clearFiles() throws IOException {
        Files.write(Paths.get("customers.txt"), new byte[0]);
        Files.write(Paths.get("flights.txt"), new byte[0]);
        Files.write(Paths.get("bookings.txt"), new byte[0]);
    }

    @Test
    public void testAddCustomer() throws FlightBookingSystemException, IOException {
        Customer retrievedCustomer = flightBookingSystemTest.getCustomerByID(2);
        assertEquals("Kostas Vlachos", retrievedCustomer.getName());
        assertEquals("07596454545", retrievedCustomer.getPhone());

        List<String> customerLines = Files.readAllLines(Paths.get("customers.txt"));
        assertTrue(customerLines.stream().anyMatch(line -> line.contains("Kostas Vlachos")));
    }

    @Test
    public void testAddFlight() throws FlightBookingSystemException, IOException {
        Flight retrievedFlight = flightBookingSystemTest.getFlightByID(2);
        assertEquals("MX24124", retrievedFlight.getFlightNumber());
        assertEquals("Manchester", retrievedFlight.getOrigin());
        assertEquals("Lisbon", retrievedFlight.getDestination());

        List<String> flightLines = Files.readAllLines(Paths.get("flights.txt"));
        assertTrue(flightLines.stream().anyMatch(line -> line.contains("Manchester")));
    }

    @Test
    public void testAddBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(2, 2, LocalDate.of(2024, 12, 28));
        addBooking.execute(flightBookingSystemTest);

        Flight flight = flightBookingSystemTest.getFlightByID(2);
        Customer customer = flightBookingSystemTest.getCustomerByID(2);

        assertTrue(flight.getPassengers().stream().anyMatch(c -> c.getId() == 2));
        assertTrue(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(1, flightBookingSystemTest.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertTrue(bookingLines.stream().anyMatch(line -> line.contains("2::2")));
    }

    @Test
    public void testCancelBooking() throws FlightBookingSystemException, IOException {
        AddBooking addBooking = new AddBooking(2, 2, LocalDate.of(2024, 12, 28));
        addBooking.execute(flightBookingSystemTest);

        CancelBooking cancelBooking = new CancelBooking(2, 2);
        cancelBooking.execute(flightBookingSystemTest);

        Flight flight = flightBookingSystemTest.getFlightByID(2);
        Customer customer = flightBookingSystemTest.getCustomerByID(2);

        assertFalse(flight.getPassengers().stream().anyMatch(c -> c.getId() == 2));
        assertFalse(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));
        assertEquals(0, flightBookingSystemTest.getBookings().size());

        List<String> bookingLines = Files.readAllLines(Paths.get("bookings.txt"));
        assertFalse(bookingLines.stream().anyMatch(line -> line.contains("2::2")));
    }
}
