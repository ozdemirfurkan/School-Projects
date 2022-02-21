// separator	Car.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Car.java_1_true.txt

package vehicles;

/**
 * 
 * @author Furkan Ozdemir
 */
public class Car{
	
	private int ownerID;
	private double fuelAmount;
	private double fuelConsumption;
	
	/**
	 * Car class constructor
	 * @param ID car's owner's ID
	 * @param fuelConsumption car's fuel consumption
	 */
	public Car(int ID, double fuelConsumption) {
		
		this.ownerID = ID;
		this.fuelConsumption=fuelConsumption;
		
	}
	
	/**
	 * this method refuels car.
	 * @param amount shows how much fuel we should add
	 */
	public void refuel(double amount) {
		
		if(amount>0) {
			
			this.fuelAmount+=amount;
		}
	}
	
	/**
	 * gets car's owner's ID
	 * @return ownerID
	 */
	public int getOwnerID() {
		
		return this.ownerID;
		
	}
	
	/**
	 * gets current fuel amount
	 * @return fuelAmount
	 */
	public double getFuelAmount() {
		
		return this.fuelAmount;
		
	}
	
	/**
	 * sets current fuel amount
	 * @param amount shows how much fuel does the car have
	 */
	public void setFuelAmount(double amount) {
		
		if(amount>0) {
			
			this.fuelAmount=amount;
		}
	}
	
	/**
	 * gets car's fuel consumption
	 * @return fuelConsumption
	 */
	public double getFuelConsumption() {
		
		return this.fuelConsumption;	
		
	}
}



// separator	Car.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

