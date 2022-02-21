// separator	DiscountedPassenger.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	DiscountedPassenger.java_1_true.txt

package passengers;

import locations.Location;
import vehicles.*;

/**
 * 
 * @author Furkan Ozdemir
 */

public class DiscountedPassenger extends Passenger{
	
	/**
	 * DiscountedPassenger class has two constructors like Passenger class.
	 */
	
	public DiscountedPassenger(int ID, boolean hasDriversLicense, Location l) {
		
		super(ID, hasDriversLicense, l);
		
	}
	
	public DiscountedPassenger(int ID, Location l, double fuelConsumption) {
		
		super(ID, l, fuelConsumption);
		car = new Car(ID, fuelConsumption);
		hasDriversLicense = true;
		
	}
	
	/**
	 * for the discounted passengers to use train.
	 * @param t train object
	 * @param l arrival point
	 */
	public void rideTrain(Train t, Location l) {
		
		if(cardBalance>=0.8*(t.getPrice(currentLocation, l))) {
			
			cardBalance-=0.8*(t.getPrice(currentLocation, l));
			ride(t, l);
		}
	}
	
	/**
	 * for the discounted passengers to use bus
	 * @param b bus object
	 * @param l arrival point
	 */
	public void rideBus(Bus b, Location l) {
		
		if(cardBalance>=0.5*(b.getPrice(currentLocation, l))) {

			cardBalance-=0.5*(b.getPrice(currentLocation, l));
			ride(b, l);
		}
	}
	
}



// separator	DiscountedPassenger.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

