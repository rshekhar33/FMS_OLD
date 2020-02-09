package com.url.app.utility;

/**
 * Application's URL and view names
 * 
 * @author Shekhar Shinde
 */
public class AppUrlView {
	public static final String REDIRECT = "redirect:/";

	/* URL root paths */
	public static final String URL_ROOT_DASHBOARD = "dashboard";
	public static final String URL_ROOT_USER = "user";
	public static final String URL_ROOT_MODULE = "module";
	public static final String URL_ROOT_COURSE_TYPE = "courseType";
	public static final String URL_ROOT_ROLE = "role";
	public static final String URL_ROOT_FACULTY_SKILLSET = "facultySkillset";

	/* URL paths */
	public static final String URL_ROOT = "/";
	public static final String URL_LOGIN = "login";
	public static final String URL_LOGIN_CHECK = "loginCheck";
	public static final String URL_LOGOUT = "logout";
	public static final String URL_HOME = "home";
	public static final String URL_SIGN_UP = "signUp";
	public static final String URL_ACCESS_DENIED = "accessDenied";
	public static final String URL_INVALID_SESSION = "invalidSession";
	public static final String URL_SESSION_EXPIRED = "sessionExpired";
	public static final String URL_ERROR = "error";

	public static final String URL_LIST = "list";
	public static final String URL_ADD = "add";
	public static final String URL_UPDATE = "update";
	public static final String URL_ACTIVATION = "activation";
	public static final String URL_FETCH_DETAILS = "fetchDetails";
	public static final String URL_FETCH_DATA = "fetchData";
	public static final String URL_VALIDATE_SAVE = "validateSave";

	public static final String URL_DASHBOARD = "dashboard";

	/* URL redirect paths */
	public static final String REDIRECT_URL_HOME = REDIRECT + URL_HOME;
	public static final String REDIRECT_URL_DASHBOARD = REDIRECT + URL_ROOT_DASHBOARD + URL_ROOT + URL_DASHBOARD;

	public static final String REDIRECT_URL_USER_LIST = REDIRECT + URL_ROOT_USER + URL_ROOT + URL_LIST;
	public static final String REDIRECT_URL_USER_UPDATE = REDIRECT + URL_ROOT_USER + URL_ROOT + URL_UPDATE;

	public static final String REDIRECT_URL_ROLE_LIST = REDIRECT + URL_ROOT_ROLE + URL_ROOT + URL_LIST;
	public static final String REDIRECT_URL_ROLE_UPDATE = REDIRECT + URL_ROOT_ROLE + URL_ROOT + URL_UPDATE;

	public static final String REDIRECT_URL_MODULE_LIST = REDIRECT + URL_ROOT_MODULE + URL_ROOT + URL_LIST;
	public static final String REDIRECT_URL_MODULE_UPDATE = REDIRECT + URL_ROOT_MODULE + URL_ROOT + URL_UPDATE;

	public static final String REDIRECT_URL_COURSE_TYPE_LIST = REDIRECT + URL_ROOT_COURSE_TYPE + URL_ROOT + URL_LIST;
	public static final String REDIRECT_URL_COURSE_TYPE_UPDATE = REDIRECT + URL_ROOT_COURSE_TYPE + URL_ROOT + URL_UPDATE;

	public static final String REDIRECT_URL_FACULTY_SKILLSET_LIST = REDIRECT + URL_ROOT_FACULTY_SKILLSET + URL_ROOT + URL_LIST;
	public static final String REDIRECT_URL_FACULTY_SKILLSET_UPDATE = REDIRECT + URL_ROOT_FACULTY_SKILLSET + URL_ROOT + URL_UPDATE;

	/* View root paths */
	public static final String VIEW_ROOT_LOGIN_SIGN_UP = "loginSignUp/";
	public static final String VIEW_ROOT_OTHER = "other/";
	public static final String VIEW_ROOT_DASHBOARD = "dashboard/";
	public static final String VIEW_ROOT_USER = "user/";
	public static final String VIEW_ROOT_MODULE = "module/";
	public static final String VIEW_ROOT_COURSE_TYPE = "courseType/";
	public static final String VIEW_ROOT_ROLE = "role/";
	public static final String VIEW_ROOT_FACULTY_SKILLSET = "facultySkillset/";

	/* View paths */
	public static final String VIEW_LIST = "list";
	public static final String VIEW_CRUD = "crud";

	public static final String VIEW_LOGIN = VIEW_ROOT_LOGIN_SIGN_UP + "login";
	public static final String VIEW_SIGN_UP = VIEW_ROOT_LOGIN_SIGN_UP + "signUp";

	public static final String VIEW_ACCESS_DENIED = VIEW_ROOT_OTHER + "accessDenied";
	public static final String VIEW_APP_ERROR_PAGE = VIEW_ROOT_OTHER + "appErrorPage";
	public static final String VIEW_GLOBAL_ERROR_PAGE = VIEW_ROOT_OTHER + "globalErrorPage";
	public static final String VIEW_INVALID_SESSION = VIEW_ROOT_OTHER + "invalidSession";
	public static final String VIEW_SESSION_EXPIRED = VIEW_ROOT_OTHER + "sessionExpired";

	public static final String VIEW_DASHBOARD = VIEW_ROOT_DASHBOARD + "dashboard";

	public static final String VIEW_USER_LIST = VIEW_ROOT_USER + VIEW_LIST;
	public static final String VIEW_USER_CRUD = VIEW_ROOT_USER + VIEW_CRUD;

	public static final String VIEW_MODULE_LIST = VIEW_ROOT_MODULE + VIEW_LIST;
	public static final String VIEW_MODULE_CRUD = VIEW_ROOT_MODULE + VIEW_CRUD;

	public static final String VIEW_COURSE_TYPE_LIST = VIEW_ROOT_COURSE_TYPE + VIEW_LIST;
	public static final String VIEW_COURSE_TYPE_CRUD = VIEW_ROOT_COURSE_TYPE + VIEW_CRUD;

	public static final String VIEW_ROLE_LIST = VIEW_ROOT_ROLE + VIEW_LIST;
	public static final String VIEW_ROLE_CRUD = VIEW_ROOT_ROLE + VIEW_CRUD;

	public static final String VIEW_FACULTY_SKILLSET_LIST = VIEW_ROOT_FACULTY_SKILLSET + VIEW_LIST;
	public static final String VIEW_FACULTY_SKILLSET_CRUD = VIEW_ROOT_FACULTY_SKILLSET + VIEW_CRUD;
}