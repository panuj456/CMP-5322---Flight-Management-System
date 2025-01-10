package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {
    
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private Boolean completed;
    private Double fee;
    private Double price;
    
    //tried giving own booking id but AddBooking implementation at this stage would suffer
    public Booking(Customer customer, Flight flight, LocalDate bookingDate, Boolean completed, Double price, Double fee) {
        // TODO: implementation here
    	this.customer = customer;
    	this.flight = flight;
    	this.bookingDate = bookingDate;
    	this.completed = completed;
        this.fee = fee;
    	this.price = price;
    }

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getDetailsShort() {
        return "Customer: " + this.customer.getDetailsShort() + " Flight:   " + this.flight.getDetailsShort() + " Booking Date  " + this.bookingDate + " Completed - " + completed + " Re-Boooking Fee - " + this.fee + " Total Price - " + (this.flight.getPrice() + this.fee);
    }
    public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
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
	public Object getBookingID() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
