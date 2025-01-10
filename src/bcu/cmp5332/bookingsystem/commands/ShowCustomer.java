package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
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
        //if (customer.getInView() == true) {
        	System.out.println("Customer ID ("+this.id+") details "+ customer.getDetailsShort());
        	System.out.println("Bookings made by customer awaiting departure: ");
        	for (Booking custBooking : customer.getBookings()) { //lists all bookings a customer has made
        		if (custBooking.getCompleted() == false) {
        		System.out.println(custBooking.getDetailsShort()); 
        		}
        	//}
        }
        	System.out.println();
        	System.out.println("Bookings made by customer already departed: ");
        	for (Booking custBooking : customer.getBookings()) { //lists all bookings a customer has made
        		if (custBooking.getCompleted() == true) {
        		System.out.println(custBooking.getDetailsShort()); 
        		}
        }      	
    }
}
