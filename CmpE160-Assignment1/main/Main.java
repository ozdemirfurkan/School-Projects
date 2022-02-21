// separator	Main.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Main.java_1_true.txt

package main;

import java.util.*;
import vehicles.*;
import java.io.*;
import java.text.DecimalFormat;

import passengers.*;
import locations.*;

/**
 * Main class simply reads an input file that is composed of
 * sequential commands related to the travel operations
 * @author Furkan Ozdemir
 */
public class Main {
	
	private static int passengerID = 0; //every passengers has its own unique ID
	private static int locationID = 1; //every location has its own unique ID
	private static int vehicleID = 0; //every vehicle has its own unique ID
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	public static void main(String[] args) throws FileNotFoundException {

		Scanner input = new Scanner(new File(args[0])).useLocale(Locale.US);//reads input file
		PrintStream output = new PrintStream(new File(args[1]));//writes output file

		ArrayList<Passenger> passengers = new ArrayList<Passenger>(); //arraylist that keeps passengers
		ArrayList<Location> locations = new ArrayList<Location>(); //arraylist that keeps locations
		ArrayList<PublicTransport> vehicles = new ArrayList<PublicTransport>(); //arraylist that keeps vehicles
		
	    Location l = new Location(0, 0, 0);  // The first location is always (0,0).
	    locations.add(l);
		int operations = input.nextInt();  // operation count
		
		/**
		 * this for loop perform 7 different actions for each operation
		 */
		for(int i=0; i<operations; i++) {
			
			int actionNumber = input.nextInt(); //every operation has an action number from 1 to 7
			
			/**
			 * if action number is 1 we should create a passenger
			 */
			if(actionNumber == 1) { 
				
				/**
				 * if the driver type is D then we should create a discounted passenger
				 * otherwise we should create a standard passenger
				 */
				String driverType = input.next();
				/**
				 * 1 means driver has a license 0 means not
				 */
				int hasLicense = input.nextInt(); 
				/**
				 * 1 means driver has a car 0 means not
				 * if the passenger has a car then one more input is given
				 */
				int hasCar = input.nextInt();
				
				if(driverType.contentEquals("D")) {
					
					if(hasLicense==1) {
						
						if(hasCar==1) {
							
							Passenger p = new DiscountedPassenger(passengerID, l, input.nextDouble()); //creating a discounted passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}else {
							
							Passenger p = new DiscountedPassenger(passengerID, true, l); //creating a discounted passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}
						
					}else {
						
						if(hasCar==1) {
							
							Passenger p = new DiscountedPassenger(passengerID, l, input.nextDouble()); //creating a discounted passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}else {
							
							Passenger p = new DiscountedPassenger(passengerID, false, l); //creating a discounted passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}
					}
						
				}else {
					
					if(hasLicense==1) {
						
						if(hasCar==1) {

							Passenger p = new StandardPassenger(passengerID, l, input.nextDouble()); //creating a standard passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}else {
							
							Passenger p = new StandardPassenger(passengerID, true, l); //creating a standard passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}
						
					}else {
						
						if(hasCar==1) {
							
							Passenger p = new StandardPassenger(passengerID, l, input.nextDouble()); //creating a standard passenger
							l.incomingPassenger(p);
							passengers.add(p);
							
						}else {
							
							Passenger p = new StandardPassenger(passengerID, false, l); //creating a standard passenger
							l.incomingPassenger(p);
							passengers.add(p);
						}
					}
				}
				
				passengerID++;
				
			/**
			 * if the action number is 2 then we should create a location	
			 */
			}else if(actionNumber == 2) {
				
				Location l1 = new Location(locationID, input.nextDouble(), input.nextDouble()); //creates location object
				locations.add(l1);
				locationID++;
			
			/**
			 * if the action number is 3 we should create a public transport vehicle 
			 */
			}else if(actionNumber == 3) {
				
				if(input.nextInt()==1) {
					
					Bus b=new Bus(vehicleID, input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble()); //creates a bus
					vehicles.add(b);
					
				}else {
					
					Train t = new Train(vehicleID, input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble()); //creates a train
					vehicles.add(t);
					
				}
				
				vehicleID++;
			
			/**
			 * if the action number is 4 passenger travels to a location	
			 */
			}else if(actionNumber == 4) {
				
				int travelerID = input.nextInt();
				int arrivalID = input.nextInt();
				/**
				 * if the vehicleChosen is 3 passenger drives his/her personal car
				 * if the vehicleChosen is 1 or 2 passenger use public transportation
				 */
				int vehicleChosen = input.nextInt(); 
				
				if(vehicleChosen==3) {
					
					if(passengers.get(travelerID).getHasCar()&&passengers.get(travelerID).getCar().getFuelAmount()>=(passengers.get(travelerID).getCurrentLocation().getDistance(locations.get(arrivalID)))*passengers.get(travelerID).getCar().getFuelConsumption()) {
						passengers.get(travelerID).getCurrentLocation().outgoingPassenger(passengers.get(travelerID)); //for history and current arraylist
						passengers.get(travelerID).drive(locations.get(arrivalID)); //passenger drives personal car
						locations.get(arrivalID).incomingPassenger(passengers.get(travelerID)); //for history and current arraylist
					}
					
				}else if(vehicleChosen==1) {
					
					int newVehicleID = input.nextInt();
					
					if(vehicles.get(newVehicleID) instanceof Bus) { //investigates the vehicle is bus or not
					
						if(passengers.get(travelerID) instanceof DiscountedPassenger) { //investigates the passenger is discounted passenger or not
						
							DiscountedPassenger d =(DiscountedPassenger) (passengers.get(travelerID));
							Bus b = (Bus) vehicles.get(newVehicleID);
						if(b.canRide(passengers.get(travelerID).getCurrentLocation(),locations.get(arrivalID))&&passengers.get(travelerID).getCardBalance()>=0.5*(b.getPrice(passengers.get(travelerID).getCurrentLocation(), locations.get(arrivalID)))) {
								passengers.get(travelerID).getCurrentLocation().outgoingPassenger(passengers.get(travelerID)); //for history and current arraylist
								d.rideBus(b, locations.get(arrivalID)); //passengers use bus
								locations.get(arrivalID).incomingPassenger(passengers.get(travelerID)); //for history and current arraylist
							}
							
						}else {
						
							StandardPassenger s =(StandardPassenger) (passengers.get(travelerID));
							Bus b = (Bus) vehicles.get(newVehicleID);
							if(b.canRide(passengers.get(travelerID).getCurrentLocation(),locations.get(arrivalID))&&passengers.get(travelerID).getCardBalance()>=(b.getPrice(passengers.get(travelerID).getCurrentLocation(), locations.get(arrivalID)))) {
								passengers.get(travelerID).getCurrentLocation().outgoingPassenger(passengers.get(travelerID)); //for history and current arraylist
								s.rideBus(b, locations.get(arrivalID)); //passengers use bus
								locations.get(arrivalID).incomingPassenger(passengers.get(travelerID)); //for history and current arraylist
							}
							
						}
					}
					
				}else if(vehicleChosen == 2) {
					
					int newVehicleID = input.nextInt();
					
					if(vehicles.get(newVehicleID) instanceof Train) { //investigates the vehicle is train or not
					
						if(passengers.get(travelerID) instanceof DiscountedPassenger) { //investigates the passenger is discounted passenger or not
						
							DiscountedPassenger d =(DiscountedPassenger) (passengers.get(travelerID));
							Train t = (Train) vehicles.get(newVehicleID);
						if(t.canRide(passengers.get(travelerID).getCurrentLocation(),locations.get(arrivalID))&&passengers.get(travelerID).getCardBalance()>=0.8*(t.getPrice(passengers.get(travelerID).getCurrentLocation(), locations.get(arrivalID)))) {
								passengers.get(travelerID).getCurrentLocation().outgoingPassenger(passengers.get(travelerID)); //for history and current arraylist
								d.rideTrain(t, locations.get(arrivalID)); //passengers use train
								locations.get(arrivalID).incomingPassenger(passengers.get(travelerID)); //for history and current arraylist
							}
							
						}else {
						
							StandardPassenger s =(StandardPassenger) (passengers.get(travelerID));
							Train t = (Train) vehicles.get(newVehicleID);
							if(t.canRide(passengers.get(travelerID).getCurrentLocation(),locations.get(arrivalID))&&passengers.get(travelerID).getCardBalance()>=(t.getPrice(passengers.get(travelerID).getCurrentLocation(), locations.get(arrivalID)))) {
								passengers.get(travelerID).getCurrentLocation().outgoingPassenger(passengers.get(travelerID)); //for history and current arraylist
								s.rideTrain(t, locations.get(arrivalID)); //passengers use train
								locations.get(arrivalID).incomingPassenger(passengers.get(travelerID)); //for history and current arraylist
							}
						}
					}	
				}
			    
			/**
			 * if the action number is 5 passengers purchase a new car	
			 */
			}else if(actionNumber == 5) {
				
				passengers.get(input.nextInt()).purchaseCar(input.nextDouble());
			
			/**
			 * if the action number is 6 passengers refuel their personal cars 	
			 */
			}else if(actionNumber == 6) {
				
				passengers.get(input.nextInt()).refuel(input.nextDouble());
			
			/**
			 * if the action number is 7 passengers refills the travel car	
			 */
			}else if(actionNumber==7){
				
				passengers.get(input.nextInt()).refillCard(input.nextDouble());
				
			}	
		}
		
		/**
		 * this for loop print passengers in the location list with their attributes
		 * if a passenger has a car we print the remaining fuel
		 * otherwise we print the remaining balance in travel card 
		 */
		for(int i=0; i<locations.size(); i++) {
			
			output.println("Location "+i+": ("+turnDouble(df2.format(locations.get(i).getLocationX()))+", "+turnDouble(df2.format(locations.get(i).getLocationY()))+")"); // print the location
			
			for(int j=locations.get(i).getCurrent().size()-1; j>=0; j--) {	
				
				if(locations.get(i).getCurrent().get(j).getHasCar()) { // if passenger has a car
					
					output.println("Passenger "+locations.get(i).getCurrent().get(j).getID()+": "+turnDouble2(locations.get(i).getCurrent().get(j).getCar().getFuelAmount())); //write fuel amount
					
				}else { //otherwise
					
					output.println("Passenger "+locations.get(i).getCurrent().get(j).getID()+": "+turnDouble2(locations.get(i).getCurrent().get(j).getCardBalance())); //write card balance
					
				}
			}
		}
	}
	
	public static String turnDouble(String s) { // double values should have 2 digits after the fraction point. 
		
		if(!(s.contains(".")||s.contains(","))) {
			
			s+=".00";
			
		}else if(s.charAt(s.length()-2)=='.') {
			
			s+="0";
			
		}
		
		return s;
		
	}
	
	public static String turnDouble2(double d) { //double values should have 2 digits after the fraction point.
			
		String s = Double.toString(d);
		if(!(s.contains("."))) {
			
			s+=".00";
			
		}else if(s.charAt(s.length()-2)=='.') {
			
			s+="0";
			
		}else {
			
			s=s.substring(0, s.indexOf('.')+3);
			
		}
		
		return s;
	}
	
}



// separator	Main.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

