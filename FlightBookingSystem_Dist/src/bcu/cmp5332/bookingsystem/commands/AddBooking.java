package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddBooking implements Command {
    
	private final int customerID;
    private final int flightID;
    private final LocalDate bookingDate;

    public AddBooking(int customerID, int flightID, LocalDate bookingDate) {
        this.customerID = customerID;
        this.flightID = flightID;
        this.bookingDate = bookingDate;
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	
    	Customer customer = flightBookingSystem.getCustomerByID(customerID);
    	
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
    	
    	Booking newBooking = new Booking(customer, flight, bookingDate);
    	
    	List<Customer> temp = flight.getPassengers();
        if (temp.size() < flight.getCapacity()) {
        	flight.addPassenger(customer); 
        	customer.addBooking(newBooking);
        	flightBookingSystem.addBookingList(newBooking);
            System.out.println("Customer ID " + customer.getId() + " added booking for " + newBooking.getDetailsShort());
        }
        else {
        	System.out.println("Flight #" + flight.getId() + " capacity reached, passenger not addedd to flight, booking not added to system.");
        }
    }
}