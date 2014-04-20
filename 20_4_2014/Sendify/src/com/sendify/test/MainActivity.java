package com.sendify.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jivesoftware.smack.AndroidConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.sendify.client.R;
import com.sendify.client.Sendify;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Sendify sendify = new Sendify("123456789", getApplicationContext());
        sendify.connect();
        sendify.login("newuser", "password");
        
        /*HashMap<String, String> attr = new HashMap<String, String>(); 
		attr.put("user", "sub"); 
		attr.put("password", "sub");
        sendify.createAccount("newuser", "password", attr);*/ // create account test code
        
//        String s = sendify.getAccountAttributes("username");
//        Log.d("TAG", s);  // getAccountAttribute test code
        
        /*Collection<String> list = sendify.getAccountAttributes();
        Iterator<String> i =list.iterator();
        while(i.hasNext()){
        	Log.d("tag", i.next());
        }*/ // Test for getAccountAttributes()
        
        //sendify.deleteAccount();
        
        String channelName = "datops";
        //sendify.deleteNode(channelName);
        //sendify.publish(channelName, "Hello!!Sendify");
        //sendify.createChannel(channelName);
        //sendify.subscribe(channelName);
        //sendify.subscribe("chinar@sendify", channelName);
        //sendify.getSubscriptions();
        //sendify.deleteNode(channelName);
        //sendify.getChannel(channelName);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
