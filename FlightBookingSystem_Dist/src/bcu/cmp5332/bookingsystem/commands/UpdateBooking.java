package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.commands.UpdateBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class UpdateBooking implements Command {
	/**
	 * This program is for updates a Booking and changing its attributes in flight booking system
	 * this changes a booking and interacts with fbs, customer and flight
	 	* @param args
	 */
    
	private final int customerID;
    private final int flightID;
    
    private final int updatedCustId;
    private final int updatedFlightId;
    private final LocalDate bookingDate;
    private final LocalDate updatedBookingDate;

    public UpdateBooking(int customerID, int flightID, LocalDate bookingDate, int updatedCustId,int updatedFlightId, LocalDate updatedBookingDate) { //might be bookingId in future with tree
    	/**
    	 * class recieves an original and updated form of all three booking object
    	 * attributes from user input
    	 	* @param
    	 */
    	
    	this.customerID = customerID;
        this.flightID = flightID;
        this.bookingDate = bookingDate;
        
        this.updatedCustId = updatedCustId;
        this.updatedFlightId = updatedFlightId;
        this.updatedBookingDate = updatedBookingDate;
        
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	/**
    	 * Execute recieves flightBookingSystem which relates to the overall fbs object model
    	 * which is passed through main, thus changes apply to the object and thus throughout the system.
    	 	* @param
    	 */
    	
    	List<Booking> bookingsList = flightBookingSystem.getBookingsB();
    	Booking tempBooking = new Booking(flightBookingSystem.getCustomerByID(customerID), flightBookingSystem.getFlightByID(flightID), bookingDate, flightBookingSystem.getFlightByID(flightID).getDeparted(),flightBookingSystem.getFlightByID(flightID).getPrice(), 0.0);
    	for (Booking booking : bookingsList) { //looking for specific booking object
			if (booking.getCustomerId()==customerID
	                 && booking.getFlightId()==flightID) {
				tempBooking = booking;
				Double bookingFee = tempBooking.getPrice() * 0.2;
				tempBooking.setFee(bookingFee);
			}
		}
    	/**
		 * In bookingsList refering to flightBookingSystem bookingsList, each item in the list is checked
		 * for matching ids, when matched tempBooking which initially has new Booking object based on old
		 * non updated attributes is changed to matching id booking.
		 */
    	
    	Customer updatedCustomer = flightBookingSystem.getCustomerByID(updatedCustId); //finds new customer object
    	Customer oldCustomer = flightBookingSystem.getCustomerByID(customerID);
    	
    	Flight updatedFlight = flightBookingSystem.getFlightByID(updatedFlightId);
    	Flight oldFlight = flightBookingSystem.getFlightByID(flightID);
    	/**
		 * updated ID objects and old ID objects are taken from flightBookingSystem object
		 */
    	
    	if (updatedCustId!=customerID) {
    		tempBooking.setCustomer(updatedCustomer);
    		oldCustomer.removeBooking(tempBooking);
    		System.out.println("Customer ID " + oldCustomer.getId() + " updated to " + updatedCustomer.getId());
    	}
    	else if (updatedFlightId!=flightID) {
    		tempBooking.setFlight(updatedFlight);
    		updatedFlight.addPassenger(updatedCustomer);
    		oldFlight.removePassenger(oldCustomer);
    		tempBooking.setCompleted(tempBooking.getFlight().getDepartureDate().isAfter(LocalDate.now()));
    	}
    	else if (updatedBookingDate!=bookingDate) {
    		tempBooking.setBookingDate(updatedBookingDate);
    		System.out.println("Flight ID " + oldFlight.getId() + " updated to " + updatedFlight.getId());
    	}
    	/**
		 * if updated versions do not match original versions of attributes, 
		 * all relevant changes are made to objects and flightBookingSystem
		 */
    	
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
}