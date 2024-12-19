package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private final ArrayList<Booking> bookings; //bookingID seen in menu, given its own parameter now
    
    // TODO: implement constructor here
	public Customer(int id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		bookings = new ArrayList<>(); //of objects
		}
	
    public String getDetailsShort() {
        return "Customer ID " + this.id + " Name  " + this.name + " Phone Number  " + this.phone;
    }
    
    //add booking in customers stated in assignment brief and in skeleton code
    /*
    "When a booking is issued for a customer a booking
	object must be created holding a reference to the outbound and return 
	(ignore outbound and return - they are booking one flight at a time - comes with more optimised UI) 
	flights booked and to the customer that made the booking. This object should be added to the
	customer list of flight bookings. In addition the customer object should be added to the
	list of passenger in the flight object."
     */
    
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        // TODO: implementation here
    	//validation here
    	bookings.add(booking);
    }
    
    public void removeBooking(Booking booking) throws FlightBookingSystemException {
    	bookings.remove(booking);
    }
    
    public List<Booking> getBookings() {
		return bookings;
	}
    
    // TODO: implementation of Getter and Setter methods (right click -> source -> generate) (done)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
