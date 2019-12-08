package com.url.app.interf.service;

import java.util.List;
import java.util.Map;

import com.url.app.dto.Role;

/**
 * Service Layer of application for Role.
 * Method declaration of business logic.
 * 
 * @author Shekhar Shinde
 */
public interface AppRoleService {

	/**
	 * Fetch all role details.
	 * 
	 * @return roles details in json format.
	 */
	List<Role> fetchDetailsRoles();

	/**
	 * Fetch role data in case roleId is sent else data required on add role screen.
	 * 
	 * @param roleIdStr the roleId of role.
	 * @return role data in json format.
	 */
	Map<String, Role> fetchDataRole(String roleIdStr);

	/**
	 * Validates add/update role data.
	 * If data is valid then it adds or updates the role,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of add/update screen.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, String> validateSaveRole(Map<String, String> allRequestParams);

	/**
	 * Validates role activation data.
	 * If data is valid then it updates the role's isActive flag,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of activation.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, String> validateUpdateActivation(Map<String, String> allRequestParams);
}