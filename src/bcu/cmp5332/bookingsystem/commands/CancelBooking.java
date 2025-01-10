package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import java.util.List;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {
	/**
	 * This program is for canceling a Booking and removing it from the flight booking system
	 * this changes attributes within flight booking system, customer, flight
	 	* @param args
	 */
    
	private final int customerID;
    private final int flightID;

    public CancelBooking(int customerID, int flightID) { //consideration if flight or customer hidden, will have to cancel booking??
    	/**
    	 * class recieves customerid and flightid 
    	 * from user input
    	 	* @param
    	 */
    	
    	this.customerID = customerID;
        this.flightID = flightID;
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	/**
    	 * Execute recieves flightBookingSystem which relates to the overall fbs object model
    	 * which is passed through main, thus changes apply to the object and thus throughout the system.
    	 	* @param
    	 */
    	
    	List<Booking> bookingList = flightBookingSystem.getBookingsB();
    	Customer customer = flightBookingSystem.getCustomerByID(customerID); 	
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
    	Double bookingFee = flight.getPrice() * 0.2;
    	/**
    	 * Calling relevant objects from flightBookingSystem
    	 */
       
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
        		temp.setFee(bookingFee);
        	}
        }
        /**
         * checks each booking in bookingList from flightBookingSystem object
         * gets the relevant data from booking object and if booking ids match inputed ids from user input
         * customerTemp and temp are variables set
         */
        
        //must keep any modifications outside of iteration - Java rules
        if (temp != null && customerTemp != null) { //cant cancel bookings if no modification to objects (if statement in for not flagged)
	        if (temp.getCustomerId() == this.customerID
	    			&& temp.getFlightId() == this.flightID) {
	        	customer.removeBooking(temp); 
	        	flight.removePassenger(customerTemp);
	        	flightBookingSystem.removeBooking(temp); //must use count, object in this form does not exist with date
	    		System.out.println("Customer ID " + customerTemp.getId() + " canceled booking for " + temp.getDetailsShort());
	    		
	    		try {
	    			FlightBookingSystemData.store(flightBookingSystem);
	    			System.out.println("Update successfully stored");
	    			} catch (IOException e) {
	    				throw new FlightBookingSystemException("Updates could not be stored.");
	    			}
	    		/**
	             * For a validated updating of data, the FlightBookingSystemData.store will store the current
	             * state of flightBookingSystem object into the .txt files. This happens at every sucessful
	             * update, thus even not typing exit, will still store the state of the program after the last
	             * update made and a print message displayed to user.
	             * If an update can't be made, a FlightBookingSystemException is thrown.
	             */
	        }
	        /**
	         * if temp and customerTemp are changed from matching ids and they are matching
	         * the customer, flight, and flightbookingsystem remove the booking from their relevant
	         * attributes within the object and give the user a validation print out
	         	* @return
	         */
        }
	else{    
        throw new FlightBookingSystemException("Booking could not be found to be cancelled.");
	}
        /**
         * when the temp and customerTemp are kept null, a flightBookingSystemException is thrown
         * and no changes to anything in the flightBookingSystem object is made
         * @return
         */
    }	
}
