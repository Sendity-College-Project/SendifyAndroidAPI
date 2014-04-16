package com.sendify.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.AndroidConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.Subscription;

import android.content.Context;
import android.util.Log;

public class Sendify {

	private static final String LOG = "SendifyAndroid";
	private static boolean ACTIVATE = false;
	
	private static String HOST;
	private static String SERVICE;
	private static int PORT = -1;
	private final String API_KEY;
	
	private static XMPPConnection connection;
	private static Context context = null;
	private static AndroidConnectionConfiguration connConfig;
	private static AccountManager accManager;
	private static PubSubManager pubsub;
	
	public Sendify(String API_KEY,Context context){
		this.API_KEY = API_KEY;
		Sendify.HOST = "162.242.243.182";
		Sendify.SERVICE = this.getService(API_KEY);
		Sendify.PORT = 5222;
		Sendify.context = context;
		SmackAndroid.init(context);
		if(validate()){
			ACTIVATE = true;
			Log.d(LOG, "Sendify().......Successfully");
		} else{
			Log.e(LOG, "Sendify().......Failed");
		}
	}	
	
	public void connect() {
		connConfig = new AndroidConnectionConfiguration(Sendify.HOST, Sendify.PORT, Sendify.SERVICE);
		connection = new XMPPConnection(connConfig);
		
		if(Sendify.context!=null){				
			try {
				connection.connect();
				Log.d(LOG, "Sendify.connect().......Successfully");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				Log.d(LOG, "Sendify.connect().......Failed due to XMPPException");
				e.printStackTrace();
			}
		} else{
			Log.d(LOG, "Sendify.connect().......failed due to wront context set in the constructor");
		}
	}
	
	public void login(String username, String password) {
		if(ACTIVATE){
			try {
				connection.login(username, password);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	

	public void createAccount(String username, String password, Map<String,String> attributes) {
		if(Sendify.connection!=null){
			accManager = new AccountManager(connection);
			try {
				if(accManager.supportsAccountCreation())
					accManager.createAccount(username, password, attributes);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getAccountAttributes(String name){
		String attribute = null;
		if(accManager!=null){
			attribute = accManager.getAccountAttribute(name);
		}
		return attribute;
	}
	
	public ArrayList<String> getAccountAttributes(){
		ArrayList<String> list = new ArrayList<String>();
		if(accManager!=null){
			list = (ArrayList<String>) accManager.getAccountAttributes();
		}
		return list;
	}
	
	public void deleteAccount(){
		if(accManager!=null){
			try {
				accManager.deleteAccount();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void createChannel(String nodeName){
		if(ACTIVATE && nodeName!=null){
			pubsub = new PubSubManager(connection);
			try {
				pubsub.createNode(nodeName);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * get the node object with a specified name
	 */
	public LeafNode getNode(String nodeName){
		if(pubsub!=null){
			LeafNode node = null;
			try {
				node = pubsub.getNode(nodeName);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return node;
		}else{
			return null;
		}
	}
	
	/*
	 * UNDEFINED
	 * connect to a node
	 */
	public void connectNode(){
		
	}
	
	public HashMap<String,String> getSubscriptions(){
		List<Subscription> list = null;
		HashMap<String, String> hashmap = new HashMap<String,String>();
		if(pubsub!=null){
			try {
				list = pubsub.getSubscriptions();
				Iterator<Subscription> iterator = list.iterator();
				while(iterator.hasNext()){
					Subscription s = iterator.next();
					hashmap.put(s.getId(), s.getJid());
				}
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hashmap;
	}
	
	public void deleteNode(String nodeName){
		if(pubsub!=null){
			try {
				pubsub.deleteNode(nodeName);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Hashtable getBuddyList(){
		Roster roster = connection.getRoster();
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
	
	
	//---------------------- Helper Function -----------------------------
	public String getService(String API_KEY){
		String service = "sendify";
		return service;
	}
	public boolean validate(){
		boolean var1=true,var2=true,var3=true;
		if(Sendify.HOST==null){
			var1 = false;
			Log.d(LOG, "Missing Host Name From Declaration.........");
		}
		if(Sendify.SERVICE == null){
			var2 = false;
			Log.d(LOG, "Missing Service Name From Declaration.........");
		}
		if(Sendify.PORT == -1){
			var3 = false;
			Log.d(LOG, "Missing Port Number From Declaration.........");
		}
		if(var1 && var2 && var3){
			return true;
		} else{
			return false;
		}			
	}
}
