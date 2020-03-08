package com.url.app.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.url.app.utility.AppValidationKey;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = ModuleNameNotExistsUpdateValidator.class)
public @interface ModuleNameNotExistsUpdate {

	String message() default AppValidationKey.MODULE_MODULENAME_EXISTS_ERROR;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}