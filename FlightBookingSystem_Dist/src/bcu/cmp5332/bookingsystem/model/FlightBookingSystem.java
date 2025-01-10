package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {

    private final LocalDate systemDate = LocalDate.parse("2024-11-11");

    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final List<Booking> bookingsList = new ArrayList<>(); 

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(new ArrayList<>(flights.values()));
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(new ArrayList<>(customers.values()));
    }

    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        Flight flight = flights.get(id);
        if (flight == null) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flight;
    }

    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customer;
    }

    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber())
                    && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with the same number and departure date in the system.");
            }
        }
        flights.put(flight.getId(), flight);
    }

    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate Customer ID.");
        }
        for (Customer existing : customers.values()) {
            if (existing.getName().equals(customer.getName()) && existing.getPhone().equals(customer.getPhone())) {
                throw new FlightBookingSystemException("There is a Customer with the same name and phone number in the system.");
            }
        }
        customers.put(customer.getId(), customer);
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookingsList);
    }

    public List<Booking> getBookingsB() {
        return bookingsList;
    }

    public void addBookingList(Booking booking) {
        bookingsList.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookingsList.remove(booking);
    }

    public Flight getFlightByNumber(String flightNumber) {
        for (Flight flight : flights.values()) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null; // Return null if flight is not found
    }
}
