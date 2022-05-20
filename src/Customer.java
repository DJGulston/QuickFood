import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Customer {
	
	// String constant variable for invoice.txt file name.
	private static final String INVOICE_TXT = "invoice.txt";

	// String constant variable for driver-info.txt file name.
	private static final String DRIVER_INFO_TXT = "driver-info.txt";
	
	// Main attributes for Customer class.
	private int orderNumber;
	private String customerName;
	private String customerContactNumber;
	private Address customerAddress;
	private String customerLocation;
	private String emailAddress;
	private Restaurant restaurant;
	private ArrayList<Meal> mealList = new ArrayList<>();
	private String specialInstructions;
	private double totalAmount;
	
	// Attributes used to create an efficient toString() function.
	private String strMealList; // String of all meals and their quantities.
	private boolean driverMatchesCustomerLocation; // Checks if driver's location matches the customer's location.
	private boolean driverMatchesRestaurantLocation; // Checks if driver's location matches the restaurant's location.
	private String driverNearRestaurant; // Name of the driver with the least load that is near the restaurant.
	
	// Constructor
	public Customer(int orderNumber, String customerName, String customerContactNumber, Address customerAddress,
			String customerLocation, String emailAddress, Restaurant restaurant, ArrayList<Meal> mealList,
			String specialInstructions)
	{
		// Set attributes
		this.setOrderNumber(orderNumber);
		this.setCustomerName(customerName);
		this.setCustomerContactNumber(customerContactNumber);
		this.setCustomerAddress(customerAddress);
		this.customerLocation = customerLocation;
		this.setEmailAddress(emailAddress);
		this.restaurant = restaurant;
		this.setMealList(mealList);
		this.setSpecialInstructions(specialInstructions);
		
		// Sets meal list string and the total cost of all meals.
		initMealOrders();
		
		// Sets driver location booleans
		initLocationStatuses();
		
	}

	/**
	 * Concatenates all meals from the mealList array list into one string that we display
	 * in the toString() method, and also calculates the total cost of all the meals together.
	 */
	private void initMealOrders() {
		this.strMealList = "";
		
		for(int i = 0; i < this.mealList.size(); i++) {
			
			Meal mealItem = this.mealList.get(i);
			
			// Concatenates meal list item to the strMealList string.
			if(i < this.mealList.size() - 1) {
				this.strMealList += mealItem + "\r\n";
			}
			else {
				this.strMealList += mealItem;
			}
			
			int mealQuantity = mealItem.getMealQuantity();
			double mealPrice = mealItem.getMealPrice();
			
			// Calculates total amount due for each meal ordered.
			this.totalAmount += mealQuantity * mealPrice;
		}
		
		// Rounds off total amount to two decimal places.
		this.totalAmount = Math.round(this.totalAmount * 100.0) / 100.0;
	}

	/**
	 * Sets both driver location boolean values relative to the customer and restaurant locations
	 * so we know what to return as an invoice to the customer (either an error message or a complete
	 * invoice) in the toString() method.
	 */
	private void initLocationStatuses() {
		this.driverMatchesCustomerLocation = true;
		this.driverMatchesRestaurantLocation = true;
		
		// Finds driver with least load in the same location as the customer.
		String driverNearCustomer = findDriver(this.customerLocation);
		
		// Finds driver with least load in the same location as the restaurant.
		this.driverNearRestaurant = findDriver(this.restaurant.getRestaurantLocation());
		
		// Checks if there is NO driver near the restaurant.
		if(this.driverNearRestaurant.equals("")) {
			this.driverMatchesRestaurantLocation = false;
			
			// Checks if there is no driver near the customer.
			if(driverNearCustomer.equals("")) {
				this.driverMatchesCustomerLocation = false;
			}
		}
		else {
			// In this scenario, there is a driver near the restaurant.
			// Checks if the driver near the customer does NOT match the driver near the restaurant.
			// If the driver's are not the same, we consider there to be NO drivers near the customer.
			if(!driverNearCustomer.equals(this.driverNearRestaurant)) {
				this.driverMatchesCustomerLocation = false;
			}
		}
	}

	// Getter
	public int getOrderNumber() {
		return orderNumber;
	}

	// Setter
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	// Getter
	public String getCustomerName() {
		return customerName;
	}

	// Setter
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// Getter
	public String getCustomerContactNumber() {
		return customerContactNumber;
	}

	// Setter
	public void setCustomerContactNumber(String customerContactNumber) {
		this.customerContactNumber = customerContactNumber;
	}

	// Getter
	public Address getCustomerAddress() {
		return customerAddress;
	}

	// Setter
	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	// Getter
	public String getCustomerLocation() {
		return customerLocation;
	}

	// Setter
	// Initializes driver location boolean values since the customer location has changed.
	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
		initLocationStatuses();
	}

	// Getter
	public String getEmailAddress() {
		return emailAddress;
	}

	// Setter
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	// Getter
	public Restaurant getRestaurant() {
		return restaurant;
	}

	// Setter
	// Initializes driver location boolean values in case the restaurant's location has changed.
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
		initLocationStatuses();
	}

	// Getter
	public ArrayList<Meal> getMealList() {
		return mealList;
	}

	// Setter
	public void setMealList(ArrayList<Meal> mealList) {
		this.mealList = mealList;
	}

	// Getter
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	// Setter
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	// Getter
	public double getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * Finds a driver with the least load that shares the same location as the location passed as
	 * the function's parameter strLocation.
	 * @param strLocation - Location that is being compared to locations in the driver-info.txt file.
	 * @return driver: String - Name of driver with the least load.
	 */
	private String findDriver(String strLocation) {
		String driver = "";
		
		// Lowest driver load initialized to a default value of 0.
		int minDriverLoad = 0;
		
		// Number of driver's that match the location.
		int driverMatchIndex = 0;
		
		File flDrivers = new File(DRIVER_INFO_TXT);
		
		Scanner scDrivers = null;
		
		Scanner scLine = null;
		
		try {
			// Scanner for driver-info.txt file.
			scDrivers = new Scanner(flDrivers);
			
			while(scDrivers.hasNextLine()) {
				// Line in driver-info.txt file.
				String strLine = scDrivers.nextLine();
				
				// Scanner for single line in driver-info.txt file.
				scLine = new Scanner(strLine);
				
				// Each item in a line separated using a comma and space as a dual delimiter.
				scLine.useDelimiter(", ");
				
				// Three line items in driver-info.txt file.
				String driverName = scLine.next();
				String driverLocation = scLine.next();
				int driverLoad = Integer.parseInt(scLine.next());
				
				// Checks if the driver's location is the same as the given parameter location.
				if(driverLocation.equals(strLocation)) {
					
					// If the parameter location has not matched a driver's location yet, set the current
					// minimum driver load and driver name to the driver load and driver name in the
					// current line of the the driver-info.txt file.
					if(driverMatchIndex == 0) {
						minDriverLoad = driverLoad;
						driver = driverName;
					}
					else {
						
						// If the current minimum driver load is greater than the driver load in the current line
						// of the drivers.txt file, set the current minimum driver load and driver name to the
						// driver load and driver name in the current line of the the driver-info.txt file.
						if(minDriverLoad > driverLoad) {
							minDriverLoad = driverLoad;
							driver = driverName;
						}
						
					}
					
					// Increment number of drivers matched.
					driverMatchIndex++;
				}
				
			}
			
		}
		catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: Cannot locate " + flDrivers.getName());
			e.printStackTrace();
		}
		finally {
			// Close file scanner
			scDrivers.close();
			
			// Close line scanner
			scLine.close();
		}
		
		// Return driver with least load in the same location as the parameter location.
		return driver;
	}
	
	/**
	 * Writes invoice details to the invoice.txt file.
	 */
	public void writeToInvoiceFile() {
		File flInvoice = new File(INVOICE_TXT);
		
		Formatter invoiceFormatter = null;
		
		try {
			invoiceFormatter = new Formatter(flInvoice);
			
			invoiceFormatter.format("%s", this.toString());
			
		}
		catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: Cannot locate " + flInvoice.getName());
			e.printStackTrace();
		}
		finally {
			invoiceFormatter.close();
		}
	}
	
	/**
	 * Displays Customer object in its string format.
	 */
	@Override
	public String toString() {
		String customer = "";
		
		// Checks if the driver is in the same location as the restaurant AND the customer.
		if(this.driverMatchesCustomerLocation && this.driverMatchesRestaurantLocation) {
			
			customer += "Order number: " + this.orderNumber + "\r\n"
					+ "Customer: " + this.customerName + "\r\n"
					+ "Email: " + this.emailAddress + "\r\n"
					+ "Phone number: " + this.customerContactNumber + "\r\n"
					+ "Customer location: " + this.customerLocation + "\r\n\r\n"
					+ "You have ordered the following from " + this.restaurant.getRestaurantName() + " in "
					+ this.restaurant.getRestaurantLocation() + ":\r\n\r\n"
					+ this.strMealList + "\r\n\r\n"
					+ "Special instructions: " + this.specialInstructions + "\r\n"
					+ "Total: R" + String.format("%.2f", this.totalAmount) + "\r\n\r\n"
					+ this.driverNearRestaurant + " is nearest to the restaurant and so they will be delivering your order to you at:\r\n\r\n"
					+ this.customerAddress + "\r\n\r\n"
					+ "If you need to contact the restaurant, their number is " + this.restaurant.getRestaurantContactNumber() + ".";
		
		}

		if(!this.driverMatchesRestaurantLocation) {
			// If there is NO driver near the restaurant, this message is displayed.
			customer += "Sorry! Our drivers are too far away from your restaurant to be able to deliver to your location.\r\n";
		}
		
		if(!this.driverMatchesCustomerLocation) {
			// If there is NO driver near the customer, or if the driver near the restaurant does NOT match
			// the driver near the customer, then this message is displayed.
			customer += "Sorry! Our drivers are too far away from you to be able to deliver to your location.\r\n";
		}
		
		return customer;
	}
}

/*
 * References:
 * 
 * How to round to 2 decimal places:
 * - https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/#stringformat2f-input
 */