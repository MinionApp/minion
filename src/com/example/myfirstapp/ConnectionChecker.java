package com.example.myfirstapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** Utility to check if the device has Internet connectivity.
 * 
 * @author Elijah Elefson (elefse)
 *
 */
public class ConnectionChecker {
	
	/**
	  * Checks if the device has Internet connection.
	  * 
	  * @return true if the device is connected to the Internet.
	  */
	public static boolean hasConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	}
}
