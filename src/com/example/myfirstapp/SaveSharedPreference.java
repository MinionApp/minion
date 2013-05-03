package com.example.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

// is used to save the state of whether or not the user wishes to remain
// logged in even after completely exiting the app
public class SaveSharedPreference {
	static final String PREF_USER_NAME= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context context, String userName) 
    {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context context)
    {
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }
}
