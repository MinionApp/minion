package uw.cse403.minion;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.content.Context;
import android.os.AsyncTask;

/**
 * AccountUtils is a collection of asynchronous tasks and 
 * validators used to manipulate user accounts.
 * @author Mary Jones (mlidge)
 */
public class AccountUtils {
	/** Class constants for string representations **/
	private static final String PHP_ADDRESS_SIGNUP = 
			"http://homes.cs.washington.edu/~elefse/signup.php";
	private static final String PHP_ADDRESS_CHECK = 
			"http://homes.cs.washington.edu/~elefse/checkLogin.php";
	private static final String PHP_ADDRESS_DELETE = 
			"http://homes.cs.washington.edu/~mlidge/deleteUser.php";
	private static final String PHP_ADDRESS_SECURITY_QUESTION = 
			"http://homes.cs.washington.edu/~elefse/getSecurityQuestion.php";
	private static final String PHP_ADDRESS_RESET = 
			"http://homes.cs.washington.edu/~elefse/resetPassword.php";
	private static final String PHP_ADDRESS_CHECK_ANSWER = 
			"http://homes.cs.washington.edu/~elefse/checkAnswer.php";

	/**
	 *  The regex that will only allow string with the following qualities:
	 *  	1.) Must be at least 8 characters long
	 *  	2.) Must contains at least 1 numerical digit
	 *  	3.) Must contain at least 1 lowercase character
	 *  	4.) Must contain at least 1 uppercase character
	 *  	5.) Must contain at least 1 of the special symbols @#$%^&+=
	 *  	6.) Must contain no spaces
	 */
	private static final String PASSWORD_PATTERN = 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	/**
	 * Checks that the given password is a valid password.
	 * @param password The password the user input.
	 * @return true if password is formatted correctly, false otherwise.
	 */
	public boolean validPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		return pattern.matcher(password).matches();
	}

	/**
	 * Checks that the given password and the given confirmation password match one another.
	 * @param password The password the user input
	 * @param passwordConfirmation The confirmation password the user input
	 * @return true if password and confirmation password match, false otherwise.
	 */
	public boolean matchingPasswords(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

	/**
	 * Checks that the given username is valid and not empty.
	 * @param username The username the user input
	 * @return true if username does not consist of only whitespace, false otherwise.
	 */
	public boolean validUsername(String username) {
		return !username.trim().equals("");
	}

	/**
	 * A wrapper method for the SignUpTask AsyncTask
	 * @param username The user's chosen username
	 * @param password The user's inputted password
	 * @param passwordConfirmation The confirmation of the inputted password
	 * @param question The chosen security question
	 * @param answer The answer to the security question
	 * @return a boolean representing a successful signin
	 */
	public boolean startSignupTask(String username, String password, String passwordConfirmation, String question, 
			String answer) {
		SignupTask task = new SignupTask(username, password, passwordConfirmation, question, answer);
		try {
			String returnVal = task.execute(username).get();
			return returnVal.equals("1");
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
	 * A wrapper method for the CheckLoginTask AsyncTask
	 * @param username the inputed username
	 * @param password the inputed password
	 * @return a boolean representing a successful login
	 */
	public boolean checkLogin(String username, String password) {
		CheckLoginTask check = new CheckLoginTask(username, password);
		try {
			String resVal = check.execute("").get();
			return resVal.equals("1");
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
	 * Helper method to delete users from the remote database and thus eliminate
	 * after the remote database has been populated by testing processes.
	 * @param username The username of the user to be deleted
	 * @return true if the user is successfully deleted, false otherwise
	 */
	public boolean deleteUser(String username) {
		DeleteTask task = new DeleteTask(username);
		try {
			String res = task.execute("").get();
			return res.equals("1");
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
	 * A wrapper method for the GetSecurityQuestionTask AsyncTask
	 * @param username The inputed username
	 * @return a string representing the security question
	 */
	public String getSecurityQuestion(String username) {
		GetSecurityQuestionTask task = new GetSecurityQuestionTask(username);
		String retVal;
		try {
			retVal = task.execute("").get();
			return retVal;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * A wrapper method for the ResetPasswordTask AsyncTask
	 * @param username The inputed username
	 * @param password The inputted password
	 * @return true if the password is reset, false otherwise
	 */
	public boolean resetPassword(String username, String password) {
		ResetPasswordTask task = new ResetPasswordTask(username, password);
		try {
			String res = task.execute("").get();
			return res.equals("1");
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
	 * A wrapper method for the CheckAnswerTask AsyncTask
	 * @param username The inputed username
	 * @param question The security question that is being queried against
	 * @param answer The inputed security answer
	 * @return true if the answer is correct for the user's security question, false otherwise
	 */
	public boolean checkAnswer(String username, String question, String answer) {
		CheckAnswerTask task = new CheckAnswerTask(username, question, answer);
		String res;
		try {
			res = task.execute("").get();
			return res.equals("1");
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
	 * SignupTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It uploads the data given by the
	 * user in the signup form to the remote database if the username they chose is not
	 * already in use and then directs them to the correct Activity. 
	 */
	private class SignupTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;
		private String passwordConfirmation;
		private String question;
		private String answer;
		private Context context;

		/**
		 * Constructs a new SignupTask object.
		 * @param username The user given username
		 * @param password The user given password
		 * @param passwordConfirmation The user given password confirmation
		 * @param question The user given security question
		 * @param answer The user given security answer
		 * @param context The current Activity's context
		 */
		private SignupTask (String username, String password, String passwordConfirmation, String question, String answer) {
			this.un = username;
			this.pw = password;
			this.passwordConfirmation = passwordConfirmation;
			this.question = question;
			this.answer = answer;
			//this.context = context;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));
			postParameters.add(new BasicNameValuePair("password", pw));
			postParameters.add(new BasicNameValuePair("question", question));
			postParameters.add(new BasicNameValuePair("answer", answer));

			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_SIGNUP, postParameters);
				res = result.toString();    
				res = res.replaceAll("\\s+", "");   
			} catch (Exception e) {   
				res = e.toString();
			}
			return res;
		}
	}

	private class DeleteTask extends AsyncTask<String, Void, String> {
		private String un;

		private DeleteTask (String username) {
			this.un = username;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));
			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_DELETE, postParameters);
				res = result.toString();    
				res = res.replaceAll("\\s+", "");   
			} catch (Exception e) {   
				res = e.toString();
			}
			return res;
		}
	}

	/**
	 * CheckLoginTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It checks if the user provided 
	 * login credentials are correct and then directs to the correct Activity based on the
	 * result.
	 */
	private class CheckLoginTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;

		/**
		 * Constructs a new CheckLoginTask object.
		 * @param username The user given username
		 * @param password The user given password
		 * @param keepLoggedIn If the user has selected to remain logged in or not
		 * @param context The current Activity's context
		 */
		private CheckLoginTask (String username, String password) {
			this.un = username;
			this.pw = password;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));
			postParameters.add(new BasicNameValuePair("password", pw));

			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_CHECK, postParameters);
				res = result.toString();   
				res = res.replaceAll("\\s+", "");    
			} catch (Exception e) {  
				res = e.toString();
			}
			return res;
		}
	}

	/**
	 * GetSecurityQuestionTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It takes the given username and retrieves the
	 * corresponding security question for that user from the remote database. This information
	 * is then passed on to the next Activity.
	 */
	private class GetSecurityQuestionTask extends AsyncTask<String, Void, String> {
		private String un;

		/**
		 * Constructs a new GetSecurityQuestionTask object.
		 * @param username The user given username
		 * @param context The current Activity's context
		 */
		private GetSecurityQuestionTask (String username) {
			this.un = username;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));

			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_SECURITY_QUESTION, postParameters);
				res = result.toString();  
				res = res.replaceAll("\\s+", "");    
			} catch (Exception e) {  
				res = e.toString();
			}
			return res;
		}
	}

	/**
	 * ResetPasswordTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It updates the user's password to a valid
	 * new one in the event that they forgot their original one and then directs to the correct
	 * Activity. 
	 */
	private class ResetPasswordTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;

		/**
		 * Constructs a new ResetPasswordTask object.
		 * @param username The user given username
		 * @param password The user given password
		 * @param passwordConfirmation The user given password confirmation
		 * @param context The current Activity's context
		 */
		private ResetPasswordTask (String username, String password) {
			this.un = username;
			this.pw = password;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));
			postParameters.add(new BasicNameValuePair("password", pw));

			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_RESET, postParameters);
				res = result.toString();    
				res = res.replaceAll("\\s+", "");   
			} catch (Exception e) {  
				res = e.toString();
			}
			return res;
		}
	}

	/**
	 * CheckAnswerTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It takes the given username and answer
	 * to the previously provided security question and checks to make sure these are correct
	 * and then allows the user access to the password reset stage of the password recovery
	 * process.
	 */
	private class CheckAnswerTask extends AsyncTask<String, Void, String> {
		private String un;
		private String question;
		private String answer;

		/**
		 * Constructs a new CheckAnswerTask object.
		 * @param username The user given username
		 * @param question The question received from the remote database that corresponds
		 * 		  to the given username
		 * @param answer The user given answer to the security question
		 * @param context The current Activity's context
		 */
		private CheckAnswerTask (String username, String question, String answer) {
			this.un = username;
			this.question = question;
			this.answer = answer;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", un));
			postParameters.add(new BasicNameValuePair("question", question));
			postParameters.add(new BasicNameValuePair("answer", answer));

			String result = null;

			//http post
			String res;
			try{
				result = CustomHttpClient.executeHttpPost(PHP_ADDRESS_CHECK_ANSWER, postParameters);
				res = result.toString();   
				res = res.replaceAll("\\s+", "");    
			} catch (Exception e) {   
				res = e.toString();
			}
			return res;
		}
	}
}
