package com.url.app.interf.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.dto.Module;
import com.url.app.utility.AppUrlView;

/**
 * Module Controller for module related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_MODULE)
public interface ModuleRestController {

	/**
	 * Fetch data of Modules Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Module> fetchDetails();

	/**
	 * Fetch data of on add/update module screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Module> fetchData(@RequestParam(value = "moduleId", required = false) final String moduleIdStr);

	/**
	 * Validate and save data of on add/update module screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> validateSave(@RequestParam final Map<String, String> allRequestParams);

	/**
	 * Validate and save data of on module activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams);
}