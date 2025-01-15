package bcu.cmp5332.bookingsystem.data; 

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/flights.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int capacity = Integer.parseInt(properties[5]);
                    Double price = Double.parseDouble(properties[6]); //flight price should change and be here
                    Boolean inView = Boolean.parseBoolean(properties[7]);         
                    Boolean departed = departureDate.isAfter(LocalDate.now());
                    LocalDate latestDate = LocalDate.parse(properties[8]); //used to ensure everytime fbs main is run, a price increase does not happen on the same day
                    Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, capacity, price, inView, departed, latestDate);                   
                    
                    /*//caused issue in final submission - variable price works now
                    if (latestDate.isEqual(LocalDate.now())) {
                    ; //price remains same - no need to inform user of price increase
                    }
                    */
                    //below was else if
                    if (departureDate.compareTo(LocalDate.now()) < 10) {
                    	//post submission - improvement could be more difference in cost for capacity and date separate calc
                    	if (flight.getPassengers().size() > flight.getCapacity() * 0.75) { 
                    		price = price * 1.05;
                    		latestDate = LocalDate.now();
                    	}
                    	else if (flight.getPassengers().size() < (int)flight.getCapacity() * 0.5) {
                    		price = price * 0.95;
                    		latestDate = LocalDate.now();
                    	}
                    	flight.setLatestDate(latestDate);
                    	flight.setPrice(price); //forgot to add line in final submission
                    }
                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getCapacity() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.print(flight.getFlightInView() + SEPARATOR);
                out.print(flight.getLatestDate() + SEPARATOR);
                out.println();
            }
        }
    }
}
