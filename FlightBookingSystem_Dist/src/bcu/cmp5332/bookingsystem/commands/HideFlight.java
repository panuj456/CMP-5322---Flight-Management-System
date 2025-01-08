package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class HideFlight implements Command{
	
	private int FlightID;
	
	public HideFlight(int FlightID) {
		this.FlightID = FlightID;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		// TODO Auto-generated method stub
		Flight hideFlight = flightBookingSystem.getFlightByID(FlightID);
		hideFlight.setFlightInView(false);
	}

}
