package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ListBookings implements Command {
	 @Override
	    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
	        List<Booking> bookings = flightBookingSystem.getBookings();
	        System.out.println("Bookings awaiting departure date ");
	        int count1 = 0;
	        for (Booking booking : bookings) {
	        	if (booking.getFlight().getDeparted() == false) {
	            System.out.println(booking.getDetailsShort());
	            count1++;
	        	}
	        }
	        System.out.println(count1 + " booking(s)");
	        System.out.println();
	        System.out.println("Bookings already departed ");
	        int count2 = 0;
	        for (Booking booking : bookings) {
	        	if (booking.getFlight().getDeparted() == true) {
	            System.out.println(booking.getDetailsShort());
	            count2++;
	        	}
	        }
	 }
}
