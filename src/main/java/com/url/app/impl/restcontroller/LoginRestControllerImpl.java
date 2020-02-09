package com.url.app.impl.restcontroller;

import org.springframework.web.bind.annotation.RestController;

import com.url.app.interf.restcontroller.LoginRestController;

/**
 * Rest Controller to handle API requests for the application Login/Registration Page.
 * 
 * @author Shekhar Shinde
 */
@RestController
public class LoginRestControllerImpl implements LoginRestController {
	/*private static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;

	@Autowired
	private SecurityContextRepository repository;

	@Override
	public Map<String, Object> loginCheck(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("userName = {} :: password = {}", userName, password);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
		try {
			final Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			repository.saveContext(SecurityContextHolder.getContext(), request, response);
			sessionAuthenticationStrategy.onAuthentication(auth, request, response);
			//rememberMeServices.loginSuccess(request, response, auth);
			final Map<String, String> json = new ConcurrentHashMap<>();
			json.put(AppResponseKey.STATUS, AppConstant.SUCCESS);
			json.put(AppConstant.MSG, AppConstant.BLANK_STRING);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return appService.loginErrorMessage(e);
		}
	}*/
}