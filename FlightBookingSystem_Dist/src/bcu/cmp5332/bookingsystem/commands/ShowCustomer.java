package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ShowCustomer implements Command{
    
	private final int id;

	public ShowCustomer(int id) {
        this.id = id;
	}
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(this.id);
        if (customer.getInView() == true) {
        	System.out.println("Customer ID ("+this.id+") details "+ customer.getDetailsShort());
        }
    }
}
