package com.url.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.url.app.utility.AppCssActiveClass;
import com.url.app.utility.AppUrlView;

/**
 * Controller for dashboard related actions.
 * 
 * @author Shekhar Shinde
 */
@Controller
@RequestMapping(value = AppUrlView.URL_ROOT_DASHBOARD)
public class DashboardController {

	/**
	 * Dashboard of Application.
	 */
	@GetMapping(value = AppUrlView.URL_DASHBOARD)
	public String dashboard(final ModelMap modelMap) {
		modelMap.addAttribute(AppCssActiveClass.DASHBOARD_ACTIVE_CLS, AppCssActiveClass.ACTIVE_CLASS);

		return AppUrlView.VIEW_DASHBOARD;
	}
}