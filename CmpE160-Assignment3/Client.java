// separator	Client.java_0_false.txt
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

// separator	Client.java_1_true.txt

package question;

/**
 * The class representing the client side consists
 * of simple implementation of the cache mechanism. 
 * 
 * @author Furkan Ozdemir
 *
 */
public class Client{
	
	private class CachedContent{
		
		/**
		 * The name of the domain.
		 */
		String domainName;
		/**
		 * IP address for the requested web domain 
		 */
		String ipAddress;
		/**
		 * Hit number of web server
		 */
		int hitNo;
		
	}
	
	/**
	 * The root of DNS Tree.
	 */
	DnsTree root;
	/**
	 * Client's IP adress 
	 */
	String ipAddress;
	/**
	 * The cache of a client is presented with a finite-size array.
	 */
	CachedContent[] cacheList;
	
	/**
	 * Client class constructor
	 * @param ipAddress IP adress of client
	 * @param root root of DNS Tree.
	 */
	public Client(String ipAddress, DnsTree root) {
		
		this.ipAddress = ipAddress;
		this.root = root;
		cacheList = new CachedContent[10];
		
	}
	
	/**
	 * the method for sending request to DNS  
	 * 
	 * @param domainName name of the domain
	 * @return the IP address of the requested domain name.
	 */
	public String sendRequest(String domainName){
		
		for(int i=0; i<10; i++) {
			
			if(cacheList[i].domainName == domainName) {
				
				cacheList[i].hitNo ++;
				return cacheList[i].ipAddress;
				
			}
			
		}
		
		return root.queryDomain(domainName);
		
	}
	/**
	 * After obtaining an IP address through sendRequest method, 
	 * it should be added to the cache by this method.
	 * @param domainName name of the domain
	 * @param ipAddress IP address of the domain
	 */
	public void addToCache(String domainName, String ipAddress) {
		
		CachedContent cc = cacheList[0];
		int k = 0;
		
		for(int i=1; i<10; i++) {
			
			if(cacheList[i].hitNo < cc.hitNo) {
				
				k = i;
				
			}
		}
		
		cacheList[k].domainName = domainName;
		cacheList[k].ipAddress = ipAddress;
		cacheList[k].hitNo = 1;
		
	}
	
}



// separator	Client.java_2_false.txt
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

