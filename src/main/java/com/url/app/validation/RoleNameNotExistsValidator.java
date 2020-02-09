package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.interf.dao.RoleRepository;

@Component
public class RoleNameNotExistsValidator implements ConstraintValidator<RoleNameNotExists, String> {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(String roleName, ConstraintValidatorContext context) {
		final Long roleNameCount = roleRepository.countByRoleName(roleName);
		return roleNameCount == 0;
	}
}