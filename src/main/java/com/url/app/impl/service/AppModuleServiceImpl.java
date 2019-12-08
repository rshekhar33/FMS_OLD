package com.url.app.impl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dto.Module;
import com.url.app.interf.dao.ModuleRepository;
import com.url.app.interf.service.AppModuleService;
import com.url.app.interf.service.AppUserService;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppResponseKey;

/**
 * Service implementation of application for Module.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appModuleServiceImpl")
public class AppModuleServiceImpl implements AppModuleService {
	//private static final Logger logger = LoggerFactory.getLogger(AppModuleServiceImpl.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private AppMessage appMessage;

	@Override
	@Transactional(readOnly = true)
	public List<Module> fetchDetailsModules() {
		return moduleRepository.findAllByOrderByModuleId();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Module> fetchDataModule(String moduleIdStr) {
		final Map<String, Module> json = new ConcurrentHashMap<>();

		if (moduleIdStr != null && !AppConstant.BLANK_STRING.equals(moduleIdStr)) {
			final Integer moduleId = Integer.parseInt(moduleIdStr);
			final Module module = moduleRepository.getOne(moduleId);
			json.put(AppResponseKey.MODULE, module);
		}

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveModule(Map<String, String> allRequestParams) {
		final String hidModuleIdStr = allRequestParams.getOrDefault("hidModuleId", "0");
		final String moduleName = allRequestParams.getOrDefault("moduleName", AppConstant.BLANK_STRING);

		Integer hidModuleId = 0;
		try {
			hidModuleId = Integer.parseInt(hidModuleIdStr);
		} catch (Exception e) {
			hidModuleId = 0;
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String moduleNameError = AppConstant.BLANK_STRING;

		if (AppConstant.BLANK_STRING.equals(moduleName)) {
			status = AppConstant.FAIL;
			moduleNameError = appMessage.getMessage("mandatory.field.error");
		} else if (AppCommon.hasRestrictedChar3(moduleName)) {
			status = AppConstant.FAIL;
			moduleNameError = appMessage.getMessage("module.modulename.restrictedchar3.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			if (hidModuleId == 0) {
				final Long moduleNameCount = moduleRepository.countByModuleName(moduleName);
				if (moduleNameCount > 0) {
					status = AppConstant.FAIL;
					moduleNameError = appMessage.getMessage("module.modulename.exists.error");
				}
			} else {
				final Long moduleNameCount = moduleRepository.countByModuleNameAndModuleIdNot(moduleName, hidModuleId);
				if (moduleNameCount > 0) {
					status = AppConstant.FAIL;
					moduleNameError = appMessage.getMessage("module.modulename.exists.error");
				}
			}
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

			Module module = new Module();
			if (hidModuleId > 0) {
				module = moduleRepository.getOne(hidModuleId);
			} else {
				module.setIsActive(AppConstant.ACTIVE);
				module.setCreatedBy(loggedInUserId);
			}
			module.setModuleName(moduleName);
			module.setModifiedBy(loggedInUserId);

			moduleRepository.save(module);
			final Integer moduleId = module.getModuleId();

			if (moduleId != null && moduleId > 0) {
				status = AppConstant.SUCCESS;
				if (hidModuleId > 0) {
					msg = appMessage.getMessage("module.update.success");
				} else {
					msg = appMessage.getMessage("module.add.success");
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.MODULE_NAME_ERROR, moduleNameError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateUpdateActivation(Map<String, String> allRequestParams) {
		final String moduleIdStr = allRequestParams.getOrDefault("moduleId", "0");
		final String isActiveStr = allRequestParams.getOrDefault("isActive", AppConstant.BLANK_STRING);

		Integer moduleId = 0;
		Integer isActive = AppConstant.ACTIVE;
		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		try {
			moduleId = Integer.parseInt(moduleIdStr);
		} catch (Exception e) {
			moduleId = 0;
		}

		try {
			isActive = Integer.parseInt(isActiveStr);
		} catch (Exception e) {
			isActive = 0;
		}

		if (moduleId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.getMessage("update.failed.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			Module module = moduleRepository.getOne(moduleId);
			module.setIsActive(isActive);

			moduleRepository.save(module);
			final Integer moduleIdUpdate = module.getModuleId();

			if (moduleIdUpdate != null && moduleIdUpdate > 0) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("module.active.success");
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("module.inactive.success");
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}