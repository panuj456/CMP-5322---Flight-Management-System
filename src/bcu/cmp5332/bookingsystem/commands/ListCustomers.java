package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ListCustomers implements Command {

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		// TODO Auto-generated method stub
		 List<Customer> customers = flightBookingSystem.getCustomers();
		 int count = 0; //does not delete but hides
	        for (Customer customer : customers) {
	        	
	        	if (customer.getInView() == true) {//if hiding when deleted, why keep in system?? nonsensical seeming
	                System.out.println(customer.getDetailsShort());
	                count++;
	            	}
	        }
	        System.out.println(count + " customer(s)");
	        //System.out.println(customers.size() + " customer(s)"); //check API
	}

}