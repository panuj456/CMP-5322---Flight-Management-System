package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    
    //tried giving own booking id but AddBooking implementation at this stage would suffer
    public Booking(Customer customer, Flight flight, LocalDate bookingDate) {
        // TODO: implementation here
    	this.customer = customer;
    	this.flight = flight;
    	this.bookingDate = bookingDate;
        
    }
    public String getDetailsShort() {
        return "Customer: " + this.customer.getDetailsShort() + " Flight:   " + this.flight.getDetailsShort() + " Booking Date  " + this.bookingDate;
    }
    // TODO: implementation of Getter and Setter methods (Done)
	public Customer getCustomer() {
		return customer;
	}
	public int getCustomerId() {
		return customer.getId();
	}
	public int getFlightId() {
		return flight.getId();
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
    
    
}
