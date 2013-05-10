package com.example.myfirstapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/** Utility to check if the device has Internet connectivity.
 * 
 * @author Elijah Elefson (elefse)
 *
 */
public class ConnectionChecker {
   // private static ConnectionChecker instance = new ConnectionChecker();
   // static Context context;
   // ConnectivityManager connectivityManager;
   // NetworkInfo wifiInfo, mobileInfo;
   // boolean connected = false;
    
    //public static ConnectionChecker getInstance(Context ctx) {
      //  context = ctx;
      //  return instance;
    //}
	
	/**
	  * Checks if the device has Internet connection.
	  * 
	  * @return true if the device is connected to the Internet.
	  */
	public static boolean hasConnection(Context context) {
	/*	try {
            connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isAvailable() &&
                networkInfo.isConnected();
        return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }*/
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
