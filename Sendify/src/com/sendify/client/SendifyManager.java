/**
 * 
 */
package com.sendify.client;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.sendify.server.AndroidAccountManager;
import com.sendify.server.ConnectionManager;

import android.content.Context;

/**
 * @author Nikzz
 *
 */
public class SendifyManager {

	private final String HOST;
	private final String SERVICE;
	private final int PORT;
	private String API_KEY;
	private static Context context;
	
	private ConnectionManager connManager;
	private AndroidAccountManager accManager;
	
	
	public SendifyManager(String API_KEY){
		this.API_KEY = API_KEY;
		this.HOST = "162.242.243.182";
		this.SERVICE = "sendify";
		this.PORT = 5222;
	}
	
	
	/*
	 * Method that initialize the AndroidSmack library
	 * Context - context of the running application
	 */
	public void init(Context context){
		this.context = context;
		connManager = new ConnectionManager(context);
	}
	
	
	/*
	 * Method to connect to the HOST server
	 * find the SERVICE name based on the API_KEY
	 * 
	 */
	public void connect() throws XMPPException{
		connManager.connect(this.API_KEY);
	}
	
	
	/*
	 * Method to Login to HOST server
	 * username - Username of the registered user
	 * password - Password of the registered user
	 * return true on success
	 * else return the corressponding error messages
	 */
	public void login(String username, String password) throws XMPPException{
		connManager.login(username, password);
	}
	

	/*
	 * Account Manager Class 
	 */
	
	/*
	 * Method to create new user account
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
	public void createAccount(String username, String password, Map<String,String> attributes) throws XMPPException{
		
	}
	
	
	/*
	 * return the value of attribute with given name, else null.
	 * User needs to be logged in
	 */
	public void getAccountAttributes(String name){
		
	}
	
	
	/*
	 * Returns an unmodifiable collection of the names of the required account attributes.
	 * user needs to be logged in
	 */
	public void getAccountAttributes(){
		
	}
	
	
	/*
	 * changes the password of current logged in user
	 * password - new password of current logged in user
	 * return true if password changed successfully
	 * else return corresponding error message
	 */
	public void changePassword(String password) throws XMPPException{
		
	}
	
	
	/*
	 * Deletes the currently logged-in account from the server.
	 */
	public void deleteAccount() throws XMPPException{
		
	}
	
	
	/*
	 * PubSub Manager Class
	 */
	
	/*
	 * create a new node
	 */
	public void createNode(String nodeName){
		
	}
	
	/*
	 * get the node object with a specified name
	 */
	public void getNode(String nodeName){
		
	}
	
	/*
	 * UNDEFINED
	 * connect to a node
	 */
	public void connectNode(){
		
	}
	
	/*
	 * Get all the nodes that exist as a child of the current specified nodes
	 */
	public void getChildNode(String nodeId){
		
	}
	
	/*
	 * get subscriptions on the root node
	 * user needs to logged in
	 */
	public void getSubscriptions(){
		
	}
	
	
	/*
	 * return the buddy list of current logged in user
	 */
	public Hashtable getBuddyList(){
		Roster roster = connManager.getConnection().getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		Hashtable<String, String> rost = new Hashtable<String,String>();
 
		System.out.println("\n\n" + entries.size() + " buddy(ies):");
		for(RosterEntry r:entries)
		{
			String cur_user = r.getUser();
			
			Presence p = roster.getPresence(cur_user);
			
			String status = null;
			
			if(p.getMode()==Presence.Mode.available){
				status = "Available";
			}
			
			else if(p.getMode()==Presence.Mode.away){
				status = "Away";
			}
			
			else if(p.getMode()==Presence.Mode.dnd){
				status = "Do not disturb";
			}
			
			else if(p.getMode()==Presence.Mode.xa){
				status = "Away for extended";
			}
			
			rost.put(cur_user, status);
			
			System.out.println(cur_user +":" +p.getStatus());
		}
		
		return rost;
	}
	
	
}
