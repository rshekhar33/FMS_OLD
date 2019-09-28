package com.url.app.utility;

import java.util.regex.Pattern;

/**
 * Application Constants.
 * 
 * @author Shekhar Shinde
 */
public class AppConstant {
	public static final Integer SYSTEM_USER_ID = 1;

	public static final Integer ACTIVE = 1;
	public static final Integer INACTIVE = 0;

	/* Application response status success/fail/error/warn */
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	/* Patterns for validation */
	public static final Pattern PATTERN_VALID_EMAIL = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
	public static final Pattern PATTERN_ALPHABET_CHAR = Pattern.compile("[a-zA-Z]+");
	public static final Pattern PATTERN_RESTRICTED_CHAR_1 = Pattern.compile("\\W");
	public static final Pattern PATTERN_RESTRICTED_CHAR_2 = Pattern.compile("^[\\w\\.@]+$");
	public static final Pattern PATTERN_RESTRICTED_CHAR_3 = Pattern.compile("^[\\w\\.@ ]+$");

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
	public static final String SPRING_SECURITY_MSG_FILE_BASENAME = "springsecurity-messages";
	public static final String SPRING_VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
	public static final String SPRING_VIEW_RESOLVER_SUFFIX = ".jsp";

	/* Spring Security ignore patterns */
	public static final String[] SPRING_SECURITY_IGNORE_PATTERNS = new String[] { "/images/**", "/js/**", "/css/**", "/fonts/**", "/resources/**" };
}