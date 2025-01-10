package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private Boolean inView;
    private Boolean departed;
    private LocalDate latestDate; //last date price was increased

    private final Set<Customer> passengers;

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price, Boolean inView, Boolean departed, LocalDate latestDate) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity; //need method to check passanger capacity isnt full before adding customer
        this.price = price;
		this.inView = inView;
		this.departed = true;
		this.latestDate = latestDate;
        
        passengers = new HashSet<>();
    }
    
    public LocalDate getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(LocalDate latestDate) {
		this.latestDate = latestDate;
	}
    
    public Boolean getDeparted() {
		return departed;
	}

	public void setDeparted(Boolean departed) {
		this.departed = departed;
	}

	public Boolean getFlightInView() {
    	return inView;
    }
    
    public void setFlightInView(Boolean out) {
    	this.inView = out;
    }
    
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }
    
    public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf) + " at price " + price
                + " capacity " + passengers.size() + "/" + capacity + " Departed - " + departed;
    }

    public String getDetailsLong() {
        // TODO: implementation here
        return null;
    }
    
    public void addPassenger(Customer passenger) throws FlightBookingSystemException{
    	// TODO: implementation here
    	if (passengers.contains(passenger)) {
            throw new IllegalArgumentException("Duplicate Passenger. ");
        }
        for (Customer existing : passengers) {
            if (existing.getName().equals(passenger.getName()) 
                && existing.getPhone().equals(passenger.getPhone())) { 
                throw new FlightBookingSystemException("There is a Customer with same "
                        + "name and phone number in the system");
            }
        }
        passengers.add(passenger);
    }
    public void removePassenger(Customer passenger) {
    	if (passengers.contains(passenger)) {
    		passengers.remove(passenger);
    	}
    	else {
    		System.out.println("Not Found");
    	}
    }
}
