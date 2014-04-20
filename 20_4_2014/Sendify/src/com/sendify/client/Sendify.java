package com.sendify.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.AndroidConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Sendify {

	private static final String LOG = "SendifyAndroid";
	
	private static String HOST= null;
	private static String SERVICE = null;
	private static int PORT = -1;
	private static String API_KEY = null;
	
	private static boolean ACTIVATE = false;
	private static boolean CONNECTED = false;
	private boolean isLogin = false;
	
	private static XMPPConnection connection = null;
	private static Context context = null;
	
	public Sendify(String API_KEY,Context context){
		this.API_KEY = API_KEY;
		Sendify.HOST = "162.242.243.182";
		Sendify.SERVICE = this.getService(API_KEY);
		Sendify.PORT = 5222;
		Sendify.context = context;
		if(validate()){
			ACTIVATE = true;
			Log.d(LOG, "Sendify().......Successfully");
		} else{
			Log.e(LOG, "Sendify().......Failed");
		}
	}	
	
	public void connect() {
		if(!ACTIVATE){
			Log.d(LOG, "Constructor instantiation failed. Recall the constructor");
			return;
		}
		SmackAndroid.init(context);
		AndroidConnectionConfiguration connConfig = new AndroidConnectionConfiguration(Sendify.HOST, Sendify.PORT, Sendify.SERVICE);
		connection = new XMPPConnection(connConfig);
		
		Log.d(LOG, "Host Name : "+Sendify.HOST+" Port Number : "+Sendify.PORT+" Service is : "+Sendify.SERVICE);		
		try {
			connection.connect();
			CONNECTED = true;
			Log.d(LOG, "Sendify.connect().......Successfully");
		} catch (XMPPException e) {
			Log.d(LOG, "Sendify.connect().......Failed due to XMPPException");
			Log.d(LOG, "Error Message is : "+e.toString());
		}
	}
	
	public void login(String username, String password) {
		if(CONNECTED){
			try {
				connection.login(username, password);
				isLogin  = true;
				Log.d(LOG, "Sendify.login().......Successfully");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				Log.d(LOG, "Sendify.connect().......Failed due to XMPPException");
				Log.d(LOG, "Error is "+ e.toString());
			}
		} else{
			Log.d(LOG, "Sendify.login().......failed due to no connection with the server");
		}
	}	

	public void createAccount(String username) {
		
		HashMap<String, String> attributes = new HashMap<String, String>(); 
		attributes.put("Name", username);		
		if(username==null){
			Log.d(LOG, "Given input are invalid. Value can not be null");
			return;
		}
		String password = username;
		if(CONNECTED){
			AccountManager accManager = new AccountManager(connection);
			try {
				if(accManager.supportsAccountCreation()){
					accManager.createAccount(username, password, attributes);
					Log.d(LOG, "Sendify.createAccount().......Successfully");
				} else{
					Log.d(LOG, "Account creation is not supported on your account");
				}
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(LOG, "Sendify.createAccount().......Failed due to XMPPException");
			}
		} else{
			Log.d(LOG, "Sendify.createAccount().......failed due to device is not connected to server");
		}
	}
	
	public String getAccountAttributes(String name){
		String attribute = null;
		if(isLogin){
			AccountManager accManager = new AccountManager(connection);		
			attribute = accManager.getAccountAttribute(name);
			Log.d(LOG, "Sendify.getAccountAttributes().......Successfully");
		} else {
			AccountManager accManager = new AccountManager(connection);
			attribute = accManager.getAccountAttribute(name);
			Log.d(LOG, "Sendify.getAccountAttributes().......Successfully");
		}
		return attribute;
	}
	
	public Collection<String> getAccountAttributes(){
		Collection<String> list = new ArrayList<String>();
		if(isLogin){
			AccountManager accManager = new AccountManager(connection);
			list = accManager.getAccountAttributes();
			Log.d(LOG, list.toString());
		} else{
			AccountManager accManager = new AccountManager(connection);
			list = accManager.getAccountAttributes();
			Log.d(LOG, list.toString());
		}
		return list;
	}
	
	public void createChannel(String nodeName){
		if(nodeName == null){
			Log.d(LOG,"passed argument can not be null");
			return;
		}
		if(isLogin) {
			PubSubManager pubsub = new PubSubManager(connection);
			try {
				LeafNode node = pubsub.getNode(nodeName);
				if(node != null){
					Log.d(LOG, "Node already exist");
					return;
				}
			} catch (XMPPException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
//				Log.d(LOG, "Node already exist");
//				return;
			}
			try {
				pubsub.createNode(nodeName);
				Log.d(LOG, "Channel with name : "+nodeName+" create successfully");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				Log.d(LOG, "Channel creation failed and error is : "+e.toString());
			}
		} else {
			Log.d(LOG, "Login to create a new channel");
		}
	}
	
	public LeafNode getChannel(String nodeName){
		if(nodeName == null){
			Log.d(LOG, "Passes argument can not be null");
			return null;
		}
		if(isLogin){
			LeafNode node = null;
			PubSubManager pubsub = new PubSubManager(connection);
			try {
				node = pubsub.getNode(nodeName);
				Log.d(LOG, "Node retrived successfully and Node name is : "+node.getId());
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				Log.d(LOG, "Error !! Node could not be retrieved");
				Log.d(LOG,"Error is : "+e.toString());
			}
			return node;
		}else{
			Log.d(LOG, "Protected Area. Open only of logined user");
			return null;
		}
	}
	
	/*
	 * delete the current existing node
	 * String nodeName : name of node that needs to be deleted
	 * user that created the node can only delete that node
	 */
	public void deleteNode(String nodeName){
		if(isLogin){
			PubSubManager pubsub = new PubSubManager(connection);
			try {
				pubsub.deleteNode(nodeName);
				Log.d(LOG, "Node Deleted Successfully");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void publish(String nodeName,String message){
		// Create a pubsub manager using an existing Connection
        PubSubManager pubSubManager = new PubSubManager(this.connection);
        
        SimplePayload payload = new SimplePayload("elementname","pubsub:testnode:elementname","<elementname>Hello World!</elementname>");
        
        PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>(null, payload);        
        
        LeafNode node;
		
        try {
			// Get the node
			node = (LeafNode) pubSubManager.getNode(nodeName);
			Log.d(LOG, node.toString() + " retrieved succesfully.");
			// Publish an Item with payload
			node.publish(item);
			Log.d(LOG, item.toString() + " published succesfully.");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Log.d(LOG, ex.toString());
		}       
	}
	
	public void subscribe(String nodeName) {
		
		// Create a pubsub manager using an existing Connection
        PubSubManager pubSubManager = new PubSubManager(this.connection);

        // Get the node
        LeafNode node;
        String user = this.connection.getUser();
        if(user!=null && ACTIVATE && !isSubscribed(nodeName)) {
			try {
				node = pubSubManager.getNode(nodeName);
				Log.d(LOG, node.toString() + " retrieved succesfully.");
				node.subscribe(user);
				Log.d(LOG, "Node subscribed succesfully.");
				node.addItemEventListener(new ItemEventCoordinator());
				Log.d(LOG, "ItemEventListener added succesfully.");				
			} catch (XMPPException ex) {
				// TODO Auto-generated catch block
				Log.d(LOG, ex.toString());
			}
		}	
    }
	
	private boolean isSubscribed(String nodeName){
		if(ACTIVATE){
			List<Subscription> list;
			try {
				PubSubManager pubsub = new PubSubManager(connection);
				list = pubsub.getSubscriptions();
				Iterator<Subscription> iterator = list.iterator();
				boolean is = false;
				while(iterator.hasNext()){
					Subscription s = iterator.next();
					if(s.getJid().equalsIgnoreCase(nodeName)){
						is = true;
						break;
					}
				}
				return is;
			} catch (XMPPException e) {
				return false;
			}					
		} else{
			return false;
		}
	}
	
	public void subscribe(String user,String nodeName) {
		
		// Create a pubsub manager using an existing Connection
        PubSubManager pubSubManager = new PubSubManager(this.connection);

        // Get the node
        LeafNode node;
        if(user!=null) {
			try {
				node = pubSubManager.getNode(nodeName);
				Log.d(LOG, node.toString() + " retrieved succesfully.");
				node.addItemEventListener(new ItemEventCoordinator());
				Log.d(LOG, "ItemEventListener added succesfully.");
				node.subscribe("chinar@sendify");
				Log.d(LOG, "Node subscribed succesfully.");
			} catch (XMPPException ex) {
				// TODO Auto-generated catch block
				Log.d(LOG, ex.toString());
			}
		}	
    }
    
    class ItemEventCoordinator implements ItemEventListener<Item> {
		@Override
		public void handlePublishedItems(ItemPublishEvent items) {
			// TODO Auto-generated method stub
			System.out.println("Item: " + items.getItems().toString());
            System.out.println(items);
//            SubsData sData = new SubsData(items.getItems().toString());
//            MessageObject mObject = new MessageObject(this, sData);            
		}
    } 

	
	public HashMap<String,String> getSubscriptions(){
		List<Subscription> list = null;
		HashMap<String, String> hashmap = new HashMap<String,String>();
		if(ACTIVATE){
			PubSubManager pubsub = new PubSubManager(connection);
			try {
				list = pubsub.getSubscriptions();
				Iterator<Subscription> iterator = list.iterator();
				while(iterator.hasNext()){
					Subscription s = iterator.next();
					hashmap.put(s.getId(), s.getJid());
					Log.d(LOG, "Subscription ID is : "+s.getId()+ " and JID of subscriber is : "+s.getJid());
				}
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hashmap;
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
		boolean var1=true,var2=true,var3=true, var4=true;
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
		if(Sendify.context==null){
			var4 = false;
			Log.d(LOG, "Missing Application Context From Declaration.........");
		}
		if(var1 && var2 && var3){
			return true;
		} else{
			return false;
		}			
	}
}
