import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.commands.*;

public class JUnitTesting {
    private FlightBookingSystem flightBookingSystem;
    private Customer customer;
    private Flight flight;
    private Booking booking;

    @Before
    public void setUp() {
        // Initialize the FlightBookingSystem
        flightBookingSystem = new FlightBookingSystem();

        // Create a sample customer
        customer = new Customer(1, "John Doe", "123-456-7890");

        // Create a sample flight
        flight = new Flight(101, "London", "New York", LocalDate.of(2024, 12, 31), 10, 500.0);

        // Create a sample booking
        booking = new Booking(customer, flight, LocalDate.now());

        // Add the sample customer and flight to the system
        flightBookingSystem.addCustomer(customer);
        flightBookingSystem.addFlight(flight);
    }

    @Test
    public void testAddBooking() throws FlightBookingSystemException {
        // Create an AddBooking command
        AddBooking addBooking = new AddBooking(1, 101, LocalDate.now());

        // Execute the command to add the booking
        addBooking.execute(flightBookingSystem);

        // Verify that the customer has been added to the flight's passenger list
        assertTrue(flight.getPassengers().contains(customer));

        // Verify that the booking has been added to the customer's list of bookings
        assertTrue(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));

        // Verify that the booking has been added to the system's booking list
        assertEquals(1, flightBookingSystem.getBookings().size());
    }

    @Test
    public void testAddBookingWhenFlightIsFull() throws FlightBookingSystemException {
        // Add enough passengers to fill the flight
        for (int i = 2; i <= 10; i++) {
            flight.addPassenger(new Customer(i, "Customer " + i, "000-000-0000"));
        }

        // Create an AddBooking command
        AddBooking addBooking = new AddBooking(1, 101, LocalDate.now());

        // Execute the command to add the booking
        addBooking.execute(flightBookingSystem);

        // Verify that the customer has not been added to the flight's passenger list
        assertFalse(flight.getPassengers().contains(customer));

        // Verify that the booking has not been added to the customer's list of bookings
        assertFalse(customer.getBookings().stream().anyMatch(b -> b.getFlight().equals(flight)));

        // Verify that the booking has not been added to the system's booking list
        assertEquals(0, flightBookingSystem.getBookings().size());
    }

    @Test
    public void testAddCustomer() throws FlightBookingSystemException {
        // Create a new customer
        Customer newCustomer = new Customer(2, "Kostas Vlachos", "07596454545");

        // Create an AddCustomer command
        AddCustomer addCustomer = new AddCustomer("Kostas Vlachos", "07596454545");

        // Execute the command to add the customer
        addCustomer.execute(flightBookingSystem);

        // Verify that the customer has been added to the system
        assertEquals(newCustomer.getName(), flightBookingSystem.getCustomerByID(2).getName());
        assertEquals(newCustomer.getPhone(), flightBookingSystem.getCustomerByID(2).getPhone());
    }

    @Test
    public void testAddFlight() throws FlightBookingSystemException {
        // Create a new flight
        Flight newFlight = new Flight(102, "Paris", "Tokyo", LocalDate.of(2025, 1, 1), 20, 750.0);

        // Create an AddFlight command
        AddFlight addFlight = new AddFlight("Flight102", "Paris", "Tokyo", LocalDate.of(2025, 1, 1), 20, 750.0);

        // Execute the command to add the flight
        addFlight.execute(flightBookingSystem);

        // Verify that the flight has been added to the system
        assertEquals(newFlight.getFlightNumber(), flightBookingSystem.getFlightByID(102).getFlightNumber());
        assertEquals(newFlight.getOrigin(), flightBookingSystem.getFlightByID(102).getOrigin());
        assertEquals(newFlight.getDestination(), flightBookingSystem.getFlightByID(102).getDestination());
    }

    @Test
    public void testCancelBooking() throws FlightBookingSystemException {
        // Add the sample booking to the system
        flightBookingSystem.addBookingList(booking);

        // Create a CancelBooking command
        CancelBooking cancelBooking = new CancelBooking(1, 101);

        // Execute the command to cancel the booking
        cancelBooking.execute(flightBookingSystem);

        // Verify that the customer has been removed from the flight's passenger list
        assertFalse(flight.getPassengers().contains(customer));

        // Verify that the booking has been removed from the customer's list of bookings
        assertFalse(customer.getBookings().contains(booking));
    }

    @Test
    public void testAddMultipleCustomers() throws FlightBookingSystemException {
        // Sample customer data
        String[] customerData = {
            "1::Abdel-Rahman Tawil::07555555555::",
            "2::Kostas Vlachos::07596454545::",
            "3::Panu::07465388825::",
            "4::Mohammad Ali Ahmadzai::07538277735::",
            "5::Jephta Zekeng::07937462520::",
            "6::William Merrick::07923442318::",
            "7::Kacper Popis::07352644427::",
            "8::Angelo::078447545462::",
            "9::Summer::07563546254::"
        };

        // Add each customer to the system
        for (String data : customerData) {
            String[] parts = data.split("::");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String phone = parts[2];
            Customer customer = new Customer(id, name, phone); // Assuming email is not mandatory for this test
            AddCustomer addCustomer = new AddCustomer(name, phone);
            addCustomer.execute(flightBookingSystem);
        }

        // Verify that all customers have been added to the system
        assertEquals(10, flightBookingSystem.getCustomers().size());  // Includes the initial customer in setup
        assertNotNull(flightBookingSystem.getCustomerByID(1));
        assertNotNull(flightBookingSystem.getCustomerByID(2));
        assertNotNull(flightBookingSystem.getCustomerByID(3));
        assertNotNull(flightBookingSystem.getCustomerByID(4));
        assertNotNull(flightBookingSystem.getCustomerByID(5));
        assertNotNull(flightBookingSystem.getCustomerByID(6));
        assertNotNull(flightBookingSystem.getCustomerByID(7));
        assertNotNull(flightBookingSystem.getCustomerByID(8));
        assertNotNull(flightBookingSystem.getCustomerByID(9));
    }
}
