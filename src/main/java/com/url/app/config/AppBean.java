package com.url.app.config;

import java.nio.charset.StandardCharsets;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import com.url.app.securityservice.AppUserDetailsService;
import com.url.app.securityservice.LoginFailureHandler;
import com.url.app.securityservice.LoginSuccessHandler;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppUrlView;

/**
 * @author Shekhar Shinde
 */
@Configuration
public class AppBean {

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(AppUserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(appUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MultipartFilter multipartFilter() {
		return new MultipartFilter();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandlerWeb() {
		final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
		loginSuccessHandler.setDefaultTargetUrl(AppUrlView.URL_LOGIN_SUCCESS);

		return loginSuccessHandler;
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		final LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
		loginFailureHandler.setDefaultFailureUrl(AppUrlView.URL_LOGIN_FAILURE);

		return loginFailureHandler;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(AppConstant.MAX_UPLOAD_SIZE_IN_BYTES);
		commonsMultipartResolver.setMaxUploadSizePerFile(AppConstant.MAX_UPLOAD_SIZE_PER_FILE_IN_BYTES);

		return commonsMultipartResolver;
	}

	@Bean
	public MessageSource messageSource() {
		final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename(AppConstant.SPRING_SECURITY_MSG_FILE_BASENAME);

		return resourceBundleMessageSource;
	}

	@Bean
	public MessageSource validationMessageSource() {
		final ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasename(AppConstant.SPRING_VALIDATION_MSG_FILE_BASENAME);
		reloadableResourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

		return reloadableResourceBundleMessageSource;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource validationMessageSource) {
		final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(validationMessageSource);

		return localValidatorFactoryBean;
	}

	@Bean
	public StringEncryptor stringEncryptor() {
		final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(AppConstant.STRING_ENCRYPTOR_PASSWORD);
		config.setKeyObtentionIterations(1000);
		config.setPoolSize(1);
		config.setProviderName(AppConstant.STRING_ENCRYPTOR_PROVIDER_NAME);
		config.setSaltGenerator(new RandomSaltGenerator());
		config.setStringOutputType(CommonUtils.STRING_OUTPUT_TYPE_BASE64);

		final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setConfig(config);

		return encryptor;
	}
}