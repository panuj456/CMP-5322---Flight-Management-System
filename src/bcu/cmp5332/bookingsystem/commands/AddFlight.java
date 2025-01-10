package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;

public class AddFlight implements  Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private int capacity;
    private double price;

    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate,int capacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity; //need method to check passanger capacity isnt full before adding customer
        this.price = price;
    }
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        
        Boolean departed = false;
        if (departureDate.isAfter(LocalDate.now())) {
        	departed = true;
        }
        
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, price, true, departed, LocalDate.now()); //first addition, will need to see then can be set false after
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");
        
		//working, updates stored even when exit isnt typed - not sure how rollback should be implemented other than changes arent implemented if IOException is met   
        try {
			FlightBookingSystemData.store(flightBookingSystem);
			System.out.println("Update successfully stored");
			} catch (IOException e) {
				throw new FlightBookingSystemException("Updates could not be stored.");
			}
        /**
         * For a validated updating of data, the FlightBookingSystemData.store will store the current
         * state of flightBookingSystem object into the .txt files. This happens at every sucessful
         * update, thus even not typing exit, will still store the state of the program after the last
         * update made and a print message displayed to user.
         * If an update can't be made, a FlightBookingSystemException is thrown.
         */
    }
}
