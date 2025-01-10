package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ShowFlight implements Command {
    
	private final int id;

	public ShowFlight(int id) {
        this.id = id;
	}
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(this.id);
        System.out.println("Flight ID ("+this.id+") details "+ flight.getDetailsShort());
        for (Customer item : flight.getPassengers()) {
        	if (item.getInView() == true) {
        		System.out.println(item.getDetailsShort());
        	}
        }
    }
}