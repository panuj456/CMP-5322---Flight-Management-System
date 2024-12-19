package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ListBookings implements Command {
	 @Override
	    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
	        List<Booking> bookings = flightBookingSystem.getBookings();
	        for (Booking booking : bookings) {
	            System.out.println(booking.getDetailsShort());
	        }
	        System.out.println(bookings.size() + " booking(s)");
	    }
}
