package com.url.app.interf.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.url.app.utility.AppUrlView;

/**
 * Controller for dashboard related actions.
 * 
 * @author Shekhar Shinde
 */
@RequestMapping(value = AppUrlView.URL_ROOT_DASHBOARD)
public interface DashboardController {

	/**
	 * Dashboard of Application.
	 */
	@GetMapping(value = AppUrlView.URL_DASHBOARD)
	public String dashboard(final ModelMap modelMap);
}