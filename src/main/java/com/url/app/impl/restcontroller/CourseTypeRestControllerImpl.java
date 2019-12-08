package com.url.app.impl.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.CourseType;
import com.url.app.interf.restcontroller.CourseTypeRestController;
import com.url.app.interf.service.AppCourseTypeService;

/**
 * Rest Controller for course type related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
public class CourseTypeRestControllerImpl implements CourseTypeRestController {

	@Autowired
	private AppCourseTypeService appCourseTypeService;

	@Override
	public List<CourseType> fetchDetails() {
		return appCourseTypeService.fetchDetailsCourseTypes();
	}

	@Override
	public Map<String, CourseType> fetchData(final String courseTypeIdStr) {
		return appCourseTypeService.fetchDataCourseType(courseTypeIdStr);
	}

	@Override
	public Map<String, String> validateSave(final Map<String, String> allRequestParams) {
		return appCourseTypeService.validateSaveCourseType(allRequestParams);
	}

	@Override
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		return appCourseTypeService.validateUpdateActivation(allRequestParams);
	}
}