package bcu.cmp5332.bookingsystem.main;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.*;

public class FlightBookingSystemTest {

    private static final String ORIGINAL_CUSTOMERS = "resources/data/original_customers.txt";
    private static final String ORIGINAL_FLIGHTS = "resources/data/original_flights.txt";
    private static final String ORIGINAL_BOOKINGS = "resources/data/original_bookings.txt";
    private static final String TEST_CUSTOMERS = "resources/data/customers.txt"; //had errors as path was not correct
    private static final String TEST_FLIGHTS = "resources/data/flights.txt";
    private static final String TEST_BOOKINGS = "resources/data/bookings.txt";

    public FlightBookingSystem flightBookingSystemTest;
    
    @BeforeClass //runs before any tests
    public static void saveFile() throws FlightBookingSystemException, IOException{
    	System.out.println("Path of target file: "
                + ORIGINAL_CUSTOMERS.toString());

    	System.out.println("Path of source file: "
                + TEST_CUSTOMERS.toString());
    	
    Files.copy(Paths.get(TEST_CUSTOMERS), Paths.get(ORIGINAL_CUSTOMERS), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(Paths.get(TEST_FLIGHTS), Paths.get(ORIGINAL_FLIGHTS), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(Paths.get(TEST_BOOKINGS), Paths.get(ORIGINAL_BOOKINGS), StandardCopyOption.REPLACE_EXISTING);
    }
    
    @Before //runs before each test
    public void setUp() throws FlightBookingSystemException, IOException {
        flightBookingSystemTest = new FlightBookingSystem();
        clearFiles();
        Customer customer = new Customer(2, "Kostas Vlachos", "07596454545", "kostas.vlachos@gmail.com", true);
        flightBookingSystemTest.addCustomer(customer);

        Flight flight = new Flight(2, "MX24124", "Manchester", "Lisbon", LocalDate.of(2024, 11, 24), 70, 50.99, true, false, LocalDate.of(2024, 11, 24));
        flightBookingSystemTest.addFlight(flight);

        FlightBookingSystemData.store(flightBookingSystemTest); // will store to flight.txt etc. but use of original files
    }

    private void clearFiles() throws IOException {
        Files.write(Paths.get(TEST_CUSTOMERS), new byte[0]);
        Files.write(Paths.get(TEST_FLIGHTS), new byte[0]);
        Files.write(Paths.get(TEST_BOOKINGS), new byte[0]);
    }


    @Test
    public void testAddCustomer() throws FlightBookingSystemException, IOException {
        Customer retrievedCustomer = flightBookingSystemTest.getCustomerByID(2);
        assertEquals("Kostas Vlachos", retrievedCustomer.getName());
        assertEquals("07596454545", retrievedCustomer.getPhone());

        List<String> customerLines = Files.readAllLines(Paths.get(TEST_CUSTOMERS));
        assertTrue(customerLines.stream().anyMatch(line -> line.contains("Kostas Vlachos")));
    }

    @Test
    public void testAddFlight() throws FlightBookingSystemException, IOException {
        Flight retrievedFlight = flightBookingSystemTest.getFlightByID(2);
        assertEquals("MX24124", retrievedFlight.getFlightNumber());
        assertEquals("Manchester", retrievedFlight.getOrigin());
        assertEquals("Lisbon", retrievedFlight.getDestination());

        List<String> flightLines = Files.readAllLines(Paths.get(TEST_FLIGHTS));
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

        List<String> bookingLines = Files.readAllLines(Paths.get(TEST_BOOKINGS));
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

        List<String> bookingLines = Files.readAllLines(Paths.get(TEST_BOOKINGS));
        assertFalse(bookingLines.stream().anyMatch(line -> line.contains("2::2")));
        
    }
    
	  @After //runs after each test
	  public void tearDown() throws IOException {
	       clearFiles();
	    }
    
    @AfterClass //runs after all tests complete
    public static void restoreOriginalData() throws IOException {
    	Files.copy(Paths.get(ORIGINAL_CUSTOMERS), Paths.get(TEST_CUSTOMERS), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get(ORIGINAL_FLIGHTS), Paths.get(TEST_FLIGHTS), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get(ORIGINAL_BOOKINGS), Paths.get(TEST_BOOKINGS), StandardCopyOption.REPLACE_EXISTING);//here
    }
    

}




