package com.sendify.server;

import java.util.Map;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class AndroidAccountManager {

	 /*
     * Creates a AccountManager object with a connection object as its arguement.
     */
	private AccountManager manager;
	private XMPPConnection connection;
	
    public AndroidAccountManager(XMPPConnection connection) {
   	    manager = new AccountManager(connection);
   	    this.connection = connection;
    }

    /*
     * Uses the AccountManager class method to create new user account
     */
    public String createAccount(String username, String password) throws XMPPException{
	   	 manager.createAccount(username, password);
	   	 String JID = username+"@"+connection.getServiceName();
	   	 return JID;
    }

    /*
     * Uses the AccountManager class method to create new user account
     * takes username, password and a list of attributes as arguements
     * username - unique entity of the user on host
     * password
     * attributes - Additional Info about the user
     * List of attributes is --
     * name - user's name
     * first - user's first name
     * last - user's last name
     * email - user's email address
     * city - user's city
     * state - user's state
     * zip - user's zip code
     * phone - user's phone number
     * url - user's website url
     * date - the date of registration took place
     * misc - other miscellaneous information to associate with the account
     * text - textual information to associate with the account
     *
     * returns JID if account created
     * else returns corresponding error message
     */
    public String createAccount(String username, String password, Map<String,String> attributes) throws XMPPException{
   	 try {
   		 manager.createAccount(username, password, attributes);
   		 String JID = username+"@sendify";
   		 return JID;
   	 	} catch(XMPPException e){    
   	 		return null;
   	 	}
    }

    /*
     * Uses the AccountManager class method to get user attributes.
     * Returns the value of attribute with given name, else null.
     * User needs to be logged in
     */
    public void getAccountAttribute(String name){
   	  manager.getAccountAttribute(name);
    }

    /*
     * Uses the AccountManager class method to get user attributes.
     * Returns an unmodifiable collection of the names of the required account attributes.
     * user needs to be logged in
     */
    public void getAccountAttributes(){
   	  manager.getAccountAttributes();
    }

    /*
     * Uses the AccountManager class method to change the password of current logged in user
     * password - new password of current logged in user
     * return true if password changed successfully
     * else return corresponding error message
     */
    public void changePassword(String newPassword) throws XMPPException{
   	 manager.changePassword(newPassword);
    }

    /*
     * Uses the AccountManager class method to delete the currently logged-in account from the server.
     */
    public void deleteAccount() throws XMPPException{
   	 manager.deleteAccount();
    }
}
