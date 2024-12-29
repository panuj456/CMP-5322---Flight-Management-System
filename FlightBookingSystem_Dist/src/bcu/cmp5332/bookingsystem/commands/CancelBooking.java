package bcu.cmp5332.bookingsystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {
    
	private final int customerID;
    private final int flightID;

    public CancelBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	
    	List<Booking> bookingList = flightBookingSystem.getBookingsB();
    	Customer customer = flightBookingSystem.getCustomerByID(customerID);
    	
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
       
    	Customer customerTemp = null;
    	Booking temp = null;
        for (Booking booking : bookingList) {
        	Customer bookingCustomer = booking.getCustomer();
        	int bookingCustomerID = bookingCustomer.getId();
        	Flight bookingFlight = booking.getFlight();
        	int bookingFlightID = bookingFlight.getId();
        	if (bookingCustomerID == this.customerID
        			&& bookingFlightID == this.flightID) {
        		customerTemp = booking.getCustomer();
        		temp = booking;
        	}
        }
        //must keep any modifications outside of iteration - Java rules
        customer.removeBooking(temp); 
        flight.removePassenger(customerTemp);
		flightBookingSystem.removeBooking(temp); //must use count, object in this form does not exist with date
		System.out.println("Customer ID " + customerTemp.getId() + " canceled booking for " + temp.getDetailsShort());
    }
}
