package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
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
        if (temp != null && customerTemp != null) { //cant cancel bookings if no modification to objects (if statement in for not flagged)
	        if (temp.getCustomerId() == this.customerID
	    			&& temp.getFlightId() == this.flightID) {
	        	customer.removeBooking(temp); 
	        	flight.removePassenger(customerTemp);
	        	flightBookingSystem.removeBooking(temp); //must use count, object in this form does not exist with date
	    		System.out.println("Customer ID " + customerTemp.getId() + " canceled booking for " + temp.getDetailsShort());
	    		//working, updates stored even when exit isnt typed - not sure how rollback should be implemented other than changes arent implemented if IOException is met
	    		try {
	    			FlightBookingSystemData.store(flightBookingSystem);
	    			System.out.println("Update successfully stored");
	    			} catch (IOException e) {
	    				throw new FlightBookingSystemException("Updates could not be stored.");
	    			}     
	        }
        }
	else{    
        throw new FlightBookingSystemException("Booking could not be found to be cancelled.");
	}
    }	
}
