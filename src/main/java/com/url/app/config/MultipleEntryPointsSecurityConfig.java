package com.url.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.multipart.support.MultipartFilter;

import com.url.app.securityservice.AuthenticationService;
import com.url.app.securityservice.DbFilterInvocationSecurityMetadataSource;
import com.url.app.securityservice.LoginFailureHandler;
import com.url.app.securityservice.LoginSuccessHandler;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppUrlView;

/**
 * Spring security configuration for different url patterns.
 * 
 * @author Shekhar Shinde
 */
@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public MultipartFilter multipartFilter() {
		return new MultipartFilter();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authenticationService);
		authProvider.setPasswordEncoder(passwordEncoder);

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		final LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
		loginFailureHandler.setDefaultFailureUrl(AppUrlView.URL_ROOT + AppUrlView.URL_LOGIN + "?error");

		return loginFailureHandler;
	}

	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private DbFilterInvocationSecurityMetadataSource dbFilterInvocationSecurityMetadataSource;

		@Autowired
		private DaoAuthenticationProvider daoAuthenticationProvider;

		@Autowired
		private LoginFailureHandler loginFailureHandler;

		@Autowired
		private SessionRegistry sessionRegistry;

		@Autowired
		private AuthenticationService authenticationService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//@formatter:off
			http.antMatcher("/**").authorizeRequests()
					.antMatchers(AppUrlView.URL_ROOT, AppUrlView.URL_ROOT + AppUrlView.URL_LOGIN).permitAll()
					.anyRequest().authenticated()
				.and().addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
					.formLogin().loginPage(AppUrlView.URL_ROOT + AppUrlView.URL_LOGIN).usernameParameter("userName").passwordParameter("password")
					.failureHandler(loginFailureHandler).successHandler(loginSuccessHandlerWeb())
				.and().rememberMe().userDetailsService(authenticationService).rememberMeParameter("remember").rememberMeCookieName("rememberMeLogin")
				.and().sessionManagement().invalidSessionUrl(AppUrlView.URL_ROOT + AppUrlView.URL_INVALID_SESSION).maximumSessions(1)
					.expiredUrl(AppUrlView.URL_ROOT + AppUrlView.URL_SESSION_EXPIRED).sessionRegistry(sessionRegistry).and()
				.and().logout().logoutUrl(AppUrlView.URL_ROOT + AppUrlView.URL_LOGOUT).logoutSuccessUrl(AppUrlView.URL_ROOT + AppUrlView.URL_LOGIN)
					.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.and().exceptionHandling().accessDeniedPage(AppUrlView.URL_ROOT + AppUrlView.URL_ACCESS_DENIED)
				.and().csrf()
				.and().headers().cacheControl()
				.and().contentTypeOptions()
				.and().frameOptions()
				.and().httpStrictTransportSecurity()
				.and().xssProtection();
			//@formatter:on
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(daoAuthenticationProvider);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(AppConstant.SPRING_SECURITY_IGNORE_PATTERNS);
		}

		@Bean
		public LoginSuccessHandler loginSuccessHandlerWeb() {
			final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
			loginSuccessHandler.setDefaultTargetUrl(AppUrlView.URL_ROOT + AppUrlView.URL_HOME);

			return loginSuccessHandler;
		}

		public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
			final FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
			filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
			filterSecurityInterceptor.setSecurityMetadataSource(dbFilterInvocationSecurityMetadataSource);

			return filterSecurityInterceptor;
		}

		public AffirmativeBased accessDecisionManager() throws Exception {
			final List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
			final RoleVoter voter = new RoleVoter();
			voter.setRolePrefix(AppConstant.BLANK_STRING);
			voters.add(voter);
			final AffirmativeBased affirmativeBased = new AffirmativeBased(voters);
			affirmativeBased.afterPropertiesSet();

			return affirmativeBased;
		}
	}
}