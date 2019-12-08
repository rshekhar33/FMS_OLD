package com.url.app.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.Module;
import com.url.app.service.AppModuleService;
import com.url.app.utility.AppUrlView;

/**
 * Module Controller for module related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
@RequestMapping(value = AppUrlView.URL_ROOT_MODULE)
public class ModuleRestController {

	@Autowired
	private AppModuleService appModuleService;

	/**
	 * Fetch data of Modules Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Module> fetchDetails() {
		return appModuleService.fetchDetailsModules();
	}

	/**
	 * Fetch data of on add/update module screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> fetchData(@RequestParam(value = "moduleId", required = false) final String moduleIdStr) {
		return appModuleService.fetchDataModule(moduleIdStr);
	}

	/**
	 * Validate and save data of on add/update module screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> validateSave(@RequestParam final Map<String, String> allRequestParams) {
		return appModuleService.validateSaveModule(allRequestParams);
	}

	/**
	 * Validate and save data of on module activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams) {
		return appModuleService.validateUpdateActivation(allRequestParams);
	}
}