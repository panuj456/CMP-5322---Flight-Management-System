package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
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
                    Customer customer = fbs.getCustomerByID(customerID);
                    Flight flight = fbs.getFlightByID(flightID);
                    Boolean departed = flight.getDeparted();
                    Double price = Double.parseDouble(properties[3]);							//booking price already purchased so should not change, bookings should list price bought at not current air price
                    Double fee = Double.parseDouble(properties[4]);
                    Booking booking = new Booking(customer, flight, bookingDate, departed, price, fee); 
                    customer.addBooking(booking);
                    flight.addPassenger(customer);
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
    	//saving data into file, storing Ids enable the booking loading with getbyid to work correctly
    	try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
                out.print(booking.getCustomerId() + SEPARATOR);
                out.print(booking.getFlightId() + SEPARATOR);
                out.print(booking.getBookingDate() + SEPARATOR); 
                out.print(booking.getPrice() + SEPARATOR); 
                out.print(booking.getFee() + SEPARATOR); 
                /**
                 * no need for departed to be stored as fixates upon status of flight.getDeparted which checks
                 * the departuredate vs current date
                 */
                out.println();
            }
        }
    }
    
}