package com.url.app.impl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.url.app.utility.AppLogMessage;
import com.url.app.utility.AppResponseKey;
import com.url.app.validation.AppModuleValidationService;

/**
 * Service implementation of application for Module.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appModuleServiceImpl")
public class AppModuleServiceImpl implements AppModuleService {
	private static final Logger logger = LoggerFactory.getLogger(AppModuleServiceImpl.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private AppMessage appMessage;

	@Autowired
	private AppModuleValidationService appModuleValidationService;

	@Override
	@Transactional(readOnly = true)
	public List<Module> fetchDetailsModules() {
		return moduleRepository.findAllByOrderByModuleId();
	}

	@Override
	@Transactional(readOnly = true)
	public Module fetchDataModule(final Module formModule) {
		Module module = null;

		if (AppCommon.isPositiveInteger(formModule.getModuleId())) {
			module = moduleRepository.getOne(formModule.getModuleId());
		}

		return module;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveModule(final Module formModule) {
		logger.info(AppLogMessage.MODULE_MSG, formModule);

		if (AppCommon.isPositiveInteger(formModule.getModuleId())) {
			appModuleValidationService.validateForUpdate(formModule);
		} else {
			appModuleValidationService.validateForCreate(formModule);
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

		Module module = new Module();
		if (AppCommon.isPositiveInteger(formModule.getModuleId())) {
			module = moduleRepository.getOne(formModule.getModuleId());
		} else {
			module.setIsActive(AppConstant.ACTIVE);
			module.setCreatedBy(loggedInUserId);
		}
		module.setModuleName(formModule.getModuleName());
		module.setModifiedBy(loggedInUserId);

		moduleRepository.save(module);
		final Integer moduleId = module.getModuleId();

		if (AppCommon.isPositiveInteger(moduleId)) {
			status = AppConstant.SUCCESS;
			if (AppCommon.isPositiveInteger(formModule.getModuleId())) {
				msg = appMessage.moduleUpdateSuccess;
			} else {
				msg = appMessage.moduleAddSuccess;
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateUpdateActivation(final Module formModule) {
		appModuleValidationService.validateForActivate(formModule);

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		final Module module = moduleRepository.getOne(formModule.getModuleId());
		module.setIsActive(formModule.getIsActive());

		moduleRepository.save(module);
		final Integer moduleIdUpdate = module.getModuleId();

		if (AppCommon.isPositiveInteger(moduleIdUpdate)) {
			status = AppConstant.SUCCESS;
			if (AppConstant.ACTIVE.equals(formModule.getIsActive())) {
				msg = appMessage.moduleActiveSuccess;
			} else if (AppConstant.INACTIVE.equals(formModule.getIsActive())) {
				msg = appMessage.moduleInactiveSuccess;
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}