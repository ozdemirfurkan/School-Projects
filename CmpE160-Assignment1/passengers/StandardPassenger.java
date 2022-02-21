// separator	StandardPassenger.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	StandardPassenger.java_1_true.txt

package passengers;

import locations.Location;
import vehicles.*;

/**
 * 
 * @author Furkan Ozdemir
 */
public class StandardPassenger extends Passenger{
	
	/**
	 * StandardPassenger class has two constructors like Passenger class.
	 */
	
	public StandardPassenger(int ID, boolean hasDriversLicense, Location l) {
		
		super(ID, hasDriversLicense, l);
		
	}
	
	public StandardPassenger(int ID, Location l, double fuelConsumption) {
		
		super(ID, l, fuelConsumption);
		hasDriversLicense = true;
		car = new Car(ID, fuelConsumption);
		
	}
	
	/**
	 * for the standard passengers to use train.
	 * @param t train object
	 * @param l arrival point
	 */
	public void rideTrain(Train t, Location l) {
		
		if(cardBalance>=1*(t.getPrice(currentLocation, l))) {
			
			cardBalance-=1*(t.getPrice(currentLocation, l));
			ride(t, l);
		}
	}
	
	/**
	 * for the standard passengers to use bus
	 * @param b bus object
	 * @param l arrival point
	 */
	public void rideBus(Bus b, Location l) {
		
		if(cardBalance>=1*(b.getPrice(currentLocation, l))) {
			
			cardBalance-=1*(b.getPrice(currentLocation, l));
			ride(b, l);
		}
	}
	
}



// separator	StandardPassenger.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

