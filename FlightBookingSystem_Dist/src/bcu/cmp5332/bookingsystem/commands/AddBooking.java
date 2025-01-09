package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    	
    	Boolean duplicate = false;
    	for (Booking booking: flightBookingSystem.getBookingsB()) {
    		if (booking.getCustomerId() == newBooking.getCustomerId() && booking.getFlightId() == newBooking.getFlightId()) {
    			duplicate = true;
    			//temp booking flight and customer here check passagner is not in flight
    		}
    	}
    	
    	List<Customer> temp = flight.getPassengers();
        if (temp.size() < flight.getCapacity() && duplicate == false) { //check
        	flight.addPassenger(customer); 
        	customer.addBooking(newBooking);
        	flightBookingSystem.addBookingList(newBooking);
            System.out.println("Customer ID " + customer.getId() + " added booking for " + newBooking.getDetailsShort());
            
    		//working, updates stored even when exit isnt typed - not sure how rollback should be implemented other than changes arent implemented if IOException is met
            try {
				FlightBookingSystemData.store(flightBookingSystem);
				System.out.println("Update successfully stored");
				} catch (IOException e) {
					throw new FlightBookingSystemException("Updates could not be stored.");
				}
            }
        else {
        	if (duplicate == true) {
        		System.out.println("Booking cannot be added, duplicate booking already made"); //change to throw exception
        	}
        	else {
        	System.out.println("Flight #" + flight.getId() + " capacity reached, passenger not added to flight, booking not added to system."); //change to throw exception
        	}
        }
    }
}