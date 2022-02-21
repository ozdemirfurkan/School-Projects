// separator	PublicTransport.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	PublicTransport.java_1_true.txt

package vehicles;

import locations.Location;

/**
 * 
 * @author Furkan Ozdemir
 */
public abstract class PublicTransport {
	
	private int ID;
	private double x1, y1, x2, y2;
	
	/**
	 * PublicTransport class constructor 
	 * @param ID public transportation vehicle ID
	 * @param x1,y1,x2,y2 coordinates of rectangle
	 */
	public PublicTransport(int ID, double x1, double y1, double x2, double y2) {
		
		this.ID=ID;
		
		if(x1<x2) {
			this.x1=x1;
			this.x2=x2;
		}else {
			this.x1=x2;
			this.x2=x1;
		}
		
		if(y1<y2) {
			this.y1=y1;
			this.y2=y2;
		}else {
			this.y1=y2;
			this.y2=y1;
		}
		
	}
	
	/**
	 * to determine whether the vehicle is suitable for the journey
	 * @param departure departure location
	 * @param arrival arrival location
	 * @return true if the vehicle is suitable
	 */
	public boolean canRide(Location departure, Location arrival) {
		
		if(departure.getLocationX()>=x1 && departure.getLocationX()<=x2 && departure.getLocationY()>=y1 && departure.getLocationY()<=y2) {
			
			if(arrival.getLocationX()>=x1 && arrival.getLocationX()<=x2 && arrival.getLocationY()>=y1 && arrival.getLocationY()<=y2) {
				
				return true;
				
			}else {
				
				return false;
				
			}
			
		}else {
			
			return false;
		}
	}
	
	/**
	 * gets vehicle's ID
	 * @return ID
	 */
	public int getID() {
		
		return ID;
		
	}
}



// separator	PublicTransport.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE





