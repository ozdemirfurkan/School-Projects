// separator	Main.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Main.java_1_true.txt

package executable;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import elements.*;
/**
 * 
 * @author Furkan Ozdemir
 *
 */
public class Main{
	
	public static void main(String[] args) throws FileNotFoundException{
				
		/**
		 * Initialization of the input and output.
		 */
		Scanner input = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));
		
		int time = 0;
		int userNumber = input.nextInt();
		int numOfQueries = input.nextInt();
		int capacity = input.nextInt();
		
		/**
		 * Initialization of the server.
		 */
		Server server = new Server(capacity);
		
		int userID = 0;
		
		/**
		 * An arraylist that keeps all users.
		 */
		ArrayList<User> allUsers = new ArrayList<User>();
		
		/**
		 * this for loop initialize all users in the program.
		 */
		for(int i=0; i < userNumber; i++) {
			
			User user = new User(userID);
			userID++;
			allUsers.add(user);
			
		}
		
		String noline = input.nextLine();
		
		/**
		 * this for loop carry out 10 types of event. 
		 */
		for(int i=0; i<numOfQueries; i++) {
			
			String line = input.nextLine();
			Scanner linescan = new Scanner(line);
			
			int actionNum = Integer.parseInt(linescan.next());
			
			/**
			 * if action number equals 0, a user sends a message to another user.
			 */
			if(actionNum == 0) {
				
				int senderID = Integer.parseInt(linescan.next());
				int receiverID = Integer.parseInt(linescan.next());
				
				String messageBody = linescan.next();
				
				while(linescan.hasNext()) {
					
					messageBody = messageBody + " " + linescan.next();
					
				}
				
				allUsers.get(senderID).sendMessage(allUsers.get(receiverID), messageBody, time, server);
				time++;
				server.checkServerLoad(output);
			
			/**
			 *  if action number equals 1, users receives all the messages that are 
			 *  sent to him/her from the server.
			 */
			}else if(actionNum == 1) {
				
				int receiverID = Integer.parseInt(linescan.next());
				allUsers.get(receiverID).getInbox().receiveMessages(server, time);
				time++;
				server.checkServerLoad(output);
			
			/**
			 *  if action number equals 2, user reads a certain amount of messages.	
			 */
			}else if(actionNum == 2) {
				
				int receiverID = Integer.parseInt(linescan.next());
				int numOfMessages = Integer.parseInt(linescan.next());
				time += allUsers.get(receiverID).getInbox().readMessages(numOfMessages, time);
			
			/**
			 *  if action number equals 21, user reads all messages from a sender.	
			 */
			}else if(actionNum == 21) {
				
				int receiverID = Integer.parseInt(linescan.next());
				int senderID = Integer.parseInt(linescan.next());
				time += allUsers.get(receiverID).getInbox().readMessages(allUsers.get(senderID), time);
				
			/**
			 *  if action number equals 22, user reads a spesific message.	
			 */
			}else if(actionNum == 22) {
				
				int receiverID = Integer.parseInt(linescan.next());
				int messageID = Integer.parseInt(linescan.next());
				allUsers.get(receiverID).getInbox().readMessage(messageID, time);
				time++;
			
			/**
			 *  if action number equals 3, user adds a friend. 	
			 */
			}else if(actionNum == 3) {
				
				int ID1 = Integer.parseInt(linescan.next());
				int ID2 = Integer.parseInt(linescan.next());
				allUsers.get(ID1).addFriend(allUsers.get(ID2));
				time++;
			
			/**
			 *  if action number equals 4, user removes a friend.	
			 */
			}else if(actionNum == 4) {
				
				int ID1 = Integer.parseInt(linescan.next());
				int ID2 = Integer.parseInt(linescan.next());
				allUsers.get(ID1).removeFriend(allUsers.get(ID2));
				time++;
			
			/**
			 *  if action number equals 5, deletes all messages from the queue of the server.	
			 */
			}else if(actionNum == 5) {
				
				server.flush();
				server.checkServerLoad(output);
				time++;
				
			/**
			 *  if action number equals 6, prints the current size of the server. 	
			 */
			}else if(actionNum == 6) {
				
				output.println("Current load of the server is "+server.getCurrentSize()+" characters.");
				time++;
				
			/**
			 *  if the action number 61, prints the last message a user has read.	
			 */
			}else if(actionNum == 61) {
				
				int receiverID = Integer.parseInt(linescan.next());
				Object[] readList = allUsers.get(receiverID).getInbox().read.toArray();
				if(readList.length>0) {
				
					Message msg = (Message) readList[readList.length-1];
					int senderID = msg.getSender().getID();
					output.println("	From: "+ senderID + " to: " + receiverID);
					output.println("	Received: " + msg.getTimeStampReceived() + " Read: " + msg.getTimeStampRead());
					output.println("	" + msg.getBody());
					
				}
				
				time++;
				
			}
			
		}
		
		
	}
}


// separator	Main.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

