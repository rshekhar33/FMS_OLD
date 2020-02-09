package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.dto.User;
import com.url.app.interf.dao.UserRepository;

@Component
public class EmailIdNotExistsUpdateValidator implements ConstraintValidator<EmailIdNotExistsUpdate, User> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(User user, ConstraintValidatorContext context) {
		final Long emailIdCount = userRepository.countByEmailIdAndUserIdNot(user.getEmailId(), user.getUserId());
		return emailIdCount == 0;
	}
}