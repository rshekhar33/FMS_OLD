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
 * Controller for faculty skillset related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
@RequestMapping(value = AppUrlView.URL_ROOT_FACULTY_SKILLSET)
public class FacultySkillsetController {
	private static final Logger logger = LoggerFactory.getLogger(FacultySkillsetController.class);

	/**
	 * Faculty Skillsets Listing Screen.
	 */
	@GetMapping(value = AppUrlView.URL_LIST)
	public String list(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_USER_ID);
		httpSess.removeAttribute(AppHttpSessionKey.HID_MODULE_ID);
		modelMap.addAttribute(AppCssActiveClass.FACULTY_SKILLSETS_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_FACULTY_SKILLSET_LIST;
	}

	/**
	 * Add faculty skillset screen.
	 */
	@GetMapping(value = AppUrlView.URL_ADD)
	public String add(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_USER_ID);
		httpSess.removeAttribute(AppHttpSessionKey.HID_MODULE_ID);
		modelMap.addAttribute(AppCssActiveClass.FACULTY_SKILLSET_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_FACULTY_SKILLSET_CRUD;
	}

	/**
	 * Action to get facultySkillsetId on update faculty skillset screen.
	 */
	@PostMapping(value = AppUrlView.URL_UPDATE)
	public String update(final HttpSession httpSess, @RequestParam("linkId") final String linkId, @RequestParam("linkId2") final String linkId2) {
		httpSess.setAttribute(AppHttpSessionKey.HID_USER_ID, linkId);
		httpSess.setAttribute(AppHttpSessionKey.HID_MODULE_ID, linkId2);

		return AppUrlView.REDIRECT_URL_FACULTY_SKILLSET_UPDATE;
	}

	/**
	 * Update faculty skillset screen.
	 */
	@GetMapping(value = AppUrlView.URL_UPDATE)
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap) {
		final String userId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_USER_ID);
		final String moduleId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_MODULE_ID);
		logger.debug("userId = {}, moduleId = {}", userId, moduleId);

		String url = AppUrlView.REDIRECT_URL_FACULTY_SKILLSET_LIST;
		if (userId != null && !AppConstant.BLANK_STRING.equals(userId) && moduleId != null && !AppConstant.BLANK_STRING.equals(moduleId)) {
			modelMap.addAttribute(AppCssActiveClass.FACULTY_SKILLSET_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);
			url = AppUrlView.VIEW_FACULTY_SKILLSET_CRUD;
		}

		return url;
	}
}