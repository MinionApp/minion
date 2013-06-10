package uw.cse403.minion.test;

import junit.framework.TestCase;
import uw.cse403.minion.AccountUtils;

/**
 * Black box tests for account functions:
 * Handle validation of passwords and asynchronous http requests
 * to the database.
 * 
 * @author Mary Jones (mlidge)
 *
 */
public class AccountUtilsTest extends TestCase {
	private static final String VALID_USERNAME = "test";
	private static final String VALID_PASSWORD = "abcDEF123@";
	private static final String VALID_PASSWORD2 = "abcDEF123#";
	private static final String INVALID_CREDENTIAL = "failure";
	private static final String UNUSED_USERNAME = "marytest";
	private static final String PHP_DELETE = "http://homes.cs.washington.edu/~mlidge/deleteUser.php";
	private static final String COLOR = "blue";
	private static final String SPINNER_QUESTION_1 = "Was ist ihre Lieblingsfarbe?";
	
	private AccountUtils account;
	
	@Override
	public void setUp() {
		account = new AccountUtils();	
	}
	
	/**
	 * Test that a valid password is valid and an invalid one is not.
	 */
	public void testValidPassword() {
		boolean valid = account.validPassword(VALID_PASSWORD);
		assertTrue(valid);
		valid = account.validPassword(INVALID_CREDENTIAL);
		assertFalse(valid);
	}

	/**
	 * Test to ensure correct return values for password pairs.
	 */
	public void testMatchingPasswords() {
		boolean match = account.matchingPasswords(VALID_PASSWORD, VALID_PASSWORD);
		assertTrue(match);	
		match = account.matchingPasswords(VALID_PASSWORD, INVALID_CREDENTIAL);
		assertFalse(match);
	}
	
	/**
	 * Check to see if a user can log in or not appropriately given the combinations of :
	 * Valid Username/Valid Password
	 * Invalid Username/Valid Password
	 * Valid Username/Invalid Password
	 * Invalid Username/Invalid Password
	 */
	public void testCheckLogin() {
		boolean valid = account.checkLogin(VALID_USERNAME, VALID_PASSWORD);
		assertTrue(valid);
		valid = account.checkLogin(UNUSED_USERNAME, VALID_PASSWORD);
		assertFalse(valid);
		valid = account.checkLogin(UNUSED_USERNAME, INVALID_CREDENTIAL);
		assertFalse(valid);
		valid = account.checkLogin(VALID_USERNAME, INVALID_CREDENTIAL);
		assertFalse(valid);
	}
	
	/**
	 * Ensure that given credentials a signup can be completed
	 */
	public void testSignUp() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
		
	}
	
	/**
	 * Test getting a security question given a username
	 */
	public void testGetSecurityQuestion() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		String questionActual = account.getSecurityQuestion(UNUSED_USERNAME);
		questionActual = questionActual	.replace("_", " ");
		assertTrue(questionActual.equals(SPINNER_QUESTION_1));
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
	}
	
	/**
	 * Test resetting a password
	 */
	public void testResetPassword() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		success = account.resetPassword(UNUSED_USERNAME, VALID_PASSWORD2);
		assertTrue(success);
		success = account.checkLogin(UNUSED_USERNAME, VALID_PASSWORD2);
		assertTrue(success);
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
	}
	
	/**
	 * Test checking that an answer to a security question is valid
	 */
	public void testCheckAnswerValid() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		success = account.checkAnswer(UNUSED_USERNAME, SPINNER_QUESTION_1, COLOR);
		assertTrue(success);
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
	}
	
	/**
	 * Test checking that an invalid answer to a security question is invalid.
	 */
	public void testCheckAnswerInvalid() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		success = account.checkAnswer(UNUSED_USERNAME, SPINNER_QUESTION_1, "brown");
		assertFalse(success);
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
	}

}
