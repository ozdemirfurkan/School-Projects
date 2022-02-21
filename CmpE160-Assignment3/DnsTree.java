// separator	DnsTree.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	DnsTree.java_1_true.txt

package question;

import java.util.*;

/**
 * This class represents the main DNS structure.
 * @author Furkan Ozdemir
 *
 */
public class DnsTree{
	
	/**
	 * root of the tree
	 */
	DnsNode root;
	
	/**
	 * DnsTree class constructor.
	 */
	public DnsTree() {
		
		root = new DnsNode();
		
	}
	
	/**
	 * This method inserts a new record for a given domain name.
	 * @param domainName the name of the domain
	 * @param ipAddress IP address of the domain
	 */
	public void insertRecord(String domainName, String ipAddress) {
		
		String name = domainName;
		
		if(domainName.charAt(0) != '.') {
			
			domainName = "." + domainName;
			
		}
		
		DnsNode dn = root;
		
		while(domainName.contains(".")) {
			
			String key = domainName.substring(domainName.lastIndexOf('.')+1);
			domainName = domainName.substring(0, domainName.lastIndexOf('.'));
			
			if(dn.childNodeList.containsKey(key)) {
			
				dn = dn.childNodeList.get(key);
			
			}else {
			
				dn.childNodeList.put(key, new DnsNode());
				dn = dn.childNodeList.get(key);
				
			}
		}
		
		dn.ipAddresses.add(ipAddress);
		dn.roundRobin.add(ipAddress);
		dn.validDomain = true;
		dn.domainName = name;
		
	}
	/**
	 * It removes the node with the given domainName from the tree.
	 * @param domainName name of the domain
	 * @return If successfully removed, return true, otherwise, return false. 
	 */
	public boolean removeRecord(String domainName) {
				
		if(domainName.charAt(0) != '.') {
			
			domainName = "." + domainName;
			
		}
		
		DnsNode dn = root;
		DnsNode dnroot = null;
		String key = "";
		
		while(domainName.contains(".")) {
			
			key = domainName.substring(domainName.lastIndexOf('.')+1);
			domainName = domainName.substring(0, domainName.lastIndexOf('.'));
			
			if(dn.childNodeList.containsKey(key)) {
				
				dnroot = dn;
				dn = dn.childNodeList.get(key);
				
			}else {
				
				return false;
				
			}	
		}
		
		dn.ipAddresses.clear();
		dn.roundRobin.clear();
		dn.validDomain = false;
		
		if(dn.childNodeList.isEmpty()) {
			
			dnroot.childNodeList.remove(key);
			
		}
		
		return true;
		
	}
	
	/**
	 * It removes the given ipAddress of a DNS node with the given domainName.
	 * @param domainName the name of the domain
	 * @param ipAddress IP address of the domain
	 * @return If successfully removed, return true, otherwise, return false. 
	 */
	public boolean removeRecord(String domainName, String ipAddress) {
		
		if(domainName.charAt(0) != '.') {
			
			domainName = "." + domainName;
			
		}
		
		DnsNode dn = root;
		
		while(domainName.contains(".")) {
			
			String key = domainName.substring(domainName.lastIndexOf('.')+1);
			domainName = domainName.substring(0, domainName.lastIndexOf('.'));
			
			if(dn.childNodeList.containsKey(key)) {
				
				dn = dn.childNodeList.get(key);
				
			}else {
				
				return false;
				
			}	
		}
		
		if(!dn.ipAddresses.contains(ipAddress)) {
			
			return false;
			
		}else if(dn.ipAddresses.size() == 1) {
			
			removeRecord(domainName);
			
		}else {
			
			dn.ipAddresses.remove(ipAddress);
			dn.roundRobin.remove(ipAddress);
			return true;
			
		}
		
		return true;
		
	}
	/**
	 * It queries a domain name within the DNS ,
	 * following the Round Robin mechanism.
	 * @param domainName the name of the domain
	 * @return the next IP address of the domainName
	 */
	public String queryDomain(String domainName) {
		
		if(domainName.charAt(0) != '.') {
			
			domainName = "." + domainName;
			
		}
		
		DnsNode dn = root;
		
		while(domainName.contains(".")) {
			
			String key = domainName.substring(domainName.lastIndexOf('.')+1);
			domainName = domainName.substring(0, domainName.lastIndexOf('.'));
			
			if(dn.childNodeList.containsKey(key)) {
				
				dn = dn.childNodeList.get(key);
				
			}else {
				
				return null;
				
			}	
		}
		
		String nextIP = dn.roundRobin.poll();
		dn.roundRobin.add(nextIP);
		return nextIP;
		
	}
	
	/**
	 * 
	 * @return all the valid domain names in the DNS mechanism 
	 * with at least 1 IP address.
	 */
	public Map<String, Set<String>> getAllRecords(){
		
		Map<String, Set<String>> allRecords = new HashMap<String, Set<String>>();
		
		DnsNode dn = root;
		
		while(dn != null) {
			
			if(dn.validDomain == true) {
				
				allRecords.put(dn.domainName, dn.ipAddresses);
				
			}
			
			Iterator<DnsNode> itr = dn.childNodeList.values().iterator(); 
			
			while(itr.hasNext()) {
				
				dn = itr.next();
			
			}
		}
		return allRecords;
		
	}
	
}



// separator	DnsTree.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

