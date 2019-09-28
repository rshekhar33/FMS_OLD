package com.url.app.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.utility.AppConstant;
import com.url.app.utility.AppCssActiveClass;
import com.url.app.utility.AppHttpSessionKey;
import com.url.app.utility.AppUrlView;

/**
 * Controller for user related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
@RequestMapping(value = AppUrlView.URL_ROOT_USER)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * Users Listing Screen.
	 */
	@GetMapping(value = AppUrlView.URL_LIST)
	public String list(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_USER_ID);
		modelMap.addAttribute(AppCssActiveClass.USERS_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_USER_LIST;
	}

	/**
	 * Add user screen.
	 */
	@GetMapping(value = AppUrlView.URL_ADD)
	public String add(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_USER_ID);
		modelMap.addAttribute(AppCssActiveClass.USER_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_USER_CRUD;
	}

	/**
	 * Action to get userId on update user screen.
	 */
	@PostMapping(value = AppUrlView.URL_UPDATE)
	public String update(final HttpSession httpSess, @RequestParam("linkId") final String linkId) {
		httpSess.setAttribute(AppHttpSessionKey.HID_USER_ID, linkId);

		return AppUrlView.REDIRECT_URL_USER_UPDATE;
	}

	/**
	 * Update user screen.
	 */
	@GetMapping(value = AppUrlView.URL_UPDATE)
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap) {
		final String userId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_USER_ID);
		logger.debug("userId = {}", userId);

		String url = AppUrlView.REDIRECT_URL_USER_LIST;
		if (userId != null && !AppConstant.BLANK_STRING.equals(userId)) {
			modelMap.addAttribute(AppCssActiveClass.USER_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);
			url = AppUrlView.VIEW_USER_CRUD;
		}

		return url;
	}
}