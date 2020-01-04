package com.url.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages/app-message.properties")
public class AppMessage {
	/**
	 * exceptions
	 */
	@Value("${exception.header}")
	public String exceptionHeader;

	@Value("${exception.header2}")
	public String exceptionHeader2;

	@Value("${exception.desc}")
	public String exceptionDesc;

	@Value("${exception.desc2}")
	public String exceptionDesc2;

	@Value("${exception.desc3}")
	public String exceptionDesc3;

	@Value("${login.error}")
	public String loginError;

	/**
	 * messages
	 */
	@Value("${mandatory.field.error}")
	public String mandatoryFieldError;

	@Value("${invalid.email.error}")
	public String invalidEmailError;

	@Value("${only.number.error}")
	public String onlyNumberError;

	@Value("${update.failed.error}")
	public String updateFailedError;

	/**
	 * user
	 */
	@Value("${user.does.not.exist}")
	public String userDoesNotExist;

	@Value("${user.username.restrictedchar2.error}")
	public String userUsernameRestrictedchar2Error;

	@Value("${user.firstname.onlyalphabets.error}")
	public String userFirstnameOnlyalphabetsError;

	@Value("${user.middlename.onlyalphabets.error}")
	public String userMiddlenameOnlyalphabetsError;

	@Value("${user.lastname.onlyalphabets.error}")
	public String userLastnameOnlyalphabetsError;

	@Value("${user.mobile.length.error}")
	public String userMobileLengthError;

	@Value("${user.username.exists.error}")
	public String userUsernameExistsError;

	@Value("${user.email.exists.error}")
	public String userEmailExistsError;

	@Value("${user.update.success}")
	public String userUpdateSuccess;

	@Value("${user.add.success}")
	public String userAddSuccess;

	@Value("${user.delete.success}")
	public String userDeleteSuccess;

	@Value("${user.active.success}")
	public String userActiveSuccess;

	@Value("${user.inactive.success}")
	public String userInactiveSuccess;

	/**
	 * module
	 */
	@Value("${module.modulename.restrictedchar3.error}")
	public String moduleModulenameRestrictedchar3Error;

	@Value("${module.modulename.exists.error}")
	public String moduleModulenameExistsError;

	@Value("${module.update.success}")
	public String moduleUpdateSuccess;

	@Value("${module.add.success}")
	public String moduleAddSuccess;

	@Value("${module.delete.success}")
	public String moduleDeleteSuccess;

	@Value("${module.active.success}")
	public String moduleActiveSuccess;

	@Value("${module.inactive.success}")
	public String moduleInactiveSuccess;

	/**
	 * courseType
	 */
	@Value("${coursetype.coursetypename.restrictedchar3.error}")
	public String coursetypeCoursetypenameRestrictedchar3Error;

	@Value("${coursetype.update.success}")
	public String coursetypeUpdateSuccess;

	@Value("${coursetype.add.success}")
	public String coursetypeAddSuccess;

	@Value("${coursetype.delete.success}")
	public String coursetypeDeleteSuccess;

	@Value("${coursetype.active.success}")
	public String coursetypeActiveSuccess;

	@Value("${coursetype.inactive.success}")
	public String coursetypeInactiveSuccess;

	/**
	 * role
	 */
	@Value("${role.rolename.restrictedchar3.error}")
	public String roleRolenameRestrictedchar3Error;

	@Value("${role.rolename.exists.error}")
	public String roleRolenameExistsError;

	@Value("${role.update.success}")
	public String roleUpdateSuccess;

	@Value("${role.add.success}")
	public String roleAddSuccess;

	@Value("${role.delete.success}")
	public String roleDeleteSuccess;

	@Value("${role.active.success}")
	public String roleActiveSuccess;

	@Value("${role.inactive.success}")
	public String roleInactiveSuccess;

	@Value("${course.update.success}")
	public String courseUpdateSuccess;

	@Value("${course.add.success}")
	public String courseAddSuccess;

	@Value("${course.delete.success}")
	public String courseDeleteSuccess;

	/**
	 * facultySkillsets
	 */
	@Value("${facultyskillsets.update.success}")
	public String facultyskillsetsUpdateSuccess;
}