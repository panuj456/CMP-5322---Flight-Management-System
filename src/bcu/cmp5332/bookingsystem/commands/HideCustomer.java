package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class HideCustomer implements Command{
	
	private int customerID;
	
	public HideCustomer(int customerID) {
		this.customerID = customerID;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		/**
    	 * Execute recieves flightBookingSystem which relates to the overall fbs object model
    	 * which is passed through main, thus changes apply to the object and thus throughout the system.
    	 	* @param
			* @throws FlightBookingSystemException if the updates to the flight booking system cannot be stored.
    	 */
		
		Customer hideCustomer = flightBookingSystem.getCustomerByID(customerID);
		hideCustomer.setInView(false); //set false for hidden id not problematic as it will set already false to false.
		
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
		
		
		//hide customer, but also remove as a passenger, remove as a booking?
	}

}
