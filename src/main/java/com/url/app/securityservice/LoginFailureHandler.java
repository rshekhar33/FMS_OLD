package com.url.app.securityservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.url.app.config.AppMessage;
import com.url.app.service.AppUserService;
import com.url.app.utility.AppHttpSessionKey;

/**
 * Login failure handler class.
 * 
 * @author Shekhar Shinde
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppMessage appMessage;

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
			throws IOException, ServletException {
		final String userName = request.getParameter("userName");

		appUserService.userUpdateLastLoginFailure(userName);

		String loginErrorMessage = appMessage.getMessage("login.error");
		if (!(exception instanceof InternalAuthenticationServiceException)) {
			loginErrorMessage = exception.getMessage();
		}

		logger.debug("Authentication for userName = '{}' failed due to = {}", userName, loginErrorMessage);

		request.getSession().setAttribute(AppHttpSessionKey.LOGIN_ERROR, loginErrorMessage);

		super.onAuthenticationFailure(request, response, exception);
	}
}