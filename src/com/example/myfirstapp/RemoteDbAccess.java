package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

/**
 * RemoteAccess contains the methods needed to access our remote database in Amazon's simpledb.
 * All remote database transactions go through this class.
 * 
 * @author Preston Sahabu (sahabp)
 */

public final class RemoteDbAccess {
	private static final AWSCredentials credentials = new BasicAWSCredentials("AKIAJNV4D2XYIO5CP63A",
																			  "kKwsT10/KrOWM94glAdVgIYemap5VeaCgoJurowL");
	private static final AmazonSimpleDBClient db = new AmazonSimpleDBClient(credentials);
	private static final String publicCharactersUserName = "__generic__";
	

	
	//public void createDomain(String domainName) {
	//	sdb.createDomain(new CreateDomainRequest(domainName));
	//}
	
	// Forks off Get tasks to be done independent of UI.
	private static class GetAttributesTask extends AsyncTask<String, Void, List<Attribute>> {
		//private ProgressDialog dialog;
		private Context context;
		private String username;
		private String password;
		private boolean keepLoggedIn;
		
		private List<Attribute> attrs = new ArrayList<Attribute>();

		
		private GetAttributesTask(String username, String password, boolean keepLoggedIn, Context con) {
			this.username = username;
			this.password = password;
			this.keepLoggedIn = keepLoggedIn;
			this.context = con;
		}
		
		//@Override
		//protected void onPreExecute() {
        //    dialog = new ProgressDialog(context);
        //    dialog.setCancelable(true);
        //    dialog.setMessage("uploading...");
        //    dialog.show();
		//}
		
		@Override
		protected void onPostExecute(List<Attribute> result) {
			super.onPostExecute(result);
			Intent intent;
			boolean correctLoginCredentials = false;
			for (Attribute attr : attrs) {
				if (attr.getName().equals("password") && password.equals(attr.getValue())) {
					correctLoginCredentials = true;
				}
			}
			// Login succeeds, go to homepage.
			if (correctLoginCredentials) {
				intent = new Intent(context, HomeActivity.class);
					
				// Stores the username into preferences.
				if (keepLoggedIn) {
					SaveSharedPreference.setUserName(context, username);
				}
				
			// Login fails, go to login failure.
			} else {
				// TODO: needs to specify login failure somehow,
				// either separate Activity or something within this Activity.
				intent = new Intent(context, HomeActivity.class); 
			}
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//dialog.dismiss();
			context.getApplicationContext().startActivity(intent);
		}
		
		@Override
		protected List<Attribute> doInBackground(String... username) {	
			String user = username[0];
			GetAttributesRequest userRequest = new GetAttributesRequest("User", user);
			GetAttributesResult response = db.getAttributes(userRequest);
			attrs = response.getAttributes();
			return attrs;
		}
	}
	
	// Forks off Put tasks to be done independent of UI.
	private static class PutAttributesTask extends AsyncTask<String, Void, Void> {
		//private ProgressDialog dialog;
		private Context context;
		private String activity;
		private List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		
		private String user;
		
		private PutAttributesTask(List<ReplaceableAttribute> attributes, String activity, Context con) {
			this.attrs = attributes;
			this.activity = activity;
			this.context = con; 
		}
		
		//@Override
		//protected void onPreExecute() {
        //    dialog = new ProgressDialog(context);
        //    dialog.setCancelable(true);
        //    dialog.setMessage("uploading...");
        //    dialog.show();
		//}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Intent intent;
			if(activity.equals("signup")) {
				intent = new Intent(context, SecurityQuestionActivity.class);
		    	intent.putExtra("username", user);
			} else { // activity.equals("password reset") AND activity.equals("security question")
				intent = new Intent(context, LoginActivity.class);
			}
			//dialog.dismiss();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.getApplicationContext().startActivity(intent);
		}
		
		@Override
		protected Void doInBackground(String... username) {
			//if(activity.equals("security question")) {
			//	user = username[0];
			//	PutAttributesRequest putUser = new PutAttributesRequest("User", user, attrs);		
			//	db.putAttributes(putUser);
			//}
			user = username[0];
			PutAttributesRequest putUser = new PutAttributesRequest("User", user, attrs);		
			db.putAttributes(putUser);
			return null;
		}
	}
	
	/**
	 * Determines if a user's login credentials are valid.
	 * 
	 * @param username, the submitted username
	 * @param password, the submitted password
	 * @return if the login succeeded
	 */
	public static boolean loginAttempt(String username, String password, boolean keepLoggedIn, String activity,  Context context) {
		// Prevent generic logins.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		// Get attributes from the database.
		GetAttributesTask task = new GetAttributesTask(username, password, keepLoggedIn, context);
		task.execute(username);
		return false;
	}
	
	/**
	 * Adds a user's login credentials to the database, and also supports changing passwords.
	 * 
	 * @param username, the submitted username
	 * @param password, the submitted password
	 * @return if the credentials were successfully updated
	 */
	// Potential hazard: only key/values are unique, not keys. *sigh*
	// Potential hazard: individual submits lots of new login requests
	public static boolean updateLoginCredentials(String username, String password, String activity, Context context) {
		// Prevent generic logins.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		/* Not necessary, since simpledb will create new key/overwrite as appropriate.
		// Iterate through attributes if existing passwords shouldn't be overwritten.
		if (!changing) {
			// Request data and iterate.
			GetAttributesRequest userRequest = new GetAttributesRequest("User", username);
			List<Attribute> attributes = db.getAttributes(userRequest).getAttributes();
			for (Attribute attr : attributes) {
				if (attr.getName().equals("itemName()") && username.equals(attr.getValue())) {
					return false;
				}
			}
		}
		*/
		
		// Create attribute list.
		ReplaceableAttribute passwordAttr = new ReplaceableAttribute("password", password, Boolean.TRUE);
		List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(passwordAttr);
		
		// Continue with add. username is itemName().
		PutAttributesTask task = new PutAttributesTask(attrs, activity, context);
		task.execute(username);
		return true;
	}
	
	/**
	 * Returns a user's security question.
	 * 
	 * @param username, the submitted username
	 * @return the security question string
	 */
	public static String getSecurityQuestion(String username, String activity, Context context, Activity a) {
		// Prevent generic checks.
		if (username.equals(publicCharactersUserName)) {
			return null;
		}
		
		// Get attributes from the database.
		GetAttributesTask task = new GetAttributesTask(username, "", false, context);
		task.execute(username);
		return null; // error
	}
	
	/**
	 * Determines if a user has successfully answered their security question.
	 * 
	 * @param username, the submitted username
	 * @param q, defining the security question
	 * @param answer, the submitted answer
	 * @return if the question was successfully answered
	 */
	public static boolean securityQuestionTest(String username, String question, String answer) {
		// Prevent generic checks.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		// Get attributes from the database.
		
		try {
			GetAttributesTask task = new GetAttributesTask(answer, answer, false, null);
			List<Attribute> attributes;
			attributes = task.execute(username).get();
			// Iterate through attributes, see if question and answer match.
			for (Attribute attr : attributes) {
				if (attr.getName().equals("answer") && answer.equals(attr.getValue())) {
					return true;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	/**
	 * Adds a user's login credentials to the database, and also supports changing questions.
	 * 
	 * @requires should only occur on inital account creation.
	 * @param username, the submitted username
	 * @param question, the submitted question
	 * @param answer, the submitted answer
	 * @return if the credentials were successfully updated
	 */
	public static boolean updateSecurityQuestion(String username, String question, String answer, String activity, Context context) {
		// Prevent generic logins.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		// Requires statement permits no checks for preexisting username.
		
		// Create attribute list.
		ReplaceableAttribute questionAttr = new ReplaceableAttribute("question", question, Boolean.TRUE);
		ReplaceableAttribute answerAttr = new ReplaceableAttribute("answer", answer, Boolean.TRUE);
		List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(questionAttr);
		attrs.add(answerAttr);
		
		// Continue with add. username is itemName().
		PutAttributesTask task = new PutAttributesTask(attrs, activity, context);
		task.execute(username);
		return true;
	}
	
	// Unimplemented way to delete user credentials, cleanup data, but unimportant at present.
	private static boolean eraseUser(String username) { return false; }
	
	
	
}

// username may not be __generic__
// methods to do:
// user login
// character storage for user
// get characters for user
// 

// db schema;
// Users: username -> password, security question, sq answer
// UserCharacters: username -> charactername, charactername...
// Character[Stats]: username_charactername -> stat1, stat2, stat3...
// Groups: groupname -> username, username, ...

// __Modify tables are key|value lists, literally stored that way.































