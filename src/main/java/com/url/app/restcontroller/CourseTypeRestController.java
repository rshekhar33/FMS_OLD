package com.url.app.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.CourseType;
import com.url.app.service.AppCourseTypeService;
import com.url.app.utility.AppUrlView;

/**
 * Rest Controller for course type related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
@RequestMapping(value = AppUrlView.URL_ROOT_COURSE_TYPE)
public class CourseTypeRestController {

	@Autowired
	private AppCourseTypeService appCourseTypeService;

	/**
	 * Fetch data of Course Types Listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CourseType> fetchDetails() {
		return appCourseTypeService.fetchDetailsCourseTypes();
	}

	/**
	 * Fetch data of on add/update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> fetchData(@RequestParam(value = "courseTypeId", required = false) final String courseTypeIdStr) {
		return appCourseTypeService.fetchDataCourseType(courseTypeIdStr);
	}

	/**
	 * Validate and save data of on add/update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> validateSave(@RequestParam final Map<String, String> allRequestParams) {
		return appCourseTypeService.validateSaveCourseType(allRequestParams);
	}

	/**
	 * Validate and save data of on course type activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> validateUpdateActivation(@RequestParam final Map<String, String> allRequestParams) {
		return appCourseTypeService.validateUpdateActivation(allRequestParams);
	}
}