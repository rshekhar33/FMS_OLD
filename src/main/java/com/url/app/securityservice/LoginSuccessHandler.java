package com.url.app.securityservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.url.app.interf.service.AppUserService;
import com.url.app.utility.AppLogMessage;

/**
 * Login success handler class.
 * 
 * @author Shekhar Shinde
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Autowired
	private AppUserService appUserService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
			throws IOException, ServletException {
		logger.debug(AppLogMessage.LOGIN_SUCCESS_WITH_ROLES_MSG, appUserService.getPrincipal().getAuthorities());

		appUserService.userUpdateLastLoginSuccess();

		super.onAuthenticationSuccess(request, response, authentication);
	}
}