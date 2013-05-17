package uw.cse403.minion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Utility to save the state of whether or not the user wishes to remain logged into
 * the application even after completely exiting the application.
 * @author Elijah Elefson (elefse)
 */
public class SaveSharedPreference {
	static final String PREF_USER_NAME= "username";

	/**
	 * Returns a SharedPreferences object that stores the login state for the given context.
	 * @param ctx The context of an activity.
	 * @return a SharedPreferences object that contains the login state of the user.
	 */
    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /**
     * Sets the username stored in the SharedPreferences object.
     * @param context The context of an activity.
     * @param userName the username of the user wanting to remain logged in.
     */
    public static void setUserName(Context context, String userName) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    /**
     * Retreives the username stored in the SharedPreferences object.
     * @param context The context of an activity.
     * @return the username if the user has chosen to stay logged in, an empty string otherwise.
     */
    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }
}
