package com.url.app.validation;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.url.app.dto.Module;

@Service
@Validated
public class AppModuleValidationService {

	@Validated(value = ValidationCreateSequence.class)
	public void validateForCreate(@Valid Module module) {
	}

	@Validated(value = { ValidationUpdateSequence.class })
	public void validateForUpdate(@Valid Module module) {
	}

	@Validated(value = { ValidationActivateSequence.class })
	public void validateForActivate(@Valid Module module) {
	}
}