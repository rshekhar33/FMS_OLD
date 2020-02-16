package com.url.app.utility;

/**
 * Application Constants.
 * 
 * @author Shekhar Shinde
 */
public class AppConstant {

	private AppConstant() {
		throw new IllegalStateException("Utility class");
	}

	public static final Integer SYSTEM_USER_ID = 1;

	public static final Integer ACTIVE = 1;
	public static final Integer INACTIVE = 0;

	/* Application response status success/fail/error/warn */
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	/* Regex for validation */
	public static final String REGEX_ALPHABET_CHAR_1 = "[a-zA-Z]+";
	public static final String REGEX_ALPHABET_CHAR_2 = "[a-zA-Z]*";
	public static final String REGEX_RESTRICTED_CHAR_1 = "\\W";
	public static final String REGEX_RESTRICTED_CHAR_2 = "^[\\w\\.@]+$";
	public static final String REGEX_RESTRICTED_CHAR_3 = "^[\\w\\.@ ]+$";
	public static final String REGEX_NUMERIC_ONLY = "\\d+";

	/* Common Settings type */
	public static final String CS_TYPE_COURSE_TYPE_CODE_COUNTER = "coursetypecode_counter";
	public static final String CS_TYPE_COURSE_CODE_COUNTER = "coursecode_counter";
	public static final String CS_TYPE_REGISTRATION_ROLE = "registration_role";

	public static final String BLANK_STRING = "";
	public static final String COURSE_TYPE_CODE_PREFIX = "CT";

	/* File upload max size */
	public static final int MAX_UPLOAD_SIZE_PER_FILE_IN_BYTES = 5 * 1024 * 1024;// 5MB
	public static final int MAX_UPLOAD_SIZE_IN_BYTES = 2 * MAX_UPLOAD_SIZE_PER_FILE_IN_BYTES;

	/* Spring settings */
	public static final String SPRING_SECURITY_MSG_FILE_BASENAME = "messages/springsecurity-messages";
	public static final String SPRING_VALIDATION_MSG_FILE_BASENAME = "classpath:messages/validation-message";
	public static final String SPRING_APP_MSG_FILE_BASENAME = "classpath:messages/app-message.properties";
	public static final String SPRING_VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
	public static final String SPRING_VIEW_RESOLVER_SUFFIX = ".jsp";

	/* Spring Security ignore patterns */
	public static final String[] SPRING_SECURITY_IGNORE_PATTERNS = new String[] { "/images/**", "/js/**", "/css/**", "/fonts/**", "/resources/**" };
}