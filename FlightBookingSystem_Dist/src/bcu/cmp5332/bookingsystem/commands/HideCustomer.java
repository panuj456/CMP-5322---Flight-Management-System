package bcu.cmp5332.bookingsystem.commands;

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
		// TODO Auto-generated method stub
		Customer hideCustomer = flightBookingSystem.getCustomerByID(customerID);
		hideCustomer.setInView(false);
	}

}
