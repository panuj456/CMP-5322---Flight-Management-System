package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.time.LocalDate;

import java.util.List;

public class ListFlights implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        int count = 0; //does not delete but hides
        for (Flight flight : flights) {
        	if (flight.getFlightInView() == true && flight.getDepartureDate().isAfter(LocalDate.now())) { //if hiding when deleted, why keep in system?? nonsensical seemingr    
        		flight.setDeparted(false); //no change but shows intention
        		System.out.println(flight.getDetailsShort());
        		count++;
        	}
        }
        System.out.println(count + " flight(s)");
        //System.out.println(flights.size() + " flight(s)");
    }
}
