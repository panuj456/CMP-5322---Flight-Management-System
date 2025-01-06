package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.commands.UpdateBooking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class UpdateBooking implements Command {
    
	private final int customerID;
    private final int flightID;
    
    private final int updatedCustId;
    private final int updatedFlightId;
    private final LocalDate bookingDate;
    private final LocalDate updatedBookingDate; //not sure if required

    public UpdateBooking(int customerID, int flightID, LocalDate bookingDate, int updatedCustId,int updatedFlightId, LocalDate updatedBookingDate) { //might be bookingId in future with tree
    	this.customerID = customerID;
        this.flightID = flightID;
        this.bookingDate = bookingDate;
        
        this.updatedCustId = updatedCustId;
        this.updatedFlightId = updatedFlightId;
        this.updatedBookingDate = updatedBookingDate;
        
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	List<Booking> bookingsList = flightBookingSystem.getBookingsB();
    	int id = 0;
    	Booking tempBooking = new Booking(flightBookingSystem.getCustomerByID(customerID), flightBookingSystem.getFlightByID(flightID), bookingDate);
    	for (Booking booking : bookingsList) { //looking for specific booking object
			if (booking.getCustomerId()==customerID
	                 && booking.getFlightId()==flightID) {
				tempBooking = booking;
			}
			id++;	
		}
    	
    	Customer updatedCustomer = flightBookingSystem.getCustomerByID(updatedCustId); //finds new customer object
    	Customer oldCustomer = flightBookingSystem.getCustomerByID(customerID);
    	
    	Flight updatedFlight = flightBookingSystem.getFlightByID(updatedFlightId);
    	Flight oldFlight = flightBookingSystem.getFlightByID(flightID);
    	
    	if (updatedCustId!=customerID) { //if not working get rid of if statements
    		tempBooking.setCustomer(updatedCustomer);
    		oldCustomer.removeBooking(tempBooking);
    	}
    	else if (updatedFlightId!=flightID) {
    		tempBooking.setFlight(updatedFlight);
    		updatedFlight.addPassenger(updatedCustomer);
    		oldFlight.removePassenger(oldCustomer);
    	}
    	else if (updatedBookingDate!=bookingDate) {
    		tempBooking.setBookingDate(updatedBookingDate);
    	}
    	
        System.out.println("Customer ID " + oldCustomer.getId() + " updated to " + updatedCustomer.getId());
        System.out.println("Flight ID " + oldFlight.getId() + " updated to " + updatedFlight.getId());
        System.out.println(bookingsList.get(id).getDetailsShort());

    }
}