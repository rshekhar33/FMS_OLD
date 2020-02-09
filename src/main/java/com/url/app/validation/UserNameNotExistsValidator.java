package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.interf.dao.UserRepository;

@Component
public class UserNameNotExistsValidator implements ConstraintValidator<UserNameNotExists, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		final Long userNameCount = userRepository.countByUserName(userName);
		return userNameCount == 0;
	}
}