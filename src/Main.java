import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	// Array list attribute that will contain all meals ordered by the customer.
	private static ArrayList<Meal> mealList;

	public static void main(String[] args) {
		
		// Initialize meal array list.
		mealList = new ArrayList<>();
		
		// Default order number.
		int orderNumber = 1;
		
		System.out.println("Welcome to Quick Food! You may enter your details below.");
		
		// Scanner initialized.
		Scanner scInput = new Scanner(System.in);
		
		// Prompts customer for their personal details.
		String customerName = promptCustomer("full name", scInput);
		String customerContactNumber = promptCustomer("contact number", scInput);
		String customerLocation = promptCustomer("city", scInput);
		String customerSuburb = promptCustomer("suburb", scInput);
		String customerStreet = promptCustomer("street address", scInput);
		// Address object created using the street and suburb strings.
		Address customerAddress = new Address(customerStreet, customerSuburb);
		String emailAddress = promptCustomer("email address", scInput);
		
		// Prompts customer for the restaurant from which they would like to order.
		System.out.print("\nPlease select a restaurant of your choice:"
				+ "\n1 - Ocean Basket"
				+ "\n2 - Burger King"
				+ "\n3 - Kung Fu Kitchen"
				+ "\n4 - Hussar Grill"
				+ "\nType either 1, 2, 3 or 4: ");
		
		String restaurantChoice = scInput.nextLine();
		
		// If a customer did not type 1, 2, 3 or 4 to choose a restaurant, they are prompted to choose again.
		while(!restaurantChoice.equals("1") &&
				!restaurantChoice.equals("2") &&
				!restaurantChoice.equals("3") &&
				!restaurantChoice.equals("4"))
		{
			System.out.println("\nYou did not choose a restaurant.");
			System.out.print("\nPlease select a restaurant of your choice:"
					+ "\n1 - Ocean Basket"
					+ "\n2 - Burger King"
					+ "\n3 - Kung Fu Kitchen"
					+ "\n4 - Hussar Grill"
					+ "\nType either 1, 2, 3 or 4: ");
			
			restaurantChoice = scInput.nextLine();
		}
		
		// Customer is prompted to enter the location of the restaurant from which they would like to order.
		String restaurantLocation = promptCustomer("restaurant city location", scInput);
		
		String restaurantName = "";
		String restaurantContactNumber = "";
		
		// Given the customer's restaurant choice, the restaurant name and restaurant phone number is set
		// based on the restaurant choice.
		switch(restaurantChoice){
		
		case "1":
			restaurantName = "Ocean Basket";
			restaurantContactNumber = "0815674892";
			break;
			
		case "2":
			restaurantName = "Burger King";
			restaurantContactNumber = "0726589134";
			break;
			
		case "3":
			restaurantName = "Kung Fu Kitchen";
			restaurantContactNumber = "0733498634";
			break;
			
		case "4":
			restaurantName = "Hussar Grill";
			restaurantContactNumber = "0864273569";
			break;
			
		}
		
		// Restaurant object created.
		Restaurant restaurant = new Restaurant(restaurantName, restaurantLocation, restaurantContactNumber);
		
		boolean promptMeal = true;
		
		// Keeps prompting customer to choose a meal until they type N.
		while(promptMeal) {
			// Prompts customer to choose a meal from the menu.
			Meal mealChoice = chooseMeal(restaurantChoice, scInput);
			
			// If the chosen meal has already been chosen before, the quantity of that meal
			// is then updated in the invoice.
			boolean quantityUpdated = updateMealQuantity(mealChoice);
			
			// If a quantity has NOT been updated, we add that meal to the invoice.
			if(!quantityUpdated) {
				mealList.add(mealChoice);
			}
			
			// Asks customer if they would like to order anything else.
			System.out.print("Would you like to add something else to your order? Type N for no, or type anything else for yes: ");
			String strOrderMore = scInput.nextLine();
			
			// If the customer does not want to order anything else (i.e. they type N), the loop stops
			// and they are no longer prompted to choose a meal.
			if(strOrderMore.equalsIgnoreCase("N")) {
				promptMeal = false;
			}
		}
		
		// Customer is prompted to enter any special instructions for their meals.
		System.out.print("\nPlease enter any special instructions (if any) pertaining to your order: ");
		String specialInstructions = scInput.nextLine();
		
		// Scanner closed.
		scInput.close();
		
		// Initialize Customer object using the above customer parameters.
		Customer customer = new Customer(orderNumber, customerName, customerContactNumber, customerAddress,
				customerLocation, emailAddress, restaurant, mealList, specialInstructions);
		
		// Writes Customer object to invoice.txt file.
		customer.writeToInvoiceFile();
		
		System.out.println("\nYour invoice has been successfully generated.");
		
		// Order number incremented by 1 upon generating a customer invoice.
		orderNumber++;
		
	}
	
	/**
	 * Prompts a customer to enter a particular detail using a scanner object and returns that detail.
	 * @param promptType: String - The type of of detail that is being prompted from the customer.
	 * @param scInput: Scanner - The scanner object used to retrieve the detail from the customer.
	 * @return userInput: String - The detail entered by the customer.
	 */
	public static String promptCustomer(String promptType, Scanner scInput) {
		String userInput = "";
		
		System.out.print("\nPlease enter your " + promptType + ": ");
		userInput = scInput.nextLine();
		
		while(userInput.equals("")) {
			System.out.print("\nEntry cannot be blank. Please enter your " + promptType + ": ");
			userInput = scInput.nextLine();
		}
		
		return userInput;
	}
	
	/**
	 * If a given meal already exists in the mealList array list, the quantity of that
	 * meal element in the meal list is updated.
	 * @param mealChoice: Meal - The newly ordered meal that is being checked if it already
	 * exists in the meal list.
	 * @return Boolean - Returns true if the matching meal's quantity in the meal list
	 * has been updated, and false if there is no matching meal quantity to update in
	 * the meal list.
	 */
	public static boolean updateMealQuantity(Meal mealChoice) {
		String mealChoiceName = mealChoice.getMealName();
		double mealChoicePrice = mealChoice.getMealPrice();
		int mealChoiceQuantity = mealChoice.getMealQuantity();
		
		for(int i = 0; i < mealList.size(); i++) {
			Meal currentMeal = mealList.get(i);
			
			// If the given meal name and price of the mealChoice parameter match that of the current meal,
			// i.e. the given meal name and price exist in the meal list already, then we sum the original
			// from the meal list and the quantity of the mealChoice parameter to get the updated quantity.
			if(mealChoiceName.equals(currentMeal.getMealName()) && mealChoicePrice == currentMeal.getMealPrice()) {
				// New updated quantity calculated.
				int newQuantity = mealChoiceQuantity + currentMeal.getMealQuantity();
				
				// Meal quantity updated in the meal list.
				mealList.set(i, new Meal(mealChoiceName, mealChoicePrice, newQuantity));
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Prompts the customer to choose a meal from a given menu based on their restaurant choice.
	 * @param restaurantChoice: String - The choice of restaurant given to us by the customer.
	 * @param scInput: Scanner - Scanner object used to obtain the customer's meal choice.
	 * @return mealChoice: Meal - The meal chosen by the customer.
	 */
	public static Meal chooseMeal(String restaurantChoice, Scanner scInput) {
		
		Meal option1 = null;
		Meal option2 = null;
		Meal option3 = null;
		Meal option4 = null;
		
		// Meal options are set based on the restaurant chosen by the customer.
		if(restaurantChoice.equals("1")) {
			option1 = new Meal("Hake and Chips", 39.90, 1);
			option2 = new Meal("Kingklip and Vegetables", 69.90, 1);
			option3 = new Meal("Family Platter", 89.90, 1);
			option4 = new Meal("500ml Coke Zero", 15.90, 1);
		}
		else if(restaurantChoice.equals("2")) {
			option1 = new Meal("Whopper with Chips", 44.90, 1);
			option2 = new Meal("Vegan Royale", 38.90, 1);
			option3 = new Meal("Extra Long Chilli Cheese", 41.90, 1);
			option4 = new Meal("500ml Sprite", 15.90, 1);
		}
		else if(restaurantChoice.equals("3")) {
			option1 = new Meal("Sweet and Sour Pork", 37.90, 1);
			option2 = new Meal("Szechuan Chicken", 37.90, 1);
			option3 = new Meal("Chicken Chow Mein", 39.90, 1);
			option4 = new Meal("500ml Fanta Orange", 15.90, 1);
		}
		else if(restaurantChoice.equals("4")) {
			option1 = new Meal("Ribeye 400g", 69.90, 1);
			option2 = new Meal("Fillet", 59.90, 1);
			option3 = new Meal("Rump", 64.90, 1);
			option4 = new Meal("500ml Coke", 14.90, 1);
		}
		
		String strMealChoice = "";
		Meal mealChoice = null;
		
		// Customer prompted to choose a meal from a menu.
		System.out.print("\nPlease select a meal of your choice:"
				+ "\n1 - " + option1.getMealName() + " (R" + String.format("%.2f", option1.getMealPrice()) + ")"
				+ "\n2 - " + option2.getMealName() + " (R" + String.format("%.2f", option2.getMealPrice()) + ")"
				+ "\n3 - " + option3.getMealName() + " (R" + String.format("%.2f", option3.getMealPrice()) + ")"
				+ "\n4 - " + option4.getMealName() + " (R" + String.format("%.2f", option4.getMealPrice()) + ")"
				+ "\nType either 1, 2, 3 or 4: ");
		
		strMealChoice = scInput.nextLine();
		
		// If the customer did not type 1, 2, 3 or 4 (i.e. they have not chosen their meal), they are
		// then prompted to choose their meal again.
		while(!strMealChoice.equals("1") &&
				!strMealChoice.equals("2") &&
				!strMealChoice.equals("3") &&
				!strMealChoice.equals("4"))
		{
			System.out.println("\nYou did not choose a meal.");
			System.out.print("\nPlease select a meal of your choice:"
					+ "\n1 - " + option1.getMealName() + " (R" + String.format("%.2f", option1.getMealPrice()) + ")"
					+ "\n2 - " + option2.getMealName() + " (R" + String.format("%.2f", option2.getMealPrice()) + ")"
					+ "\n3 - " + option3.getMealName() + " (R" + String.format("%.2f", option3.getMealPrice()) + ")"
					+ "\n4 - " + option4.getMealName() + " (R" + String.format("%.2f", option4.getMealPrice()) + ")"
					+ "\nType either 1, 2, 3 or 4: ");
			
			strMealChoice = scInput.nextLine();
		}
		
		// Meal choice is assigned based on which number the customer typed.
		if(strMealChoice.equals("1")) {
			mealChoice = option1;
		}
		else if(strMealChoice.equals("2")) {
			mealChoice = option2;
		}
		else if(strMealChoice.equals("3")) {
			mealChoice = option3;
		}
		else if(strMealChoice.equals("4")) {
			mealChoice = option4;
		}
		
		boolean promptQuantity = true;
		
		// Customer is continuously prompted to enter the quantity of their meal choice
		// if they do not enter an integer to indicate their meal quantity.
		while(promptQuantity) {
			// Customer prompted to enter meal quantity.
			System.out.print("\nEnter the quantity of the meal you would like to order: ");
			String strQuantity = scInput.nextLine();
			
			try {
				int quantity = Integer.parseInt(strQuantity);
				
				// Quantity of the customer's meal choice is set.
				mealChoice.setMealQuantity(quantity);
				
				// Boolean set to false, and thus the customer will no longer prompted to
				// enter a quantity.
				promptQuantity = false;
			}
			catch(NumberFormatException ex) {
				// Error message displayed to user if they did not enter an integer as a quantity.
				System.out.println("\nYou have not entered a quantity. A quantity must be an integer.");
			}
		}
		
		return mealChoice;
		
	}
	
}
