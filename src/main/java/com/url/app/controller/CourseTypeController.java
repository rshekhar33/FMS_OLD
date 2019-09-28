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
 * Controller for course type related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
@RequestMapping(value = AppUrlView.URL_ROOT_COURSE_TYPE)
public class CourseTypeController {
	private static final Logger logger = LoggerFactory.getLogger(CourseTypeController.class);

	/**
	 * Course Types Listing Screen.
	 */
	@GetMapping(value = AppUrlView.URL_LIST)
	public String list(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_COURSE_TYPE_ID);
		modelMap.addAttribute(AppCssActiveClass.COURSE_TYPES_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_COURSE_TYPE_LIST;
	}

	/**
	 * Add course type screen.
	 */
	@GetMapping(value = AppUrlView.URL_ADD)
	public String add(final HttpSession httpSess, final ModelMap modelMap) {
		httpSess.removeAttribute(AppHttpSessionKey.HID_COURSE_TYPE_ID);
		modelMap.addAttribute(AppCssActiveClass.COURSE_TYPE_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_COURSE_TYPE_CRUD;
	}

	/**
	 * Action to get courseTypeId on update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_UPDATE)
	public String update(final HttpSession httpSess, @RequestParam("linkId") final String linkId) {
		httpSess.setAttribute(AppHttpSessionKey.HID_COURSE_TYPE_ID, linkId);

		return AppUrlView.REDIRECT_URL_COURSE_TYPE_UPDATE;
	}

	/**
	 * Update course type screen.
	 */
	@GetMapping(value = AppUrlView.URL_UPDATE)
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap) {
		final String courseTypeId = (String) httpSess.getAttribute(AppHttpSessionKey.HID_COURSE_TYPE_ID);
		logger.debug("courseTypeId = {}", courseTypeId);

		String url = AppUrlView.REDIRECT_URL_COURSE_TYPE_LIST;
		if (courseTypeId != null && !AppConstant.BLANK_STRING.equals(courseTypeId)) {
			modelMap.addAttribute(AppCssActiveClass.COURSE_TYPE_CRUD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);
			url = AppUrlView.VIEW_COURSE_TYPE_CRUD;
		}

		return url;
	}
}