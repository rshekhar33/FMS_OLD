package com.url.app.validation;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.url.app.dto.User;

@Service
@Validated
public class AppUserValidationService {

	@Validated(value = ValidationCreateSequence.class)
	public void validateForCreate(@Valid User user) {
	}

	@Validated(value = { ValidationUpdateSequence.class })
	public void validateForUpdate(@Valid User user) {
	}

	@Validated(value = { ValidationActivateSequence.class })
	public void validateForActivate(@Valid User user) {
	}
}