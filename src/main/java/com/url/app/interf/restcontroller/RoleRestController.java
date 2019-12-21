package com.url.app.interf.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.dto.Role;
import com.url.app.utility.AppUrlView;

/**
 * Role Controller for role related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_ROLE)
public interface RoleRestController {

	/**
	 * Fetch data of Roles Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	List<Role> fetchDetails();

	/**
	 * Fetch data of on add/update role screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Role> fetchData(@RequestParam(value = "roleId", required = false) String roleIdStr);

	/**
	 * Validate and save data of on add/update role screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, String> validateSave(@RequestParam Map<String, String> allRequestParams);

	/**
	 * Validate and save data of on role activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, String> validateUpdateActivation(@RequestParam Map<String, String> allRequestParams);
}