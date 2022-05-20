
public class Restaurant {
	// Main attributes for Restaurant class.
	private String restaurantName;
	private String restaurantLocation;
	private String restaurantContactNumber;
	
	// Constructor
	public Restaurant(String restaurantName, String restaurantLocation, String restaurantContactNumber) {
		this.setRestaurantName(restaurantName);
		this.setRestaurantLocation(restaurantLocation);
		this.setRestaurantContactNumber(restaurantContactNumber);
	}

	// Getter
	public String getRestaurantName() {
		return restaurantName;
	}

	// Setter
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	// Getter
	public String getRestaurantLocation() {
		return restaurantLocation;
	}

	// Setter
	public void setRestaurantLocation(String restaurantLocation) {
		this.restaurantLocation = restaurantLocation;
	}

	// Getter
	public String getRestaurantContactNumber() {
		return restaurantContactNumber;
	}

	// Setter
	public void setRestaurantContactNumber(String restaurantContactNumber) {
		this.restaurantContactNumber = restaurantContactNumber;
	}
	
	
}
