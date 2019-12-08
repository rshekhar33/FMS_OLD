package com.url.app.impl.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.url.app.dto.Role;
import com.url.app.interf.restcontroller.RoleRestController;
import com.url.app.interf.service.AppRoleService;

/**
 * Role Controller for role related actions.
 * 
 * @author Shekhar Shinde
 */
@RestController
public class RoleRestControllerImpl implements RoleRestController {

	@Autowired
	private AppRoleService appRoleService;

	@Override
	public List<Role> fetchDetails() {
		return appRoleService.fetchDetailsRoles();
	}

	@Override
	public Map<String, Role> fetchData(final String roleIdStr) {
		return appRoleService.fetchDataRole(roleIdStr);
	}

	@Override
	public Map<String, String> validateSave(final Map<String, String> allRequestParams) {
		return appRoleService.validateSaveRole(allRequestParams);
	}

	@Override
	public Map<String, String> validateUpdateActivation(final Map<String, String> allRequestParams) {
		return appRoleService.validateUpdateActivation(allRequestParams);
	}
}