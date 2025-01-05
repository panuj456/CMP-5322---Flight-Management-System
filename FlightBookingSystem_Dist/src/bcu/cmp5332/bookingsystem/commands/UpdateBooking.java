package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class UpdateBooking implements Command {
    
	private final int customerID;
    private final int flightID;
    
    private final int updatedCustId;
    private final int updatedFlightId;
   // private final LocalDate updatedBookingDate; //not sure if required

    public UpdateBooking(int customerID, int flightID, int updatedCustId,int updatedFlightId) { //might be bookingId in future with tree
    	this.customerID = customerID;
        this.flightID = flightID;
        
        this.updatedCustId = updatedCustId;
        this.updatedFlightId = updatedFlightId;
        //this.updatedBookingDate = updatedBookingDate;
        
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	List<Booking> bookingsList = flightBookingSystem.getBookingsB();
    	int id = 0;
    	Booking tempBooking;
    	for (Booking booking : bookingsList) {
			if (booking.getCustomerId()==customerID
	                 && booking.getFlightId()==flightID) {
				tempBooking = booking;
			}
			id++;	
		}
    	
    	Customer updatedCustomer = flightBookingSystem.getCustomerByID(updatedCustId);
    	Customer oldCustomer = flightBookingSystem.getCustomerByID(customerID);
    	
    	Flight updatedFlight = flightBookingSystem.getFlightByID(updatedFlightId);
    	Flight oldFlight = flightBookingSystem.getFlightByID(flightID);
    	
    	if (updatedCustId!=customerID) {
    		tempBooking.setCustomer(updatedCustomer);
    		oldCustomer.removeBooking(tempBooking);
    	}
    	else if (updatedFlightId!=flightID) {
    		tempBooking.setFlight(updatedFlight);
    		updatedFlight.addPassenger(updatedCustomer);
    		oldFlight.removePassenger(oldCustomer);
    	}
    	
    		/*
        	flight.addPassenger(customer); 
        	customer.addBooking(newBooking);
        	flightBookingSystem.addBookingList(newBooking);
        	flight.removePassenger(customer)
        	*/
            System.out.println("Customer ID " + customer.getId() + " added booking for " + newBooking.getDetailsShort());
        
    }
}