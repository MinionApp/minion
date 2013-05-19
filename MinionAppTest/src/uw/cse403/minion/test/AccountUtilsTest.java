package uw.cse403.minion.test;

import junit.framework.TestCase;
import uw.cse403.minion.AccountUtils;

public class AccountUtilsTest extends TestCase {
	private static final String VALID_USERNAME = "test";
	private static final String VALID_PASSWORD = "abcDEF123@";
	private static final String INVALID_CREDENTIAL = "failure";
	private static final String UNUSED_USERNAME = "marytest";
	private static final String PHP_DELETE = "http://homes.cs.washington.edu/~mlidge/deleteUser.php";
	private static final String COLOR = "blue";
	private static final String SPINNER_QUESTION_1 = "What is your favorite color?";
	
	private AccountUtils account;
	
	@Override
	public void setUp() {
		account = new AccountUtils();	
	}
	
	public void testValidPassword() {
		boolean valid = account.validPassword(VALID_PASSWORD);
		assertTrue(valid);
		valid = account.validPassword(INVALID_CREDENTIAL);
		assertFalse(valid);
	}

	public void testMatchingPasswords() {
		boolean match = account.matchingPasswords(VALID_PASSWORD, VALID_PASSWORD);
		assertTrue(match);
		match = account.matchingPasswords(VALID_PASSWORD, INVALID_CREDENTIAL);
		assertFalse(match);
	}

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
	
	public void testSignUp() {
		boolean success = account.startSignupTask(UNUSED_USERNAME, VALID_PASSWORD, VALID_PASSWORD, SPINNER_QUESTION_1,
				COLOR);
		assertTrue(success);
		success = account.deleteUser(UNUSED_USERNAME);
		assertTrue(success);
		
	}

}
