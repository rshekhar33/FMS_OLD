package com.url.app.interf.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.dto.UserMng;
import com.url.app.utility.AppUrlView;

/**
 * Rest Controller for user related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_USER)
public interface UserRestController {

	/**
	 * Fetch data of Users Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserMng> fetchDetails();

	/**
	 * Fetch data of on add/update user screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> fetchData(@RequestParam(value = "userId", required = false) final String userIdStr);

	/**
	 * Validate and save data of on add/update user screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> validateSave(@RequestParam final Map<String, String> allRequestParams);

	/**
	 * Validate and save data of on user activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams);
}