package com.url.app.impl.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dto.FacultySkillset;
import com.url.app.dto.FacultySkillsetMng;
import com.url.app.dto.Module;
import com.url.app.dto.User;
import com.url.app.interf.dao.AppDao;
import com.url.app.interf.dao.ModuleRepository;
import com.url.app.interf.dao.UserRepository;
import com.url.app.interf.service.AppFacultySkillsetService;
import com.url.app.interf.service.AppUserService;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppResponseKey;

/**
 * Service implementation of application for Faculty Skillset.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appFacultySkillsetServiceImpl")
public class AppFacultySkillsetServiceImpl implements AppFacultySkillsetService {
	//private static final Logger logger = LoggerFactory.getLogger(AppFacultySkillsetServiceImpl.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppDao appDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private AppMessage appMessage;

	@Override
	@Transactional
	public List<FacultySkillsetMng> fetchDetailsFacultySkillsets() {
		return appDao.fetchFacultySkillsetsListing();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> fetchDataFacultySkillset(String userIdStr) {
		final Map<String, Object> json = new ConcurrentHashMap<>();

		if (userIdStr != null && !AppConstant.BLANK_STRING.equals(userIdStr)) {
			final Integer userId = Integer.parseInt(userIdStr);
			final User user = appDao.fetchUserWithModules(userId);
			json.put(AppResponseKey.USER, user);

			final List<Integer> userModuleIds = user.getFacultySkillsets().stream().map(FacultySkillset::getModule).map(Module::getModuleId).collect(Collectors.toList());
			json.put(AppResponseKey.USER_MODULE_IDS, userModuleIds);
		}

		final List<Module> modules = moduleRepository.findByIsActiveOrderByModuleId(AppConstant.ACTIVE);
		json.put(AppResponseKey.MODULES, modules);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveFacultySkillset(Map<String, String> allRequestParams) {
		final String hidUserIdStr = allRequestParams.getOrDefault("hidUserId", "0");
		final String modulesStr = allRequestParams.getOrDefault("modulesStr", AppConstant.BLANK_STRING);

		Integer hidUserId = 0;
		try {
			hidUserId = Integer.parseInt(hidUserIdStr);
		} catch (Exception e) {
			hidUserId = 0;
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String modulesError = AppConstant.BLANK_STRING;

		if (AppConstant.BLANK_STRING.equals(modulesStr)) {
			status = AppConstant.FAIL;
			modulesError = appMessage.getMessage("mandatory.field.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

			User user = new User();
			if (hidUserId > 0) {
				user = userRepository.getOne(hidUserId);
			}
			user.setModifiedBy(loggedInUserId);

			if (!AppConstant.BLANK_STRING.equals(modulesStr)) {
				final Set<FacultySkillset> removedFacultySkillsets = new HashSet<>(user.getFacultySkillsets());
				final List<Integer> moduleIdList = Arrays.asList(modulesStr.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
				for (Integer moduleId : moduleIdList) {
					final Module module = new Module();
					module.setModuleId(moduleId);

					final FacultySkillset facultySkillset = new FacultySkillset();
					facultySkillset.setModule(module);
					facultySkillset.setIsActive(AppConstant.ACTIVE);
					facultySkillset.setCreatedBy(loggedInUserId);
					facultySkillset.setModifiedBy(loggedInUserId);

					user.addFacultySkillset(facultySkillset);
					removedFacultySkillsets.remove(facultySkillset);
				}
				for (FacultySkillset facultySkillset : removedFacultySkillsets) {
					user.removeFacultySkillset(facultySkillset);
				}
			}

			userRepository.save(user);
			final Integer userId = user.getUserId();

			if (userId != null && userId > 0) {
				status = AppConstant.SUCCESS;
				if (hidUserId > 0) {
					msg = appMessage.getMessage("facultyskillsets.update.success");
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.MODULES_ERROR, modulesError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, Object> validateUpdateActivation(Map<String, String> allRequestParams) {

		final Map<String, Object> json = new ConcurrentHashMap<>();

		return json;
	}
}