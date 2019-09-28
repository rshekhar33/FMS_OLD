package com.url.app.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.Role;
import com.url.app.service.AppRoleService;
import com.url.app.utility.AppUrlView;

/**
 * Role Controller for role related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
@RequestMapping(value = AppUrlView.URL_ROOT_ROLE)
public class RoleRestController {

	@Autowired
	private AppRoleService appRoleService;

	/**
	 * Fetch data of Roles Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Role> fetchDetails() {
		return appRoleService.fetchDetailsRoles();
	}

	/**
	 * Fetch data of on add/update role screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> fetchData(@RequestParam(value = "roleId", required = false) final String roleIdStr) {
		return appRoleService.fetchDataRole(roleIdStr);
	}

	/**
	 * Validate and save data of on add/update role screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> validateSave(@RequestParam final Map<String, String> allRequestParams) {
		return appRoleService.validateSaveRole(allRequestParams);
	}

	/**
	 * Validate and save data of on role activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams) {
		return appRoleService.validateUpdateActivation(allRequestParams);
	}
}