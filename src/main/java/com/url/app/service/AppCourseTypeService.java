package com.url.app.service;

import java.util.List;
import java.util.Map;

import com.url.app.dto.CourseType;

/**
 * Service Layer of application for Course Type.
 * Method declaration of business logic.
 * 
 * @author Shekhar Shinde
 */
public interface AppCourseTypeService {

	/**
	 * Fetch all course type details.
	 * 
	 * @return course type details in json format.
	 */
	List<CourseType> fetchDetailsCourseTypes();

	/**
	 * Fetch course type data in case courseTypeId is sent else data required on add course type screen.
	 * 
	 * @param courseTypeIdStr the courseTypeId of course type.
	 * @return course type data in json format.
	 */
	Map<String, Object> fetchDataCourseType(String courseTypeIdStr);

	/**
	 * Validates add/update course type data.
	 * If data is valid then it adds or updates the course type,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of add/update screen.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, Object> validateSaveCourseType(Map<String, String> allRequestParams);

	/**
	 * Validates course type activation data.
	 * If data is valid then it updates the course type's isActive flag,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of activation.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, Object> validateUpdateActivation(Map<String, String> allRequestParams);
}