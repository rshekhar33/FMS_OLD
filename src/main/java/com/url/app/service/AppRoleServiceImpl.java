package com.url.app.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dao.RoleRepository;
import com.url.app.dto.Role;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppResponseKey;

/**
 * Service implementation of application for Role.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appRoleServiceImpl")
public class AppRoleServiceImpl implements AppRoleService {
	//private static final Logger logger = LoggerFactory.getLogger(AppRoleServiceImpl.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AppMessage appMessage;

	@Override
	@Transactional(readOnly = true)
	public List<Role> fetchDetailsRoles() {
		return roleRepository.findAllByOrderByRoleId();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> fetchDataRole(String roleIdStr) {
		final Map<String, Object> json = new ConcurrentHashMap<>();

		if (roleIdStr != null && !AppConstant.BLANK_STRING.equals(roleIdStr)) {
			final Integer roleId = Integer.parseInt(roleIdStr);
			final Role role = roleRepository.getOne(roleId);
			json.put(AppResponseKey.ROLE, role);
		}

		return json;
	}

	@Override
	@Transactional
	public Map<String, Object> validateSaveRole(Map<String, String> allRequestParams) {
		final String hidRoleIdStr = allRequestParams.getOrDefault("hidRoleId", "0");
		final String roleName = allRequestParams.getOrDefault("roleName", AppConstant.BLANK_STRING);

		Integer hidRoleId = 0;
		try {
			hidRoleId = Integer.parseInt(hidRoleIdStr);
		} catch (Exception e) {
			hidRoleId = 0;
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String roleNameError = AppConstant.BLANK_STRING;

		if (AppConstant.BLANK_STRING.equals(roleName)) {
			status = AppConstant.FAIL;
			roleNameError = appMessage.getMessage("mandatory.field.error");
		} else if (AppCommon.hasRestrictedChar3(roleName)) {
			status = AppConstant.FAIL;
			roleNameError = appMessage.getMessage("role.rolename.restrictedchar3.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			if (hidRoleId == 0) {
				final Long roleNameCount = roleRepository.countByRoleName(roleName);
				if (roleNameCount > 0) {
					status = AppConstant.FAIL;
					roleNameError = appMessage.getMessage("role.rolename.exists.error");
				}
			} else {
				final Long roleNameCount = roleRepository.countByRoleNameAndRoleIdNot(roleName, hidRoleId);
				if (roleNameCount > 0) {
					status = AppConstant.FAIL;
					roleNameError = appMessage.getMessage("role.rolename.exists.error");
				}
			}
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

			Role role = new Role();
			if (hidRoleId > 0) {
				role = roleRepository.getOne(hidRoleId);
			} else {
				role.setIsActive(AppConstant.ACTIVE);
				role.setCreatedBy(loggedInUserId);
			}
			role.setRoleName(roleName);
			role.setModifiedBy(loggedInUserId);

			roleRepository.save(role);
			final Integer roleId = role.getRoleId();

			if (roleId != null && roleId > 0) {
				status = AppConstant.SUCCESS;
				if (hidRoleId > 0) {
					msg = appMessage.getMessage("role.update.success");
				} else {
					msg = appMessage.getMessage("role.add.success");
				}
			}
		}

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.ROLE_NAME_ERROR, roleNameError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, Object> validateUpdateActivation(Map<String, String> allRequestParams) {
		final String roleIdStr = allRequestParams.getOrDefault("roleId", "0");
		final String isActiveStr = allRequestParams.getOrDefault("isActive", AppConstant.BLANK_STRING);

		Integer roleId = 0;
		Integer isActive = AppConstant.ACTIVE;
		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		try {
			roleId = Integer.parseInt(roleIdStr);
		} catch (Exception e) {
			roleId = 0;
		}

		try {
			isActive = Integer.parseInt(isActiveStr);
		} catch (Exception e) {
			isActive = 0;
		}

		if (roleId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.getMessage("update.failed.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			Role role = roleRepository.getOne(roleId);
			role.setIsActive(isActive);

			roleRepository.save(role);
			final Integer roleIdUpdate = role.getRoleId();

			if (roleIdUpdate != null && roleIdUpdate > 0) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("role.active.success");
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("role.inactive.success");
				}
			}
		}

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}