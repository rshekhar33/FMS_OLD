package com.url.app.impl.restcontroller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.url.app.config.AppMessage;
import com.url.app.interf.restcontroller.GlobalExceptionRestController;
import com.url.app.utility.AppResponseKey;

/**
 * Global Error Handler for API's.
 * 
 * @author Shekhar Shinde
 */
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionRestControllerImpl implements GlobalExceptionRestController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionRestControllerImpl.class);

	@Autowired
	private AppMessage appMessage;

	@Override
	public Map<String, Object> handleNoHandlerException(HttpServletRequest request, NoHandlerFoundException e) {
		logger.error("Global NoHandlerFoundException at Location : {} with Exception : {}", request.getRequestURL(), e.getMessage());

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, HttpStatus.NOT_FOUND.value());
		json.put(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header"));
		json.put(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc"));

		return json;
	}

	@Override
	public Map<String, Object> handleMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
		logger.error("Global HttpRequestMethodNotSupportedException at Location : {} with Exception : {}", request.getRequestURL(), e.getMessage());

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, HttpStatus.METHOD_NOT_ALLOWED.value());
		json.put(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header"));
		json.put(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc2"));

		return json;
	}

	@Override
	public Map<String, Object> handleAllException(HttpServletRequest request, Exception e) {
		logger.error("Global Exception at Location : " + request.getRequestURL(), e);

		final Map<String, Object> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		json.put(AppResponseKey.EXCEPTION_HEADER, appMessage.getMessage("exception.header2"));
		json.put(AppResponseKey.EXCEPTION_DESC, appMessage.getMessage("exception.desc3"));

		return json;
	}
}