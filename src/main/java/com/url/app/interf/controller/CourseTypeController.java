package com.url.app.interf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.utility.AppUrlView;

/**
 * Controller for course type related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_COURSE_TYPE)
public interface CourseTypeController {

	/**
	 * Course Types Listing Screen.
	 */
	@GetMapping(value = AppUrlView.URL_LIST)
	String list(HttpSession httpSess, ModelMap modelMap);

	/**
	 * Add course type screen.
	 */
	@GetMapping(value = AppUrlView.URL_ADD)
	String add(HttpSession httpSess, ModelMap modelMap);

	/**
	 * Action to get courseTypeId on update course type screen.
	 */
	@PostMapping(value = AppUrlView.URL_UPDATE)
	String update(HttpSession httpSess, @RequestParam("linkId") String linkId);

	/**
	 * Update course type screen.
	 */
	@GetMapping(value = AppUrlView.URL_UPDATE)
	String updateScreen(HttpSession httpSess, ModelMap modelMap);
}