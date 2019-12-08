package com.url.app.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.FacultySkillsetMng;
import com.url.app.service.AppFacultySkillsetService;
import com.url.app.utility.AppUrlView;

/**
 * Rest Controller for faculty skillset related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
@RequestMapping(value = AppUrlView.URL_ROOT_FACULTY_SKILLSET)
public class FacultySkillsetRestController {

	@Autowired
	private AppFacultySkillsetService appFacultySkillsetService;

	/**
	 * Fetch data of Faculty Skillsets Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FacultySkillsetMng> fetchDetails() {
		return appFacultySkillsetService.fetchDetailsFacultySkillsets();
	}

	/**
	 * Fetch data of on add/update faculty skillset screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> fetchData(@RequestParam(value = "userId", required = false) final String userIdStr) {
		return appFacultySkillsetService.fetchDataFacultySkillset(userIdStr);
	}

	/**
	 * Validate and save data of on add/update faculty skillset screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> validateSave(@RequestParam final Map<String, String> allRequestParams) {
		return appFacultySkillsetService.validateSaveFacultySkillset(allRequestParams);
	}

	/**
	 * Validate and save data of on faculty skillset activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams) {
		return appFacultySkillsetService.validateUpdateActivation(allRequestParams);
	}
}