package com.url.app.impl.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.url.app.config.AppMessage;
import com.url.app.interf.controller.GlobalExceptionController;
import com.url.app.interf.service.AppUserService;
import com.url.app.utility.AppResponseKey;
import com.url.app.utility.AppUrlView;

/**
 * Global Error Handler.
 * 
 * @author Shekhar Shinde
 */
@ControllerAdvice(annotations = Controller.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionControllerImpl implements GlobalExceptionController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionControllerImpl.class);

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppMessage appMessage;

	@Override
	public ModelAndView handleNoHandlerException(HttpServletRequest request, NoHandlerFoundException e) {
		logger.error("Global NoHandlerFoundException at Location : {} with Exception : {}", request.getRequestURL(), e.getMessage());

		final ModelAndView mav = new ModelAndView(errorPage());
		mav.addObject(AppResponseKey.EXCEPTION_MSG, e.getMessage());
		mav.addObject(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header"));
		mav.addObject(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc"));

		return mav;
	}

	@Override
	public ModelAndView handleMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
		logger.error("Global HttpRequestMethodNotSupportedException at Location : {} with Exception : {}", request.getRequestURL(), e.getMessage());

		final ModelAndView mav = new ModelAndView(errorPage());
		mav.addObject(AppResponseKey.EXCEPTION_MSG, e.getMessage());
		mav.addObject(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header"));
		mav.addObject(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc2"));

		return mav;
	}

	@Override
	public ModelAndView handleAllException(HttpServletRequest request, Exception e) {
		logger.error("Global Exception at Location : " + request.getRequestURL(), e);

		final ModelAndView mav = new ModelAndView(errorPage());
		mav.addObject(AppResponseKey.EXCEPTION_MSG, e.getMessage());
		mav.addObject(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header2"));
		mav.addObject(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc3"));
		final StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		mav.addObject(AppResponseKey.EXCEPTION_STACK, stringWriter.toString());

		return mav;
	}

	@Override
	public String errorPage() {
		String view = AppUrlView.VIEW_GLOBAL_ERROR_PAGE;
		try {
			final Integer userId = appUserService.getPrincipalUserUserId();
			if (userId != null && userId > 0) {
				view = AppUrlView.VIEW_APP_ERROR_PAGE;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return view;
	}
}