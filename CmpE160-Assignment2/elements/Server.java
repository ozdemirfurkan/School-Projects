// separator	Server.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package elements;

import java.io.PrintStream;
import java.util.*;

public class Server{
	
	/**
	 * capacity of the server 
	 */
	private long capacity;
	/**
	 * This number should be kept updated when messages in the server 
	 * are deleted or added. Total number of the characters of messages’ 
	 * bodies in the msgs queue.
	 */
	public long currentSize;
	/**
	 * A queue where non received messages are stored.
	 */
	public Queue<Message> msgs = new LinkedList<Message>();
	/**
	 * After %50 full message this variable change to false.
	 */
	private boolean fiftyOccupancy = true;
	/**
	 * After %80 full message this variable change to false.
	 */
	private boolean eightyOccupancy = true;
	
	/**
	 * Server class constructor
	 * @param capacity capacity of server 
	 */
	public Server(long capacity) {
		
		this.capacity = capacity;
		this.currentSize = 0;
		
	}
	
	/**
	 * prints the warnings about the capacity
	 * @param printer PrintStream object
	 */
	public void checkServerLoad(PrintStream printer) {
		
		double serverOccupancyRate = (double) currentSize / (double) capacity;
		
		/**
		 * if occupancy rate is higher than 1, deletes all messages 
		 * in the server.
		 */
		if(serverOccupancyRate >= 1) {
			
			printer.println("Server is full. Deleting all messages...");
			flush();
			fiftyOccupancy = true;
			eightyOccupancy = true;
			
		}else if(serverOccupancyRate >= 0.8 && eightyOccupancy && serverOccupancyRate < 1) {
			
			printer.println("Warning! Server is 80% full.");
			eightyOccupancy = false;
			fiftyOccupancy = true;
			
		}else if(serverOccupancyRate >= 0.5 && fiftyOccupancy && serverOccupancyRate < 0.8) {
			
			printer.println("Warning! Server is 50% full.");
			fiftyOccupancy = false;
			eightyOccupancy = true;
			
		}else if(serverOccupancyRate < 0.5){
			
			fiftyOccupancy = true;
			eightyOccupancy = true;
			
		}
	}
	
	/**
	 * getter method for current size.
	 * @return currentSize
	 */
	public long getCurrentSize() {
		
		return currentSize;
		
	}
	
	/**
	 * getter method for capacity of server
	 * @return capacity
	 */
	public long getCapacity() {
		
		return capacity;
		
	}
	
	/**
	 * getter method for msgs queue
	 * @return msgs
	 */
	public Queue getMsgs() {
		
		return msgs;
		
	}
	
	/**
	 * Empties the queue
	 */
	public void flush() {
		
		while(!msgs.isEmpty()) {
			
			msgs.remove();
			
		}
		
		currentSize = 0;
	}
	
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE