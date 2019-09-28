package com.url.app.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.url.app.utility.AppConstant;

/**
 * Spring mvc configuration of application.
 * 
 * @author Shekhar Shinde
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);

		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(AppConstant.SPRING_VIEW_RESOLVER_PREFIX);
		resolver.setSuffix(AppConstant.SPRING_VIEW_RESOLVER_SUFFIX);
		resolver.setExposedContextBeanNames("appAuthorization");
		registry.viewResolver(resolver);
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(AppConstant.MAX_UPLOAD_SIZE_IN_MB);
		commonsMultipartResolver.setMaxUploadSizePerFile(AppConstant.MAX_UPLOAD_SIZE_PER_FILE_IN_MB);

		return commonsMultipartResolver;
	}

	@Bean
	public MessageSource messageSource() {
		final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename(AppConstant.SPRING_SECURITY_MSG_FILE_BASENAME);

		return resourceBundleMessageSource;
	}
}