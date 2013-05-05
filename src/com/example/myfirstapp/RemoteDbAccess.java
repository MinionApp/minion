package com.example.myfirstapp;

import com.amazonaws.services.simpledb;
import com.amazonaws.AmazonWebServiceRequest;

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
	
	/**
	 * Determines if a user's login credentials are valid.
	 * 
	 * @param username, the submitted username
	 * @param password, the submitted password
	 * @return if the login succeeded
	 */
	public static boolean loginAttempt(String username, String password) {
		// Prevent generic logins.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		// Get attributes from the database; GetAttributesResult intermediate
		GetAttributesRequest userRequest = new GetAttributesRequest("User", username);
		List<Attribute> attributes = db.getAttributes(userRequest).getAttributes();
		
		// Iterate through attributes, see if password matches.
		for (Attribute attr : attributes) {
			if (attr.getName().equals("password") && password.equals(attr.getValue())) {
				return true;
			}
		}
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
	public static boolean updateLoginCredentials(String username, String password) {
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
		List<ReplaceableAttribute> attrs = new ArrayList(1);
		attrs.add(passwordAttr);
		
		// Continue with add. username is itemName().
		PutAttributesRequest putUser = new PutAttributesRequest("User", username, attrs);		
		db.putAttributes(putUser);
		return true;
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
		
		// Get attributes from the database; GetAttributesResult intermediate
		GetAttributesRequest userRequest = new GetAttributesRequest("User", username);
		List<Attribute> attributes = db.getAttributes(userRequest).getAttributes();
		
		boolean questionMatch = false;
		boolean answerMatch = false;
		// Iterate through attributes, see if question and answer match.
		for (Attribute attr : attributes) {
			if (attr.getName().equals("question") && question.equals(attr.getValue())) {
				questionMatch = true;
			}
			if (attr.getName().equals("answer") && answer.equals(attr.getValue())) {
				answerMatch = true;
			}
		}
		return questionMatch && answerMatch;
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
	public static boolean updateSecurityQuestion(String username, String question, String answer) {
		// Prevent generic logins.
		if (username.equals(publicCharactersUserName)) {
			return false;
		}
		
		// Requires statement permits no checks for preexisting username.
		
		// Create attribute list.
		ReplaceableAttribute questionAttr = new ReplaceableAttribute("question", question, Boolean.TRUE);
		ReplaceableAttribute answerAttr = new ReplaceableAttribute("answer", answer, Boolean.TRUE);
		List<ReplaceableAttribute> attrs = new ArrayList(2);
		attrs.add(questionAttr);
		attrs.add(answerAttr);
		
		// Continue with add. username is itemName().
		PutAttributesRequest putUser = new PutAttributesRequest("User", username, attrs);		
		db.putAttributes(putUser);
		return true;
	}
	
	// Unimplemented way to delete user credentials, cleanup data, but unimportant at present.
	private static boolean eraseUser(String username);
	
	
	
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































