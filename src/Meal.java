
public class Meal {
	// Main attributes for Meal class.
	private String mealName;
	private double mealPrice;
	private int mealQuantity;
	
	// Constructor
	public Meal(String mealName, double mealPrice, int mealQuantity) {
		this.setMealName(mealName);
		this.setMealPrice(mealPrice);
		this.setMealQuantity(mealQuantity);
	}

	// Getter
	public String getMealName() {
		return mealName;
	}

	// Setter
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	// Getter
	public double getMealPrice() {
		return mealPrice;
	}

	// Setter
	public void setMealPrice(double mealPrice) {
		this.mealPrice = mealPrice;
	}
	
	// Getter
	public int getMealQuantity() {
		return mealQuantity;
	}

	// Setter
	public void setMealQuantity(int mealQuantity) {
		this.mealQuantity = mealQuantity;
	}

	/**
	 * Displays Meal object in its string format.
	 */
	@Override
	public String toString() {
		
		return this.mealQuantity + " x " + this.mealName + " (R"
				+ String.format("%.2f", (Math.round(this.mealPrice * 100.0) / 100.0)) + ")";

	}
}

/*
 * References:
 * 
 * How to round to 2 decimal places:
 * - https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/#stringformat2f-input
 */