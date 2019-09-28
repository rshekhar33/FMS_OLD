package com.url.app.service;

import java.util.List;
import java.util.Map;

import com.url.app.dto.LoggedUser;
import com.url.app.dto.User;
import com.url.app.dto.UserMng;

/**
 * Service Layer of application for User.
 * Method declaration of business logic.
 * 
 * @author Shekhar Shinde
 */
public interface AppUserService {

	/**
	 * Get loggedUser from spring security principal.
	 * 
	 * @return loggedUser from spring security principal.
	 */
	LoggedUser getPrincipal();

	/**
	 * Get User of LoggedUser from spring security principal.
	 * 
	 * @return User of LoggedUser from spring security principal.
	 */
	User getPrincipalUser();

	/**
	 * Get userId of LoggedUser from spring security principal.
	 * 
	 * @return userId of User of LoggedUser from spring security principal.
	 */
	Integer getPrincipalUserUserId();

	/**
	 * Fetch user data from userName.
	 * 
	 * @param userName the userName of user.
	 * @return user data.
	 */
	User fetchValidUser(String userName);

	/**
	 * Update user last success login date.
	 */
	void userUpdateLastLoginSuccess();

	/**
	 * Update user last failure login date and increment failed login count.
	 * 
	 * @param userName the userName of User.
	 */
	void userUpdateLastLoginFailure(String userName);

	/**
	 * Fetch all user details.
	 * 
	 * @return users details in json format.
	 */
	List<UserMng> fetchDetailsUsers();

	/**
	 * Fetch user data in case userId is sent else data required on add user screen.
	 * 
	 * @param userIdStr the userId of user.
	 * @return user data in json format.
	 */
	Map<String, Object> fetchDataUser(String userIdStr);

	/**
	 * Validates add/update user data.
	 * If data is valid then it adds or updates the user,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of add/update screen.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, Object> validateSaveUser(Map<String, String> allRequestParams);

	/**
	 * Validates user activation data.
	 * If data is valid then it updates the user's isActive flag,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of activation.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, Object> validateUpdateActivation(Map<String, String> allRequestParams);
}