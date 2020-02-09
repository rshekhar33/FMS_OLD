package com.url.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.dto.Role;
import com.url.app.interf.dao.RoleRepository;

@Component
public class RoleNameNotExistsUpdateValidator implements ConstraintValidator<RoleNameNotExistsUpdate, Role> {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(Role role, ConstraintValidatorContext context) {
		final Long roleNameCount = roleRepository.countByRoleNameAndRoleIdNot(role.getRoleName(), role.getRoleId());
		return roleNameCount == 0;
	}
}