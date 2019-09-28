package com.url.app.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dao.AppDao;
import com.url.app.dao.RoleRepository;
import com.url.app.dao.UserRepository;
import com.url.app.dto.LoggedUser;
import com.url.app.dto.Role;
import com.url.app.dto.User;
import com.url.app.dto.UserMng;
import com.url.app.dto.UserRoleRelation;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppResponseKey;

/**
 * Service implementation of application for User.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appUserServiceImpl")
public class AppUserServiceImpl implements AppUserService {
	private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AppMessage appMessage;

	@Override
	public LoggedUser getPrincipal() {
		LoggedUser loggedUser = null;
		try {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if ((principal != null) && (principal instanceof LoggedUser)) {
				loggedUser = (LoggedUser) principal;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return loggedUser;
	}

	@Override
	public User getPrincipalUser() {
		User user = null;
		try {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if ((principal != null) && (principal instanceof LoggedUser)) {
				final LoggedUser loggedUser = (LoggedUser) principal;
				if (loggedUser != null) {
					user = loggedUser.getUser();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return user;
	}

	@Override
	public Integer getPrincipalUserUserId() {
		Integer userId = null;
		try {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if ((principal != null) && (principal instanceof LoggedUser)) {
				final LoggedUser loggedUser = (LoggedUser) principal;
				if (loggedUser != null) {
					userId = loggedUser.getUser().getUserId();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return userId;
	}

	@Override
	@Transactional(readOnly = true)
	public User fetchValidUser(String userName) {
		return appDao.fetchUser(userName);
	}

	@Override
	@Transactional
	public void userUpdateLastLoginSuccess() {
		appDao.userUpdateLastLoginSuccess(getPrincipalUserUserId());
	}

	@Override
	@Transactional
	public void userUpdateLastLoginFailure(final String userName) {
		appDao.userUpdateLastLoginFailure(userName);
	}

	@Override
	@Transactional
	public List<UserMng> fetchDetailsUsers() {
		return appDao.fetchUsersListing();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> fetchDataUser(final String userIdStr) {
		final Map<String, Object> json = new ConcurrentHashMap<>();

		if (userIdStr != null && !AppConstant.BLANK_STRING.equals(userIdStr)) {
			final Integer userId = Integer.parseInt(userIdStr);
			final User user = appDao.fetchUserWithRoles(userId);
			json.put(AppResponseKey.USER, user);

			final List<Integer> userRoleIds = user.getUserRoleRelations().stream().map(UserRoleRelation::getRole).map(Role::getRoleId).collect(Collectors.toList());
			json.put(AppResponseKey.USER_ROLE_IDS, userRoleIds);
		}

		final List<Role> roles = roleRepository.findByIsActiveOrderByRoleId(AppConstant.ACTIVE);
		json.put(AppResponseKey.ROLES, roles);

		return json;
	}

	@Override
	@Transactional
	public Map<String, Object> validateSaveUser(final Map<String, String> allRequestParams) {
		final String hidUserIdStr = allRequestParams.getOrDefault("hidUserId", "0");
		final String userName = allRequestParams.getOrDefault("userName", AppConstant.BLANK_STRING);
		final String firstName = allRequestParams.getOrDefault("firstName", AppConstant.BLANK_STRING);
		final String middleName = allRequestParams.get("middleName");
		final String lastName = allRequestParams.get("lastName");
		final String emailId = allRequestParams.getOrDefault("emailId", AppConstant.BLANK_STRING);
		final String mobileNo = allRequestParams.getOrDefault("mobileNo", AppConstant.BLANK_STRING);
		final String rolesStr = allRequestParams.getOrDefault("rolesStr", AppConstant.BLANK_STRING);

		Integer hidUserId = 0;
		try {
			hidUserId = Integer.parseInt(hidUserIdStr);
		} catch (Exception e) {
			hidUserId = 0;
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String userNameError = AppConstant.BLANK_STRING;
		String firstNameError = AppConstant.BLANK_STRING;
		String middleNameError = AppConstant.BLANK_STRING;
		String lastNameError = AppConstant.BLANK_STRING;
		String emailIdError = AppConstant.BLANK_STRING;
		String mobileNoError = AppConstant.BLANK_STRING;
		String rolesError = AppConstant.BLANK_STRING;

		if (hidUserId == 0) {
			if (AppConstant.BLANK_STRING.equals(userName)) {
				status = AppConstant.FAIL;
				userNameError = appMessage.getMessage("mandatory.field.error");
			} else if (AppCommon.hasRestrictedChar2(userName)) {
				status = AppConstant.FAIL;
				userNameError = appMessage.getMessage("user.username.restrictedchar2.error");
			}
		}
		if (AppConstant.BLANK_STRING.equals(firstName)) {
			status = AppConstant.FAIL;
			firstNameError = appMessage.getMessage("mandatory.field.error");
		} else if (!AppCommon.hasOnlyAlphabets(firstName)) {
			status = AppConstant.FAIL;
			firstNameError = appMessage.getMessage("user.firstname.onlyalphabets.error");
		}
		if (!AppConstant.BLANK_STRING.equals(middleName) && !AppCommon.hasOnlyAlphabets(middleName)) {
			status = AppConstant.FAIL;
			middleNameError = appMessage.getMessage("user.middlename.onlyalphabets.error");
		}
		if (!AppConstant.BLANK_STRING.equals(lastName) && !AppCommon.hasOnlyAlphabets(lastName)) {
			status = AppConstant.FAIL;
			lastNameError = appMessage.getMessage("user.lastname.onlyalphabets.error");
		}
		if (AppConstant.BLANK_STRING.equals(emailId)) {
			status = AppConstant.FAIL;
			emailIdError = appMessage.getMessage("mandatory.field.error");
		} else if (AppCommon.isNotValidEmail(emailId)) {
			status = AppConstant.FAIL;
			emailIdError = appMessage.getMessage("invalid.email.error");
		}
		if (AppConstant.BLANK_STRING.equals(mobileNo)) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.getMessage("mandatory.field.error");
		} else if (AppCommon.isNotNumber(mobileNo)) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.getMessage("only.number.error");
		} else if (mobileNo.length() != 10) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.getMessage("user.mobile.length.error");
		}
		if (AppConstant.BLANK_STRING.equals(rolesStr)) {
			status = AppConstant.FAIL;
			rolesError = appMessage.getMessage("mandatory.field.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			if (hidUserId == 0) {
				final Long userNameCount = userRepository.countByUserName(userName);
				if (userNameCount > 0) {
					status = AppConstant.FAIL;
					userNameError = appMessage.getMessage("user.username.exists.error");
				}
				final Long emailIdCount = userRepository.countByEmailId(emailId);
				if (emailIdCount > 0) {
					status = AppConstant.FAIL;
					emailIdError = appMessage.getMessage("user.email.exists.error");
				}
			} else {
				final Long emailIdCount = userRepository.countByEmailIdAndUserIdNot(emailId, hidUserId);
				if (emailIdCount > 0) {
					status = AppConstant.FAIL;
					emailIdError = appMessage.getMessage("user.email.exists.error");
				}
			}
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			final Integer loggedInUserId = getPrincipalUserUserId();

			User user = new User();
			if (hidUserId > 0) {
				user = userRepository.getOne(hidUserId);
			} else {
				user.setUserName(userName);
				user.setPassword(passwordEncoder.encode("fms"));
				user.setIsActive(AppConstant.ACTIVE);
				user.setCreatedBy(loggedInUserId);
			}
			user.setFirstName(firstName);
			user.setMiddleName(middleName);
			user.setLastName(lastName);
			user.setEmailId(emailId);
			user.setMobileNo(mobileNo);
			user.setModifiedBy(loggedInUserId);

			if (!AppConstant.BLANK_STRING.equals(rolesStr)) {
				final Set<UserRoleRelation> removedUserRoleRelations = new HashSet<>(user.getUserRoleRelations());
				final List<Integer> roleIdList = Arrays.asList(rolesStr.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
				for (Integer roleId : roleIdList) {
					final Role role = new Role();
					role.setRoleId(roleId);

					final UserRoleRelation userRoleRelation = new UserRoleRelation();
					userRoleRelation.setRole(role);
					userRoleRelation.setIsActive(AppConstant.ACTIVE);
					userRoleRelation.setCreatedBy(loggedInUserId);
					userRoleRelation.setModifiedBy(loggedInUserId);

					user.addUserRoleRelation(userRoleRelation);
					removedUserRoleRelations.remove(userRoleRelation);
				}
				for (UserRoleRelation userRoleRelation : removedUserRoleRelations) {
					user.removeUserRoleRelation(userRoleRelation);
				}
			}

			userRepository.save(user);
			final Integer userId = user.getUserId();

			if (userId != null && userId > 0) {
				status = AppConstant.SUCCESS;
				if (hidUserId > 0) {
					msg = appMessage.getMessage("user.update.success");
				} else {
					msg = appMessage.getMessage("user.add.success");
				}
			}
		}

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.USER_NAME_ERROR, userNameError);
		json.put(AppResponseKey.FIRST_NAME_ERROR, firstNameError);
		json.put(AppResponseKey.MIDDLE_NAME_ERROR, middleNameError);
		json.put(AppResponseKey.LAST_NAME_ERROR, lastNameError);
		json.put(AppResponseKey.EMAIL_ID_ERROR, emailIdError);
		json.put(AppResponseKey.MOBILE_NO_ERROR, mobileNoError);
		json.put(AppResponseKey.ROLES_ERROR, rolesError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, Object> validateUpdateActivation(Map<String, String> allRequestParams) {
		final String userIdStr = allRequestParams.getOrDefault("userId", "0");
		final String isActiveStr = allRequestParams.getOrDefault("isActive", AppConstant.BLANK_STRING);

		Integer userId = 0;
		Integer isActive = AppConstant.ACTIVE;
		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		try {
			userId = Integer.parseInt(userIdStr);
		} catch (Exception e) {
			userId = 0;
		}

		try {
			isActive = Integer.parseInt(isActiveStr);
		} catch (Exception e) {
			isActive = 0;
		}

		if (userId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.getMessage("update.failed.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			User user = userRepository.getOne(userId);
			user.setIsActive(isActive);

			userRepository.save(user);
			final Integer userIdUpdate = user.getUserId();

			if (userIdUpdate != null && userIdUpdate > 0) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("user.active.success");
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("user.inactive.success");
				}
			}
		}

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}