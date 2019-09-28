package com.url.app.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.url.app.utility.AppUrlView;

/**
 * Handles requests for the application Login/Registration/Access Denied/Invalid Session/Expired Sessions page.
 * 
 * @author Shekhar Shinde
 */
@Controller
public class LoginController {

	/**
	 * Index/Login Page of application.
	 */
	@GetMapping(value = { AppUrlView.URL_ROOT, AppUrlView.URL_LOGIN })
	public String index(final Locale locale, final Principal principal) {
		String url = AppUrlView.REDIRECT_URL_HOME;
		if (principal == null) {
			url = AppUrlView.VIEW_LOGIN;
		}

		return url;
	}

	/**
	 * HomePage/Dashboard of Application.
	 */
	@GetMapping(value = AppUrlView.URL_HOME)
	public String home() {
		return AppUrlView.REDIRECT_URL_DASHBOARD;
	}

	/**
	 * SignUp/Registration URL for Users.
	 */
	@GetMapping(value = AppUrlView.URL_SIGN_UP)
	public String signUp() {
		return AppUrlView.VIEW_SIGN_UP;
	}

	/**
	 * View when access is denied.
	 * 
	 * @return name of the view which is to be rendered when access is denied.
	 */
	@RequestMapping(value = AppUrlView.URL_ACCESS_DENIED, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public String accessDeniedPage() {
		return AppUrlView.VIEW_ACCESS_DENIED;
	}

	/**
	 * View when application is accessed using invalid session id.
	 * 
	 * @return name of the view which is to be rendered when session is invalid.
	 */
	@RequestMapping(value = AppUrlView.URL_INVALID_SESSION, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public String invalidSessionPage() {
		return AppUrlView.VIEW_INVALID_SESSION;
	}

	/**
	 * View when session has expired.
	 * 
	 * @return name of the view which is to be rendered when session has expired.
	 */
	@RequestMapping(value = AppUrlView.URL_SESSION_EXPIRED, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public String sessionExpiredPage() {
		return AppUrlView.VIEW_SESSION_EXPIRED;
	}
}