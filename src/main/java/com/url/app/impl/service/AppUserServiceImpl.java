package com.url.app.impl.service;

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
import com.url.app.dto.LoggedUser;
import com.url.app.dto.Role;
import com.url.app.dto.User;
import com.url.app.dto.UserMng;
import com.url.app.dto.UserRoleRelation;
import com.url.app.interf.dao.AppDao;
import com.url.app.interf.dao.RoleRepository;
import com.url.app.interf.dao.UserRepository;
import com.url.app.interf.service.AppUserService;
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

			if (principal != null && principal instanceof LoggedUser) {
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
		final LoggedUser loggedUser = getPrincipal();
		if (loggedUser != null) {
			user = loggedUser.getUser();
		}

		return user;
	}

	@Override
	public Integer getPrincipalUserUserId() {
		Integer userId = null;
		final User user = getPrincipalUser();
		if (user != null) {
			userId = user.getUserId();
		}

		return userId;
	}

	@Override
	@Transactional(readOnly = true)
	public User fetchValidUser(final String userName) {
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

		if (!AppCommon.isEmpty(userIdStr)) {
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
	public Map<String, String> validateSaveUser(final Map<String, String> allRequestParams) {
		final String hidUserIdStr = allRequestParams.getOrDefault("hidUserId", "0");
		final String userName = allRequestParams.get("userName");
		final String firstName = allRequestParams.get("firstName");
		final String middleName = allRequestParams.get("middleName");
		final String lastName = allRequestParams.get("lastName");
		final String emailId = allRequestParams.get("emailId");
		final String mobileNo = allRequestParams.get("mobileNo");
		final String rolesStr = allRequestParams.get("rolesStr");

		Integer hidUserId = AppCommon.toInteger(hidUserIdStr);

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
			if (AppCommon.isEmpty(userName)) {
				status = AppConstant.FAIL;
				userNameError = appMessage.mandatoryFieldError;
			} else if (AppCommon.hasRestrictedChar2(userName)) {
				status = AppConstant.FAIL;
				userNameError = appMessage.userUsernameRestrictedchar2Error;
			}
		}
		if (AppCommon.isEmpty(firstName)) {
			status = AppConstant.FAIL;
			firstNameError = appMessage.mandatoryFieldError;
		} else if (!AppCommon.hasOnlyAlphabets(firstName)) {
			status = AppConstant.FAIL;
			firstNameError = appMessage.userFirstnameOnlyalphabetsError;
		}
		if (!AppCommon.isEmpty(middleName) && !AppCommon.hasOnlyAlphabets(middleName)) {
			status = AppConstant.FAIL;
			middleNameError = appMessage.userMiddlenameOnlyalphabetsError;
		}
		if (!AppCommon.isEmpty(lastName) && !AppCommon.hasOnlyAlphabets(lastName)) {
			status = AppConstant.FAIL;
			lastNameError = appMessage.userLastnameOnlyalphabetsError;
		}
		if (AppCommon.isEmpty(emailId)) {
			status = AppConstant.FAIL;
			emailIdError = appMessage.mandatoryFieldError;
		} else if (AppCommon.isNotValidEmail(emailId)) {
			status = AppConstant.FAIL;
			emailIdError = appMessage.invalidEmailError;
		}
		if (AppCommon.isEmpty(mobileNo)) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.mandatoryFieldError;
		} else if (AppCommon.isNotNumber(mobileNo)) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.onlyNumberError;
		} else if (mobileNo.length() != 10) {
			status = AppConstant.FAIL;
			mobileNoError = appMessage.userMobileLengthError;
		}
		if (AppCommon.isEmpty(rolesStr)) {
			status = AppConstant.FAIL;
			rolesError = appMessage.mandatoryFieldError;
		}

		if (AppCommon.isEmpty(status)) {
			if (hidUserId == 0) {
				final Long userNameCount = userRepository.countByUserName(userName);
				if (userNameCount > 0) {
					status = AppConstant.FAIL;
					userNameError = appMessage.userUsernameExistsError;
				}
				final Long emailIdCount = userRepository.countByEmailId(emailId);
				if (emailIdCount > 0) {
					status = AppConstant.FAIL;
					emailIdError = appMessage.userEmailExistsError;
				}
			} else {
				final Long emailIdCount = userRepository.countByEmailIdAndUserIdNot(emailId, hidUserId);
				if (emailIdCount > 0) {
					status = AppConstant.FAIL;
					emailIdError = appMessage.userEmailExistsError;
				}
			}
		}

		if (AppCommon.isEmpty(status)) {
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

			userRepository.save(user);
			final Integer userId = user.getUserId();

			if (AppCommon.isPositiveInteger(userId)) {
				status = AppConstant.SUCCESS;
				if (hidUserId > 0) {
					msg = appMessage.userUpdateSuccess;
				} else {
					msg = appMessage.userAddSuccess;
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
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
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		final String userIdStr = allRequestParams.getOrDefault("userId", "0");
		final String isActiveStr = allRequestParams.get("isActive");

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		Integer userId = AppCommon.toInteger(userIdStr);
		Integer isActive = AppCommon.toIntegerOrNull(isActiveStr);

		if (userId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.updateFailedError;
		}

		if (AppCommon.isEmpty(status)) {
			User user = userRepository.getOne(userId);
			user.setIsActive(isActive);

			userRepository.save(user);
			final Integer userIdUpdate = user.getUserId();

			if (AppCommon.isPositiveInteger(userIdUpdate)) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.userActiveSuccess;
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.userInactiveSuccess;
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}