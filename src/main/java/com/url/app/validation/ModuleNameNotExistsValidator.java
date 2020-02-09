package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.interf.dao.ModuleRepository;

@Component
public class ModuleNameNotExistsValidator implements ConstraintValidator<ModuleNameNotExists, String> {

	@Autowired
	private ModuleRepository moduleRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(String moduleName, ConstraintValidatorContext context) {
		final Long moduleNameCount = moduleRepository.countByModuleName(moduleName);
		return moduleNameCount == 0;
	}
}