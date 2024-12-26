package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class BookingDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/bookings.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        // TODO: implementation here
    	//change to booking variables
    	try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int customerID = Integer.parseInt(properties[0]);
                    int flightID = Integer.parseInt(properties[1]);
                    LocalDate bookingDate = LocalDate.parse(properties[2]);
                    //Flight flight = new Flight(id, flightNumber, origin, destination, departureDate);
                    Booking booking = new Booking(fbs.getCustomerByID(customerID), fbs.getFlightByID(flightID), bookingDate); // ###Error, parsing objects as parameters that arent stored

                    System.out.println(booking.getDetailsShort());
                    fbs.addBookingList(booking);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to pa"
                    		+ "rse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        // TODO: implementation here
    	//change to booking variables
    	try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
            	fbs.getCustomerByID(customerID);
            	fbs.getFlightByID(flightID);
            	bookingDate;
                out.print(booking. + SEPARATOR);
                out.print(booking.getFlight() + SEPARATOR);
                out.print(booking.getBookingDate() + SEPARATOR);
                out.println();
            }
        }
    }
    
}