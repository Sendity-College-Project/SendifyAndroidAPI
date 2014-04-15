package com.sendify.server;

import org.jivesoftware.smack.AndroidConnectionConfiguration;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.content.Context;
import android.util.Log;

public class ConnectionManager {

	private String HOST;
	private String SERVICE;
	private int PORT;
	private XMPPConnection connection;
	private static final String LOGTAG = LogUtils.makeLogTag(ConnectionManager.class);
	
	/*
	 * 
	 */
	public ConnectionManager(Context con){
		SmackAndroid.init(con);
		Log.d(LOGTAG, "ConnectionManager()......");
	}
	
	/*
	 * 
	 */
	public void connect(String API_KEY) throws XMPPException{
		Log.d(LOGTAG, "ConnectionManager.connect()......");
		AndroidConnectionConfiguration connConfig = new AndroidConnectionConfiguration(HOST, PORT, SERVICE);
		connection = new XMPPConnection(connConfig);
		connection.connect();
		
		//Log.i(LOGTAG, "user connected to the server");
	}
	
	/*
	 * 
	 */
	public void login(String username, String password) throws XMPPException {
		if(connection!=null && username !=null && password != null)
			connection.login(username, password);
	}
	
	/*
	 * 
	 */
	public XMPPConnection getConnection(){
		return this.connection;
	}
}
