// separator	Passenger.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Passenger.java_1_true.txt

package passengers;

import interfaces.*;
import locations.Location;
import vehicles.*;

/**
 * 
 * @author Furkan Ozdemir
 */
public class Passenger implements ownCar,usePublicTransport{
	
	private int ID;
	protected boolean hasDriversLicense;
	protected double cardBalance;
	protected Car car;
	private boolean hasCar;
	protected Location currentLocation;
	
	/**
	 * Passenger class has two constructor, first constructor is for passengers who
	 * have not got a car, second constructor is for passengers who have cars
	 * @param ID each passenger has own unique ID
	 * @param hasDriversLicense returns true if passenger has drivers license 
	 * @param l passenger's current location
	 * @param hasCar true if passenger has car
	 * @param cardBalance the passenger's travel card balance
	 * @param car passenger's car
	 * @param fuelConsumption car's fuel consumption
	 */
	
	public Passenger(int ID, boolean hasDriversLicense, Location l) {
		
		this.ID=ID;
		this.hasDriversLicense=hasDriversLicense;
		this.currentLocation=l;
		hasCar =false;
		
	}
	
	public Passenger(int ID, Location l, double fuelConsumption) {
		
		this.ID=ID;
		this.currentLocation=l;
		car = new Car(ID,fuelConsumption);
		this.hasDriversLicense=true;
		hasCar = true;
		
	}
	
	/**
	 * this method refuels passengers car if passengers has a car
	 * @param amount shows how much fuel we should add
	 */
	public void refuel(double amount) {
		
		if(amount>0 && hasCar) {
			
			car.refuel(amount);
		}
	}
	
	/**
	 * this method enables passengers purchasing cars and we accept that
	 * when a passenger purchase a car, he has drivers license.
	 * @param fuelConsumption shows new car's fuel consumption. 
	 */
	public void purchaseCar(double fuelConsumption) {
		
	    car = new Car(this.ID,fuelConsumption);
	    hasDriversLicense = true;
	    hasCar = true;
	    
	}
	
	/**
	 * for the passengers to use their own cars.
	 * @param l arrival point
	 */
	public void drive(Location l) {
		
		if(hasDriversLicense && car.getFuelAmount()>=(currentLocation.getDistance(l))*car.getFuelConsumption()) {
			
			car.setFuelAmount((car.getFuelAmount())-(currentLocation.getDistance(l))*(car.getFuelConsumption()));
			this.currentLocation=l;
			
		}
	}
	
	/**
	 * for the passengers to refill their travel cards.
	 * @param amount shows how much money we should add
	 */
	public void refillCard(double amount) {
		
		if(amount>0) {
			
			this.cardBalance+=amount;
		}
	}
	
	/**
	 * for the passengers to use public transportation
	 * @param p public transportation vehicle
	 * @param l arrival point
	 */
	public void ride(PublicTransport p, Location l) {
		
		if(p.canRide(currentLocation, l)) {
			
			this.currentLocation=l;
		}
	}
	
	/**
	 * overrides equal method for passengers
	 */
	public boolean equals(Object o) {
		
		if(o instanceof Passenger) {
			
			return this.ID == ((Passenger)o).ID;
			
		}else {
			
			return false;
		}
	}
	
	/**
	 * gets passenger's travel card balance
	 * @return cardBalance
	 */
	public double getCardBalance() {
		
		return cardBalance;
		
	}
	
	/**
	 * gets passenger's ID
	 * @return ID
	 */
	public int getID() {
		
		return ID;
		
	}
	
	/**
	 * gets passenger's current location
	 * @return currentLocation 
	 */
	public Location getCurrentLocation() {
		
		return currentLocation;
		
	}
	
	/**
	 * gets passenger whether has a car or not
	 * @return hasCar
	 */
	public boolean getHasCar() {
		
		return hasCar;
		
	}
	
	/**
	 * gets passenger's car
	 * @return car
	 */
	public Car getCar() {
		
		return car;
		
	}
	
	
}



// separator	Passenger.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

