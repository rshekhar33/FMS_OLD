package com.url.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = RoleNameNotExistsValidator.class)
public @interface RoleNameNotExists {

	String message() default "{role.rolename.exists.error}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}