package com.url.app.impl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dto.Role;
import com.url.app.interf.dao.RoleRepository;
import com.url.app.interf.service.AppRoleService;
import com.url.app.interf.service.AppUserService;
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
	public Map<String, Role> fetchDataRole(final String roleIdStr) {
		final Map<String, Role> json = new ConcurrentHashMap<>();

		if (!AppCommon.isEmpty(roleIdStr)) {
			final Integer roleId = Integer.parseInt(roleIdStr);
			final Role role = roleRepository.getOne(roleId);
			json.put(AppResponseKey.ROLE, role);
		}

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveRole(final Map<String, String> allRequestParams) {
		final String hidRoleIdStr = allRequestParams.getOrDefault("hidRoleId", "0");
		final String roleName = allRequestParams.get("roleName");

		Integer hidRoleId = AppCommon.toInteger(hidRoleIdStr);

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String roleNameError = AppConstant.BLANK_STRING;

		if (AppCommon.isEmpty(roleName)) {
			status = AppConstant.FAIL;
			roleNameError = appMessage.mandatoryFieldError;
		} else if (AppCommon.hasRestrictedChar3(roleName)) {
			status = AppConstant.FAIL;
			roleNameError = appMessage.roleRolenameRestrictedchar3Error;
		}

		if (AppCommon.isEmpty(status)) {
			if (hidRoleId == 0) {
				final Long roleNameCount = roleRepository.countByRoleName(roleName);
				if (roleNameCount > 0) {
					status = AppConstant.FAIL;
					roleNameError = appMessage.roleRolenameExistsError;
				}
			} else {
				final Long roleNameCount = roleRepository.countByRoleNameAndRoleIdNot(roleName, hidRoleId);
				if (roleNameCount > 0) {
					status = AppConstant.FAIL;
					roleNameError = appMessage.roleRolenameExistsError;
				}
			}
		}

		if (AppCommon.isEmpty(status)) {
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

			if (AppCommon.isPositiveInteger(roleId)) {
				status = AppConstant.SUCCESS;
				if (hidRoleId > 0) {
					msg = appMessage.roleUpdateSuccess;
				} else {
					msg = appMessage.roleAddSuccess;
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.ROLE_NAME_ERROR, roleNameError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		final String roleIdStr = allRequestParams.getOrDefault("roleId", "0");
		final String isActiveStr = allRequestParams.get("isActive");

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		Integer roleId = AppCommon.toInteger(roleIdStr);
		Integer isActive = AppCommon.toIntegerOrNull(isActiveStr);

		if (roleId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.updateFailedError;
		}

		if (AppCommon.isEmpty(status)) {
			Role role = roleRepository.getOne(roleId);
			role.setIsActive(isActive);

			roleRepository.save(role);
			final Integer roleIdUpdate = role.getRoleId();

			if (AppCommon.isPositiveInteger(roleIdUpdate)) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.roleActiveSuccess;
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.roleInactiveSuccess;
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}