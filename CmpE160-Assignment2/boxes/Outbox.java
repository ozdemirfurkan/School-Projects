// separator	Outbox.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Outbox.java_1_true.txt

package boxes;

import java.util.*;

import elements.*;

public class Outbox extends Box{
	
	/**
	 * A queue which contains messages sent by user.
	 */
	public Queue<Message> sent = new LinkedList<Message>();
	
	/**
	 * Outbox class constructor
	 * @param owner owner of the outbox
	 */
	public Outbox(User owner) {
		
		this.owner = owner;
		
	}
	
	
	
}


// separator	Outbox.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

