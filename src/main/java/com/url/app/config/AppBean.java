package com.url.app.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource validationMessageSource;

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
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
		loginSuccessHandler.setDefaultTargetUrl(AppUrlView.URL_ROOT + AppUrlView.URL_HOME);

		return loginSuccessHandler;
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		final LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
		loginFailureHandler.setDefaultFailureUrl(AppUrlView.URL_ROOT + AppUrlView.URL_LOGIN + "?error");

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
		reloadableResourceBundleMessageSource.setBasename("classpath:messages/validation-message");
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");

		return reloadableResourceBundleMessageSource;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(validationMessageSource);

		return localValidatorFactoryBean;
	}

	@Bean
	public StringEncryptor stringEncryptor() {
		final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("fmsEncryptKey");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setConfig(config);

		return encryptor;
	}
}