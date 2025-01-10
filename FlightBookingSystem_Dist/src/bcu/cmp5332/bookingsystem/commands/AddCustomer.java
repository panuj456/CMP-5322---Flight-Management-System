package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command for adding a new customer to the flight booking system.
 
 * This class implements the Command interface and provides functionality
 * to add a new customer to the FlightBookingSystem. It takes the customer's
 * name, phone number, and address as input, assigns an ID to the customer, 
 * and adds the customer to the system. The changes are then stored persistently
 * in the data files.
 * 
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see Customer
 */


public class AddCustomer implements Command {
	/**
	 * This program is for adding a Customer to the flight booking system
	 * this changes attributes within flight booking system, customer
	 	* @param args
          * @param flightBookingSystem The flight booking system instance to add the customer to.
         * @throws FlightBookingSystemException If there is an error storing the system data.
         */

    private final String name;
    private final String phone;
    private final String address;

    public AddCustomer(String name, String phone, String address) {
    	/**
    	 * class recieves name, phone and address all in string format 
    	 * from user input

         
         * @param flightBookingSystem The flight booking system instance to add the customer to.
         * @throws FlightBookingSystemException If there is an error storing the system data.
         */
    	
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	/**
    	 * Execute recieves flightBookingSystem which relates to the overall fbs object model
    	 * which is passed through main, thus changes apply to the object and thus throughout the system.
    	 	* @param
    	 */
    	
    	int maxId = 0;
        if (flightBookingSystem.getCustomers().size() > 0) {
            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
        }
        /**
         * checks that customerList in flightBookingSystem class object is not empty
         * if empty the maxId is set to 0 as the new customer will become the 0 indexed item
         * last index value is found then the id of the lastIndex item is set as maxId (when not empty)
         */
        
        Customer customer = new Customer(++maxId, name, phone, address, true);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer ID " + customer.getId() + " added.");
        /**
         * Creates new customer object with attributes given to command by user.
         * and adds customer to flightBookingSystemList, gives user output message to confirm function
         	* @return
         */
        
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
         	* @return
         */
    }
   }

