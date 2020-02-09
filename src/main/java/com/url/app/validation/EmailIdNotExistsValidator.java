package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.interf.dao.UserRepository;

@Component
public class EmailIdNotExistsValidator implements ConstraintValidator<EmailIdNotExists, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(String emailId, ConstraintValidatorContext context) {
		final Long emailIdCount = userRepository.countByEmailId(emailId);
		return emailIdCount == 0;
	}
}