// separator	Inbox.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package boxes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import elements.*;

public class Inbox extends Box{
	
	/**
	 * the stack which contains unread messages.
	 */
	public Stack<Message> unread = new Stack<Message>();
	/**
	 * the queue which contains read messages.
	 */
	public Queue<Message> read = new LinkedList<Message>();
	
	/**
	 * Inbox class constructor
	 * @param owner owner of the inbox
	 */
	public Inbox(User owner){
		
		this.owner = owner;
		
	}
	
	/**
	 * receives messages from the server, adds to the unread stack. 
	 * This method also changes timeStampReceived with the parameter time.
	 * @param server
	 * @param time
	 */
	public void receiveMessages(Server server, int time) {
		
		for(Message msg : server.msgs) {
			
			if(msg.getReceiver().equals(this.owner) && msg.getReceiver().isFriendsWith(msg.getSender())) {
				
				unread.push(msg);
				msg.setTimeStampReceived(time);
				
			}
		}
		
		for(Message msg : unread) {
			
			if(server.msgs.contains(msg)) {
				
				server.msgs.remove(msg);
				server.currentSize = server.currentSize-msg.getLength();
				
			}
		}
	}
	
	/**
	 * This method is for reading a certain amount of messages from 
	 * the unread stack. If the num parameter is 0, then all 
	 * messages in unread should be read. If the number of messages 
	 * in unread is less than read, still all messages should be read.
	 * @param num
	 * @param time
	 * @return number of messages which are read.
	 */
	public int readMessages(int num, int time) {
		
		int msgnum = 0;
		int t = time;
		
		if(num > 0) {
			
			while(!unread.isEmpty() && msgnum < num) {
			
				Message msg = unread.pop();
				read.add(msg);
				msg.setTimeStampRead(t);
				t++;
				msgnum++;
			
			}
			
		}else if(num == 0) {
			
			while(!unread.isEmpty()) {
				
				Message msg = unread.pop();
				read.add(msg);
				msg.setTimeStampRead(t);
				t++;
				msgnum++;
				
			}
		}
		
		if(msgnum == 0) {
			
			return 1;
			
		}else {
			
			return msgnum;
			
		}	
	}
	
	/**
	 * This method is for reading a specified sender’s messages.
	 * @param sender
	 * @param time
	 * @return
	 */
	public int readMessages(User sender, int time) {
		
		int msgnum = 0;
		int t = time;
		
		Iterator<Message> itr = unread.iterator();
		Stack<Message> tempStack = new Stack<Message>();
		
		while(itr.hasNext()) {
			
			Message msg = itr.next();
			
			if(msg.getSender().equals(sender)) {
				
				tempStack.add(msg);
				msgnum++;
				
			}
		}
		
		while(!tempStack.isEmpty()) {
			
			tempStack.peek().setTimeStampRead(t);
			read.add(tempStack.pop());
			t++;
			
		}
		
		Iterator<Message> itr2 = read.iterator();
		
		while(itr2.hasNext()) {
			
			Message msg = itr2.next();
			
			if(msg.getSender().equals(sender)) {
				
				unread.remove(msg);
				
			}
		}
		
		if(msgnum == 0) {
			
			return 1;
			
		}else {
			
			return msgnum;
			
		}
	}
	
	/**
	 * When this method is called, the message with the ID number 
	 * msgId should be read, if it exists.
	 * @param msgId
	 * @param time
	 */
	public void readMessage(int msgId, int time) {
		
		int t = time;
		
		Iterator<Message> itr = unread.iterator();
		
		while(itr.hasNext()) {
			
			Message msg = (Message) itr.next();
			
			if(msg.getId() == msgId) {
				
				msg.setTimeStampRead(t);
				unread.remove(msg);
				read.add(msg);
				t++;
				
			}
		}
		
		
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
