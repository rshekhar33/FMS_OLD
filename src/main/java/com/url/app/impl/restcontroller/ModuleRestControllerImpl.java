package com.url.app.impl.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.Module;
import com.url.app.interf.restcontroller.ModuleRestController;
import com.url.app.interf.service.AppModuleService;

/**
 * Module Controller for module related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
public class ModuleRestControllerImpl implements ModuleRestController {

	@Autowired
	private AppModuleService appModuleService;

	@Override
	public List<Module> fetchDetails() {
		return appModuleService.fetchDetailsModules();
	}

	@Override
	public Map<String, Module> fetchData(final String moduleIdStr) {
		return appModuleService.fetchDataModule(moduleIdStr);
	}

	@Override
	public Map<String, String> validateSave(final Map<String, String> allRequestParams) {
		return appModuleService.validateSaveModule(allRequestParams);
	}

	@Override
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		return appModuleService.validateUpdateActivation(allRequestParams);
	}
}