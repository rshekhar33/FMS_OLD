package com.url.app.validation;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.url.app.dto.Role;

@Service
@Validated
public class AppRoleValidationService {

	@Validated(value = ValidationCreateSequence.class)
	public void validateForCreate(@Valid Role role) {
	}

	@Validated(value = { ValidationUpdateSequence.class })
	public void validateForUpdate(@Valid Role role) {
	}

	@Validated(value = { ValidationActivateSequence.class })
	public void validateForActivate(@Valid Role role) {
	}
}