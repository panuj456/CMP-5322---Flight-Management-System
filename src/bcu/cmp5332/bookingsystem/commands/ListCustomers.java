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
	        for (Customer customer : customers) {
	            System.out.println(customer.getDetailsShort());
	        }
	        System.out.println(customers.size() + " customer(s)");
	}

}
