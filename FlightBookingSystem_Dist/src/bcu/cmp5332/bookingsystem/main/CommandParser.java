package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import bcu.cmp5332.bookingsystem.commands.ShowCustomer;
import bcu.cmp5332.bookingsystem.commands.ShowFlight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListBookings;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            
            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flighNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();
                LocalDate departureDate = parseDateWithAttempts(reader);
                System.out.print("Capacity: ");
                String temp = reader.readLine();
                int capacity = Integer.parseInt(temp);
                System.out.print("Price: ");
                String temp2 = reader.readLine();
                double price = Double.parseDouble(temp2);
                return new AddFlight(flighNumber, origin, destination, departureDate, capacity, price);
            
            } else if (cmd.equals("addcustomer")) {
            	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Name: ");
                String name = reader.readLine();
                System.out.print("Phone Number: ");
                String phone = reader.readLine();
                System.out.print("Email Address: ");
                String email = reader.readLine();
                return new AddCustomer(name, phone, email);
                
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            
            } else if (cmd.equals("showbookings")) {
            	return new ListBookings();
            			
        	}else if (parts.length == 1) {
                
        		if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                	return new ListCustomers();
                    
                } else if (line.equals("help")) {
                    return new Help();
                }
            
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                	return new ShowFlight(id);
                    
                } else if (cmd.equals("showcustomer")) {
                	return new ShowCustomer(id);
                }
            
            } else if (parts.length == 3) {//for bookings {
       
                if (cmd.equals("addbooking")) {
                	//ask for customer ID then flight ID
                	int customerID = Integer.parseInt(parts[1]);
                	int flightID = Integer.parseInt(parts[2]);
                    LocalDate bookingDate = LocalDate.now(); //reader.readLine(); //booking date kept for now, will set to current time LocalDate.now()
                    return new AddBooking(customerID, flightID, bookingDate);
                    
                }  else if (cmd.equals("cancelbooking")) {
                	int customerID = Integer.parseInt(parts[1]);
                	int flightID = Integer.parseInt(parts[2]);
                	return new CancelBooking(customerID, flightID);               
                }                
            } 
            //Solely for FlightBookingSystemTest
            else if (parts.length == 4) {//for bookings
                
                if (cmd.equals("addcustomertest")) {
                	String name = parts[1];
                	String phone = parts[2];
                	String email = parts[3];
                    return new AddCustomer(name, phone, email);  
                }
                else if (cmd.equals("editbooking")) { //[booking id] [flight id] [bookingDate]
                //stored on customer, can modify and add parameter, ambiguous - make decision - justify
                	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                	int customerID = Integer.parseInt(parts[1]);
               	 	int flightID = Integer.parseInt(parts[2]);
               	 	LocalDate bookingDate = LocalDate.parse(parts[3]);
               	 	// Menu
               	 	System.out.println("Update - 1 for Change Customer Id");
               	 	System.out.println("Update - 2 for Change Flight Id");
               	 	System.out.println("Update - 3 for Change Booking Date");
	                String temp = reader.readLine();
	                int selection = Integer.parseInt(temp);
	                if (selection == 1){
                        System.out.println("Update - Enter Customer ID (else type 'n')");
                        String temp2 = reader.readLine();
                        int updateCustomerID = Integer.parseInt(temp2);
                        
                        
                    }
                    else if (selection == 2){
                        System.out.println("Update - Enter Flight ID (else type 'n')");
                        String temp2 = reader.readLine();
                        int updateFlightID = Integer.parseInt(temp2);
                        
                        
                    }
                    if (selection == 3){
                        System.out.println("Update - Enter Booking Date (YYYY-MM-DD) (else type 'n')");
                        String temp2 = reader.readLine();
                        LocalDate updateBookingDate = LocalDate.parse(temp2);
                        
                        
                    }
                
				return new UpdateBooking(customerID, flightID, bookingDate, updateCustomerID, updateFlightID, updateBookingDate);              
                } 
             } 
        } catch (NumberFormatException ex) {

        }
        throw new FlightBookingSystemException("Invalid command.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher that 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
