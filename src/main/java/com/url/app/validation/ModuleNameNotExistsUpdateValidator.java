package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.dto.Module;
import com.url.app.interf.dao.ModuleRepository;

@Component
public class ModuleNameNotExistsUpdateValidator implements ConstraintValidator<ModuleNameNotExistsUpdate, Module> {

	@Autowired
	private ModuleRepository moduleRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(Module module, ConstraintValidatorContext context) {
		final Long moduleNameCount = moduleRepository.countByModuleNameAndModuleIdNot(module.getModuleName(), module.getModuleId());
		return moduleNameCount == 0;
	}
}