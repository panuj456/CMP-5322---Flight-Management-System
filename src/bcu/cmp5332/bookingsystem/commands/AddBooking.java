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
	/**
	 * This program is for adding a booking to flight booking system
	 * this changes attributes within flight booking system, customer, flight
	 	* @param args
	 */
	
	private final int customerID;
    private final int flightID;
    private final LocalDate bookingDate;

    public AddBooking(int customerID, int flightID, LocalDate bookingDate) {
    	/**
    	 * class recieves customer id, flight id and booking date 
    	 * from user input which is validated by FlightBookingSystemException
    	 	* @param
    	 */
        
    	this.customerID = customerID;
        this.flightID = flightID;
        this.bookingDate = bookingDate;
    	}

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	/**
    	 * Execute recieves flightBookingSystem which relates to the overall fbs object model
    	 * which is passed through main, thus changes apply to the object and thus throughout the system.
    	 	* @param
    	 */
    	
    	Customer customer = flightBookingSystem.getCustomerByID(customerID);
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
    	Booking newBooking = new Booking(customer, flight, bookingDate, flight.getDeparted(), flight.getPrice(), 0.0); //uses flight.getPrice() as price at current moment in time
    	/**
    	 * Above code initiates customer object and flight object from respective 
    	 * ids generated from constructor and creates a new booking object which will
    	 * be added to bookingsList in flightBookingSystem (as object is passed through execute).
    	 */
    	
    	Boolean duplicate = false;
    	for (Booking booking: flightBookingSystem.getBookingsB()) {
    		/**
    		 * for loop checking each booking in flightBookingSystem method getBookingsB
    		 * which returns a modifiable version of the bookingsList 
    		 * (bookingsList is a list of Booking objects) attribute in the flightBookingSystem object. 
    		 */
    		
    		if (booking.getCustomerId() == newBooking.getCustomerId() && booking.getFlightId() == newBooking.getFlightId()) {
    			duplicate = true;
    			/**
    			 * is only executed when ids from for loop current Booking and newBooking objects
    			 * are the same, thus a duplicate is present and temporary variable is set from false to true.
    			 */
    			//temp booking flight and customer here check passagner is not in flight
    		}
    	}
    	
    	List<Customer> temp = flight.getPassengers();
        if (temp.size() < flight.getCapacity() && duplicate == false) {
        	/**
        	 * checks before a new booking is made (and thus a new passenger is added to flight and
        	 * new booking is added to customer and new booking added to flightbookingsystem) there
        	 * must be enough capacity on the flight to add a new passenger. Also checks that a duplicate
        	 * booking hasnt already been made.
        	 */
        	
        	flight.addPassenger(customer); 
        	customer.addBooking(newBooking);
        	flightBookingSystem.addBookingList(newBooking);
            System.out.println("Customer ID " + customer.getId() + " added booking for " + newBooking.getDetailsShort());
            /**
             * Each initiated object from the overall flightBookingSystem object has its attributes updated
             * with the newBooking or relevant object and the console prints an output to the user to
             * validate this change.
             * @return
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
        
        else {
        	if (duplicate == true) {
        		System.out.println("Booking cannot be added, duplicate booking already made"); //change to throw exception
        	}
        	else {
        	System.out.println("Flight #" + flight.getId() + " capacity reached, passenger not added to flight, booking not added to system."); //change to throw exception
        	}
        	/**
        	 * when capacity and duplicate errors are found, else statement is executed
        	 * giving separate output to user explaining their error.
        	 	* @return
        	 */
        }
    }
}