package com.url.app.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.url.app.dto.User;
import com.url.app.interf.service.AppUserService;

/**
 * Logging Aspect.
 * 
 * @author Shekhar Shinde
 */
@Aspect
@Component
public class AppLogger {
	private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired
	private AppUserService appUserService;

	@Around("(within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)) "
			+ "&& (execution(* com.url.app.interf.controller.*.*(..)) || execution(* com.url.app.interf.restcontroller.*.*(..)))")
	public Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			final User user = principalUser();

			logger.info("UserId : {} : UserName : {} : Inside URL : '{}' : Method type : {}", user.getUserId(), user.getUserName(), request.getServletPath(),
					request.getMethod());
			logger.debug(getPreMessage(user, joinPoint));

			final StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			final Object retVal = joinPoint.proceed();

			stopWatch.stop();

			logger.debug(getPostMessage(user, joinPoint, retVal, stopWatch.getTotalTimeMillis()));

			return retVal;
		} catch (final Throwable ex) {
			//logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	public User principalUser() {
		User user = appUserService.getPrincipalUser();
		if (user == null) {
			user = new User();
		}

		return user;
	}

	private static String getPreMessage(final User user, final JoinPoint joinPoint) {
		//@formatter:off
		final StringBuilder builder = new StringBuilder()
				.append("UserId : ").append(user.getUserId())
				.append(" : UserName : ").append(user.getUserName())
				.append(" : Entered with values : (");

		appendMethodParamValues(builder, joinPoint);

		return builder
				.append(") : in method : ").append(joinPoint.getSignature().toString())
				.toString();
		//@formatter:on
	}

	private static String getPostMessage(final User user, final JoinPoint joinPoint, final Object retVal, final long millis) {
		//@formatter:off
		return new StringBuilder()
				.append("UserId : ").append(user.getUserId())
				.append(" : UserName : ").append(user.getUserName())
				.append(" : Exited with execution time : ").append(millis)
				.append(" ms : with return value : ").append(retVal)
				.append(" : from method : ").append(joinPoint.getSignature().toString())
				.toString();
		//@formatter:on
	}

	private static void appendMethodParamValues(final StringBuilder builder, final JoinPoint joinPoint) {
		final Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				builder.append(", ");
			}
			builder.append(args[i]);
		}
	}
}