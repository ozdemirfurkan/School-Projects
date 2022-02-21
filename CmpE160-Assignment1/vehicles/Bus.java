// separator	Bus.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Bus.java_1_true.txt

package vehicles;

import locations.Location;

/**
 * 
 * @author Furkan Ozdemir
 */
public class Bus extends PublicTransport{
	
	/**
	 * Bus class constructor
	 * @param ID vehicle's ID
	 * @param x1,y1,x2,y2 coordinates of rectangle
	 */
	public Bus(int ID, double x1, double y1, double x2, double y2) {
		
		super(ID,x1,y1,x2,y2);
		
	}
	
	/**
	 * calculates the journey price
	 * @param departure departure location
	 * @param arrival arrival location
	 * @return price of the journey
	 */
	public double getPrice(Location departure, Location arrival) {
		
		if(canRide(departure,arrival)) {
			
			return 2;
			
		}else {
			
			return 0;
			
		}
	}
}



// separator	Bus.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

