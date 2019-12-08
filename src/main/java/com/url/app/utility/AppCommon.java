package com.url.app.utility;

import java.util.Date;

/**
 * Common functionalities of application.
 * 
 * @author Shekhar Shinde
 */
public class AppCommon {

	/**
	 * Get current date and time.
	 * 
	 * @return date object of current date and time.
	 */
	public static final Date currentDateTime() {
		return new Date();
	}

	/**
	 * Check whether string is not a number.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean isNotNumber(final String str) {
		boolean isNotNumber = false;
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			isNotNumber = true;
		}

		return isNotNumber;
	}

	/**
	 * Check whether string is not a valid email address.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean isNotValidEmail(final String str) {
		return !AppConstant.PATTERN_VALID_EMAIL.matcher(str).matches();
	}

	/**
	 * Check whether string has only alphabets or not.
	 * Allow only alphabets (a-zA-Z)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean hasOnlyAlphabets(final String str) {
		return AppConstant.PATTERN_ALPHABET_CHAR.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters and underscore. (a-zA-Z0-9_)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean hasRestrictedChar1(final String str) {
		return AppConstant.PATTERN_RESTRICTED_CHAR_1.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters and some special characters. (a-zA-Z0-9_.@)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean hasRestrictedChar2(final String str) {
		return !AppConstant.PATTERN_RESTRICTED_CHAR_2.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters, spaces and some special characters. (a-zA-Z0-9_.@ )
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false
	 */
	public static final boolean hasRestrictedChar3(final String str) {
		return !AppConstant.PATTERN_RESTRICTED_CHAR_3.matcher(str).matches();
	}
}