
public class Address {
	// Main attributes for Address class.
	private String street;
	private String suburb;
	
	// Constructor
	public Address(String street, String suburb) {
		this.setStreet(street);
		this.setSuburb(suburb);
	}

	// Getter
	public String getStreet() {
		return street;
	}

	// Setter
	public void setStreet(String street) {
		this.street = street;
	}

	// Getter
	public String getSuburb() {
		return suburb;
	}

	// Setter
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
	/**
	 * Displays Address object in its string format.
	 */
	@Override
	public String toString() {
		
		return this.street + "\r\n" + this.suburb;
		
	}
	
}
