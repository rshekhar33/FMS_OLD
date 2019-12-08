package com.url.app.interf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url.app.utility.AppUrlView;

/**
 * Controller for role related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_ROLE)
public interface RoleController {

	/**
	 * Roles Listing Screen.
	 */
	@GetMapping(value = AppUrlView.URL_LIST)
	public String list(final HttpSession httpSess, final ModelMap modelMap);

	/**
	 * Add role screen.
	 */
	@GetMapping(value = AppUrlView.URL_ADD)
	public String add(final HttpSession httpSess, final ModelMap modelMap);

	/**
	 * Action to get roleId on update role screen.
	 */
	@PostMapping(value = AppUrlView.URL_UPDATE)
	public String update(final HttpSession httpSess, @RequestParam("linkId") final String linkId);

	/**
	 * Update role screen.
	 */
	@GetMapping(value = AppUrlView.URL_UPDATE)
	public String updateScreen(final HttpSession httpSess, final ModelMap modelMap);
}