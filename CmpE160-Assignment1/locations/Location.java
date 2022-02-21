// separator	Location.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Location.java_1_true.txt

package locations;
import java.util.ArrayList;
import passengers.Passenger;

/**
 * 
 * @author Furkan Ozdemir
 */
public class Location{
	
	private int ID;
	private double locationX;
	private double locationY;
	private ArrayList<Passenger> history = new ArrayList<Passenger>();
	private ArrayList<Passenger> current = new ArrayList<Passenger>();
	
	/**
	 * Location class constructor
	 * @param ID each location object has it's own unique ID
	 * @param locationX X coordinate of location
	 * @param locationY Y coordinate of location
	 * @param history keeps track of each passenger who has visited
	 * @param current keeps track of the passengers currently here
	 */
	public Location(int ID, double locationX, double locationY) {
		
		this.ID=ID;
		this.locationX=locationX;
		this.locationY=locationY;
	
	}
	
	/**
	 * calculates the distance between object itself and another location
	 * @param other another location object
	 * @return distance as a double value
	 */
	public double getDistance(Location other) {
			
		return Math.sqrt((other.locationX-locationX)*(other.locationX-locationX)+(other.locationY-locationY)*(other.locationY-locationY));
		
	}
	
	/**
	 * this method perform operations on incoming passengers to this location
	 * @param p passenger object
	 */
	public void incomingPassenger(Passenger p) {
		
		history.add(p);
		current.add(p);
		
	}
	
	/**
	 * this method perform operations on outgoing passengers from this location
	 * @param p passenger object
	 */
	public void outgoingPassenger(Passenger p) {
		
		/**
		 * this for loop is used for traveling current arraylist.
		 */
		for(int i=0; i<current.size(); i++) {
			
			if((current.get(i)).equals(p)) {
				
				current.remove(i);
				
			}
		}
	}
	
	/**
	 * gets the ID of location
	 * @return ID
	 */
	public int getID() {
		
		return this.ID;
		
	}
	
	/**
	 * gets the X location 
	 * @return X location
	 */
	public double getLocationX() {
		
		return locationX;
		
	}
	
	/**
	 * gets the Y location 
	 * @return Y location
	 */
	public double getLocationY() {
		
		return locationY;
		
	}
	
	/**
	 * gets the history Arraylist 
	 * @return history arraylist
	 */
	public ArrayList<Passenger> getHistory() {
		
		return history;
		
	}
	
	/**
	 * gets the current arraylist 
	 * @return current arraylist
	 */
	public ArrayList<Passenger> getCurrent() {
		
		return current;
		
	}
}



// separator	Location.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

