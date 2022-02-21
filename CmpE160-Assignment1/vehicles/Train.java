// separator	Train.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Train.java_1_true.txt

package vehicles;

import locations.Location;

/**
 * 
 * @author Furkan Ozdemir
 */
public class Train extends PublicTransport{
	
	/**
	 * Train class constructor
	 * @param ID vehicle's ID
	 * @param x1,y1,x2,y2 coordinates of rectangle
	 */
	public Train(int ID, double x1, double y1, double x2, double y2) {
		
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
			
			return (Math.round((departure.getDistance(arrival)/15.0)))*5.0;
			
		}else {
			
			return 0;
			
		}
	}
}



// separator	Train.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

