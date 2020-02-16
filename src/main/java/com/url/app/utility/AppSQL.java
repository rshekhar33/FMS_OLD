package com.url.app.utility;

public class AppSQL {

	private AppSQL() {
		throw new IllegalStateException("Utility class");
	}

	//@formatter:off
	/* sql queries */
	public static final String QRY_SELECT_URL_ROLE_ID = "select distinct new com.url.app.dto.UrlRolesBean(a.actionPath, r.roleId) "
			+ "from RolePrivilegeRelation rpr "
			+ "inner join rpr.id.role r "
			+ "inner join rpr.id.privilege p "
			+ "inner join p.actions a "
			+ "where rpr.isActive=1 and r.isActive=1 and p.isActive=1 and a.isSkip=0 and a.isActive=1"
			+ "order by a.actionPath asc";

	public static final String QRY_SELECT_ACTIONS = "from Action a "
			+ "order by a.isSkip, a.actionPath asc";

	public static final String QRY_SELECT_USER_ROLES_BY_USERNAME = "from User u "
			+ "join fetch u.userRoleRelations urr "
			+ "where u.userName=:userName "
			+ "order by u.userId asc";

	public static final String QRY_UPDATE_USER_LAST_SUCCESS_LOGIN_DATE = "update User "
			+ "set failedAttemptCnt=:failedAttemptCnt, "
			+ "lastSuccessfulLoginDate=:lastSuccessfulLoginDate "
			+ "where userId=:userId";

	public static final String QRY_UPDATE_USER_LAST_FAILED_LOGIN_DATE = "update User "
			+ "set failedAttemptCnt=failedAttemptCnt+1, "
			+ "lastFailedLoginDate=:lastFailedLoginDate "
			+ "where userName=:userName";

	public static final String QRY_SELECT_USER_ROLES_BY_USERID = "from User u "
			+ "left join fetch u.userRoleRelations urr "
			+ "where u.userId=:userId "
			+ "order by u.userId asc";

	public static final String QRY_SELECT_USER_SKILLSETS = "from User u "
			+ "left join fetch u.facultySkillsets fs "
			+ "where u.userId=:userId "
			+ "order by u.userId asc";

	public static final String QRY_SELECT_COMMON_SETTING_VALUE = "select cs.value "
			+ "from CommonSetting cs "
			+ "where cs.type=:type "
			+ "order by cs.orderNumber asc";

	public static final String QRY_UPDATE_COMMON_SETTING_VALUE = "update CommonSetting "
			+ "set value=value+1 "
			+ "where type=:type";

	/* pl/sql Procedures */
	public static final String PROC_ALL_USERS = "get_all_users";

	public static final String PROC_FACULTY_SKILLSETS = "get_faculty_skillsets";
	//@formatter:on
}