package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2024-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final ArrayList<Booking> bookingsList = new ArrayList<>(); //of objects

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }

    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        // TODO: implementation here (complete)
    	if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customers.get(id);
    }

    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        // TODO: implementation here
    	 if (customers.containsKey(customer.getId())) {
             throw new IllegalArgumentException("Duplicate Customer ID.");
         }
         for (Customer existing : customers.values()) {
             if (existing.getName().equals(customer.getName()) 
                 && existing.getPhone().equals(customer.getPhone())) { 
                 throw new FlightBookingSystemException("There is a Customer with same "
                         + "name and phone number in the system");
             }
         }
         customers.put(customer.getId(), customer);
    }
   
    // needed in ListBookings(get all bookings in the system? Need getBookingList and addBookingList?
	public List<Booking> getBookings() {
		return Collections.unmodifiableList(bookingsList);       
	}
	
	public List<Booking> getBookingsB() {
		return bookingsList;       
	}
	
	public void addBookingList(Booking booking) throws FlightBookingSystemException {
        // TODO: implementation here
		//validation here
    	bookingsList.add(booking);
    }
	
	public void removeBooking(Booking booking) throws FlightBookingSystemException {
    	
		bookingsList.remove(booking);
    }

	}

