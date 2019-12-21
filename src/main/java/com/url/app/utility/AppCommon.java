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
	 * Check whether string is null or empty.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean isEmpty(final String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * Check whether integer is a positive number.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean isPositiveInteger(final Integer val) {
		return val != null && val > 0;
	}

	/**
	 * Check whether string is not a number.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean isNotNumber(final String str) {
		boolean isNotNumber = true;
		try {
			Double.parseDouble(str);
			isNotNumber = false;
		} catch (NumberFormatException nfe) {
		}

		return isNotNumber;
	}

	/**
	 * Convert String to Integer. If String is invalid then returns 0.
	 * 
	 * @param str the string which is to be converted.
	 * @return integer from string.
	 */
	public static final Integer toInteger(final String str) {
		Integer val = 0;
		try {
			val = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
		}

		return val;
	}

	/**
	 * Convert String to Integer. If String is invalid then returns null.
	 * 
	 * @param str the string which is to be converted.
	 * @return integer from string.
	 */
	public static final Integer toIntegerOrNull(final String str) {
		Integer val = null;
		try {
			val = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
		}

		return val;
	}

	/**
	 * Check whether string is not a valid email address.
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean isNotValidEmail(final String str) {
		return !AppConstant.PATTERN_VALID_EMAIL.matcher(str).matches();
	}

	/**
	 * Check whether string has only alphabets or not.
	 * Allow only alphabets (a-zA-Z)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean hasOnlyAlphabets(final String str) {
		return AppConstant.PATTERN_ALPHABET_CHAR.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters and underscore. (a-zA-Z0-9_)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean hasRestrictedChar1(final String str) {
		return AppConstant.PATTERN_RESTRICTED_CHAR_1.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters and some special characters. (a-zA-Z0-9_.@)
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean hasRestrictedChar2(final String str) {
		return !AppConstant.PATTERN_RESTRICTED_CHAR_2.matcher(str).matches();
	}

	/**
	 * Check whether string has restricted characters.
	 * Allow only alphanumeric characters, spaces and some special characters. (a-zA-Z0-9_.@ )
	 * 
	 * @param str the string which is to be checked.
	 * @return true/false.
	 */
	public static final boolean hasRestrictedChar3(final String str) {
		return !AppConstant.PATTERN_RESTRICTED_CHAR_3.matcher(str).matches();
	}
}