package com.url.app.interf.service;

import java.util.List;
import java.util.Map;

import com.url.app.dto.Module;

/**
 * Service Layer of application for Module.
 * Method declaration of business logic.
 * 
 * @author Shekhar Shinde
 */
public interface AppModuleService {

	/**
	 * Fetch all module details.
	 * 
	 * @return modules details in json format.
	 */
	List<Module> fetchDetailsModules();

	/**
	 * Fetch module data in case moduleId is sent else data required on add module screen.
	 * 
	 * @param moduleIdStr the moduleId of module.
	 * @return module data in json format.
	 */
	Map<String, Module> fetchDataModule(String moduleIdStr);

	/**
	 * Validates add/update module data.
	 * If data is valid then it adds or updates the module,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of add/update screen.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, String> validateSaveModule(Map<String, String> allRequestParams);

	/**
	 * Validates module activation data.
	 * If data is valid then it updates the module's isActive flag,
	 * if data is invalid then proper error messages are returned in JSON format.
	 * 
	 * @param allRequestParams all the parameters of activation.
	 * @return status as success if data is valid or else all the validation messages with status as failure in JSON.
	 */
	Map<String, String> validateUpdateActivation(Map<String, String> allRequestParams);
}