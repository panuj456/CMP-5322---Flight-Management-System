package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String address;

    public AddCustomer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // TODO: implementation here
    	//change to customer variables
    	int maxId = 0;
        if (flightBookingSystem.getCustomers().size() > 0) {
            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
        }
        
        Customer customer = new Customer(++maxId, name, phone, address, true);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer ID " + customer.getId() + " added.");
        
		//working, updates stored even when exit isnt typed - not sure how rollback should be implemented other than changes arent implemented if IOException is met
        try {
			FlightBookingSystemData.store(flightBookingSystem);
			System.out.println("Update successfully stored");
			} catch (IOException e) {
				throw new FlightBookingSystemException("Updates could not be stored.");
			}
    }
   }

