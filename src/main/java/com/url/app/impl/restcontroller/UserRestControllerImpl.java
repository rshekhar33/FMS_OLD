package com.url.app.impl.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.UserMng;
import com.url.app.interf.restcontroller.UserRestController;
import com.url.app.interf.service.AppUserService;

/**
 * Rest Controller for user related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
public class UserRestControllerImpl implements UserRestController {

	@Autowired
	private AppUserService appUserService;

	@Override
	public List<UserMng> fetchDetails() {
		return appUserService.fetchDetailsUsers();
	}

	@Override
	public Map<String, Object> fetchData(final String userIdStr) {
		return appUserService.fetchDataUser(userIdStr);
	}

	@Override
	public Map<String, String> validateSave(final Map<String, String> allRequestParams) {
		return appUserService.validateSaveUser(allRequestParams);
	}

	@Override
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		return appUserService.validateUpdateActivation(allRequestParams);
	}
}