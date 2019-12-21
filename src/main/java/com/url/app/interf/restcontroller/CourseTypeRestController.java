package com.url.app.interf.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.dto.CourseType;
import com.url.app.utility.AppUrlView;

@RequestMapping(value = AppUrlView.URL_ROOT_COURSE_TYPE)
public interface CourseTypeRestController {

	/**
	 * Fetch data of Course Types listing.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
	List<CourseType> fetchDetails();

	/**
	 * Fetch data of on add/update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_FETCH_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, CourseType> fetchData(@RequestParam(value = "courseTypeId", required = false) String courseTypeIdStr);

	/**
	 * Validate and save data of on add/update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_VALIDATE_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, String> validateSave(@RequestParam Map<String, String> allRequestParams);

	/**
	 * Validate and save data of on course type activation screen.
	 */
	@PostMapping(value = AppUrlView.URL_ACTIVATION, produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, String> validateUpdateActivation(@RequestParam Map<String, String> allRequestParams);
}