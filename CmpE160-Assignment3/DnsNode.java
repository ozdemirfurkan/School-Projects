// separator	DnsNode.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	DnsNode.java_1_true.txt

package question;

import java.util.*;

/**
 * This class represents each of the nodes in the DNS tree structure. 
 * @author Furkan Ozdemir
 *
 */

public class DnsNode{
	
	/**
	 * This field is utilized for maintaining the tree structure.
	 * The keys are represented as strings, and the values are DNS nodes.
	 */
	Map<String, DnsNode> childNodeList;
	/**
	 * It shows whether the current node is a valid domain name 
	 * or just a subdomain that cannot have any IP address. 
	 */
	boolean validDomain;
	/**
	 * This set is for storing the IP addresses as a list of a domain name.
	 */
	Set<String> ipAddresses;
	/**
	 * A queue for Round Robin mechanism.
	 */
	Queue<String> roundRobin;
	/**
	 * The name of the domain
	 */
	String domainName;
	
	/**
	 * DnsNode class constructor
	 */
	public DnsNode(){
		
		this.childNodeList = new HashMap<String, DnsNode>();
		this.ipAddresses = new HashSet<String>();
		this.validDomain = false;
		this.roundRobin = new LinkedList<String>();
		
	}
}



// separator	DnsNode.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

