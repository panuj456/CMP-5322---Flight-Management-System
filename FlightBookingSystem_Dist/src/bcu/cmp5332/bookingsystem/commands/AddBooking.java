package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;

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
        //flightBookingSystem.addBookingList(stufffss);
    	
    	Booking newBooking = new Booking(customer, flight, bookingDate);
        
    	customer.addBooking(newBooking);
    	flightBookingSystem.addBookingList(newBooking);
        System.out.println("Customer ID " + customer.getId() + " added booking for " + newBooking.getDetailsShort());
    }
}