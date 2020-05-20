package com.url.app.impl.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.url.app.interf.controller.RoleController;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppCssActiveClass;
import com.url.app.utility.AppHttpSessionKey;
import com.url.app.utility.AppLogMessage;
import com.url.app.utility.AppUrlView;

/**
 * Controller for role related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
public class RoleControllerImpl implements RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleControllerImpl.class);

	@Override
	public String list(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_ROLE_ID);
		AppCssActiveClass.addClass(modelMap, AppCssActiveClass.ROLE_MENU_OPEN_CLS, AppCssActiveClass.ROLES_ACTIVE_CLS);

		return AppUrlView.VIEW_ROLE_LIST;
	}

	@Override
	public String add(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_ROLE_ID);
		AppCssActiveClass.addClass(modelMap, AppCssActiveClass.ROLE_MENU_OPEN_CLS, AppCssActiveClass.ROLE_CRUD_ACTIVE_CLS);

		return AppUrlView.VIEW_ROLE_CRUD;
	}

	@Override
	public String update(final HttpSession httpSess, final String linkId) {
		httpSess.setAttribute(AppHttpSessionKey.HID_ROLE_ID, linkId);

		return AppUrlView.REDIRECT_URL_ROLE_UPDATE;
	}

	@Override
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap) {
		final String roleId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_ROLE_ID);
		logger.debug(AppLogMessage.ROLE_ID_MSG, roleId);

		String url = AppUrlView.REDIRECT_URL_ROLE_LIST;
		if (!AppCommon.isEmpty(roleId)) {
			AppCssActiveClass.addClass(modelMap, AppCssActiveClass.ROLE_MENU_OPEN_CLS, AppCssActiveClass.ROLE_CRUD_ACTIVE_CLS);
			url = AppUrlView.VIEW_ROLE_CRUD;
		}

		return url;
	}
}