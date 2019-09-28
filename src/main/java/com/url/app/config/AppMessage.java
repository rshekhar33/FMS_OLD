package com.url.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/message.properties")
public class AppMessage {

	@Autowired
	private Environment env;

	public String getMessage(String property) {
		return env.getProperty(property);
	}

	public String getMessage(String property, String regex, String replacement) {
		final String message = env.getProperty(property);
		return message.replaceFirst(regex, replacement);
	}
}