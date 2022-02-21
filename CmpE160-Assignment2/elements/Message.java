// separator	Message.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Message.java_1_true.txt

package elements;

public class Message{
	
	/**
	 * Number of total messages in the program. 
	 */
	private static int numOfMessages = 0;
	/**
	 * The IDs of the messages are determined by the order 
	 * they are created as consecutive integer values. 
	 * The first message sent in the program has the ID of 0, 
	 * the second message sent has the ID of 1 and so on.
	 */
	private int ID;
	/**
	 * Message body
	 */
	private String body;
	/**
	 * Sender of message.
	 */
	private User sender;
	/**
	 * Receiver of message.
	 */
	private User receiver;
	private int timeStampSent;
	private int timeStampRead;
	private int timeStampReceived;
	
	/**
	 * Message class constructor.
	 * @param sender sender of message
	 * @param receiver receiver of message
	 * @param body message body
	 * @param server 
	 * @param time
	 */
	public Message(User sender, User receiver, String body, Server server, int time) {
		
		this.sender = sender;
		this.receiver = receiver;
		this.body = body;
		this.timeStampSent = time;
		this.ID =numOfMessages;
		numOfMessages ++;
		
	}
	
	/**
	 * setter method for sent time stamp.
	 * @param time
	 */
	public void setTimeStampSent(int time) {
		
		this.timeStampSent = time;
		
	}
	
	/**
	 * setter method for read time stamp.
	 * @param time
	 */
	public void setTimeStampRead(int time) {
		
		this.timeStampRead = time;
		
	}
	
	/**
	 * setter method for received time stamp.
	 * @param time
	 */
	public void setTimeStampReceived(int time) {
		
		this.timeStampReceived = time;
		
	}
	
	/**
	 * getter method for sent time sent.
	 * @return timeStampSent
	 */
	public int getTimeStampSent() {
		
		return this.timeStampSent;
		
	}
	
	/**
	 * getter method for read time sent.
	 * @return timeStampRead
	 */
	public int getTimeStampRead() {
		
		return this.timeStampRead;
		
	}
	
	/**
	 * getter method for received time sent.
	 * @return timeStampReceived
	 */
	public int getTimeStampReceived() {
		
		return this.timeStampReceived;
		
	}
	
	/**
	 * getter method for ID
	 * @return ID
	 */
	public int getId() {
		
		return this.ID;
		
	}
	
	/**
	 * getter method for message body
	 * @return message body
	 */
	public String getBody() {
		
		return this.body;
		
	}
	
	/**
	 * getter method for length of message body.
	 * @return length
	 */
	public int getLength() {
		
		return this.body.length();
		
	}
	
	/**
	 * getter method for total number of messages.
	 * @return numOfMessages
	 */
	public int getNumOfMessages() {
		
		return numOfMessages;
		
	}
	
	/**
	 * getter method for sender
	 * @return sender
	 */
	public User getSender() {
		
		return sender;
		
	}
	
	/**
	 * getter method for receiver
	 * @return receiver
	 */
	public User getReceiver() {
		
		return receiver;
		
	}
	
	/**
	 *  If this message is longer than the other message, return a positive number.
	 *  Else if the other message is longer, return a negative number. 
	 *  Return 0 if both messages have the same number of characters.
	 * @param o Message object
	 */
	public int compareTo(Message o) {
		
		if(this.getLength() > o.getLength()) {
				
			return 1;
				
		}else if(this.getLength() < o.getLength()){
				
			return -1;
				
		}else {
			
			return 0;
			
		}	
	}
	
	/**
	 * Messages are equal if their ID's same.
	 */
	public boolean equals(Object o) {
		
		if(o instanceof Message) {
			
			if(this.getId() == ((Message)o).getId()) {
				
				return true;
				
			}else {
				
				return false;
				
			}
		}
		
		return false;
		
	}
	
	/**
	 * Each line should start with a tab. 'received' or 'read' should 
	 * be empty strings if the message has not been received/read yet.
	 */
	public String toString() {
		
		String message = "";
		if(this.timeStampReceived>0) {
			
			if(this.timeStampRead>0) {
				
				message = "	From: "+sender.getID()+" To: "+receiver.getID()+"\n"+"	Received: received Read: read"+"\n"+"	"+this.body;
				
			}else {
				
				message = "	From: "+sender.getID()+" To: "+receiver.getID()+"\n"+"	Received: received Read:  "+"\n"+"	"+this.body;
				
			}
			
		}else {
			
			message = "	From: "+sender.getID()+" To: "+receiver.getID()+"\n"+"	Received:  Read:  "+"\n"+"	"+this.body;
			
		}
		
		return message;
		
	}
	
}


// separator	Message.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

