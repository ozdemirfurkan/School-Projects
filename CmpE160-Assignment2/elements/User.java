// separator	User.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package elements;

import boxes.*;
import java.util.ArrayList;

public class User{
	
	/**
	 * Each user has a unique ID, starting from 0.
	 */
	private int ID;
	/**
	 * Each user has its own inbox.
	 */
	private Inbox inbox;
	/**
	 * Each user has its own outbox.
	 */
    private Outbox outbox;
    /**
     * An arraylist that stores user's friends.
     */
	private ArrayList<User> friends = new ArrayList<User>();
	
	/**
	 * User class constructor.
	 * @param ID user's unique ID
	 */
	public User(int ID) {
		
		this.ID= ID;
		this.inbox = new Inbox(this);
		this.outbox = new Outbox(this);
		
	}
	
	/**
	 *  A method for adding a friend to the friends list.
	 *  It should also add the user to the other user’s friends list.
	 * @param other another user object 
	 */
	public void addFriend(User other) {
		
		if(other instanceof User) {
			
			if(!this.friends.contains(other)) {
				
			this.friends.add(other);
			other.friends.add(this);
			
			}
		}
	}
	
	/**
	 * A method for removing a friend from the friends list.
	 * It should also remove the user from the other user’s friends list.
	 * @param other another user object
	 */
	public void removeFriend(User other) {
		
		if(other instanceof User) {
			
			for(int i=0; i<friends.size(); i++) {
				
				if(friends.get(i).ID == other.ID) {
					
					friends.remove(i);
					
				}
			}
			
			for(int i=0; i<other.friends.size(); i++) {
				
				if(other.friends.get(i).ID == this.ID) {
					
					other.friends.remove(i);
					
				}
			}
		}
	}
	
	/**
	 * investigate whether users are friends or not
	 * @param other another friend object
	 * @return true if the user and the other user are friends, false otherwise.
	 */
	public boolean isFriendsWith(User other) {
		
		boolean isFriends = false;
			
			for(User u : friends) {
				
				if(u.getID() == other.getID()) {
					
					isFriends = true;
					
				}
			}
		
		return isFriends;
		
	}
	
	/**
	 * A new message should be created and added to the user’s
	 * sent list which is in his/her outbox.
	 * @param receiver 
	 * @param body messege body
	 * @param time
	 * @param server
	 */
	public void sendMessage(User receiver, String body, int time, Server server) {
		
		Message msg = new Message(this,receiver,body,server,time);
		this.outbox.sent.add(msg);
		server.msgs.add(msg);
		server.currentSize += body.length();
		
	}
	
	/**
	 * getter method for ID number
	 * @return ID number of user.
	 */
	public int getID() {
		
		return this.ID;
		
	}
	
	/**
	 * getter method for inbox
	 * @return inbox
	 */
	public Inbox getInbox() {
		
		return inbox;
		
	}
	
	/**
	 * getter method for outbox
	 * @return outbox
	 */
	public Outbox getOutbox() {
		
		return outbox;
		
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE