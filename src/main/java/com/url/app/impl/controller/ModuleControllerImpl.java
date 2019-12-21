package com.url.app.impl.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.url.app.interf.controller.ModuleController;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppCssActiveClass;
import com.url.app.utility.AppHttpSessionKey;
import com.url.app.utility.AppUrlView;

/**
 * Controller for module related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
public class ModuleControllerImpl implements ModuleController {
	private static final Logger logger = LoggerFactory.getLogger(ModuleControllerImpl.class);

	@Override
	public String list(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_MODULE_ID);
		modelMap.addAttribute(AppCssActiveClass.MODULES_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_MODULE_LIST;
	}

	@Override
	public String add(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_MODULE_ID);
		modelMap.addAttribute(AppCssActiveClass.MODULE_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_MODULE_CRUD;
	}

	@Override
	public String update(final HttpSession httpSess, final String linkId) {
		httpSess.setAttribute(AppHttpSessionKey.HID_MODULE_ID, linkId);

		return AppUrlView.REDIRECT_URL_MODULE_UPDATE;
	}

	@Override
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap) {
		final String moduleId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_MODULE_ID);
		logger.debug("moduleId = {}", moduleId);

		String url = AppUrlView.REDIRECT_URL_MODULE_LIST;
		if (!AppCommon.isEmpty(moduleId)) {
			modelMap.addAttribute(AppCssActiveClass.MODULE_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);
			url = AppUrlView.VIEW_MODULE_CRUD;
		}

		return url;
	}
}