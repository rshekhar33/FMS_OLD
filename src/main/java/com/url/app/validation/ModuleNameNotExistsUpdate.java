package com.url.app.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = ModuleNameNotExistsUpdateValidator.class)
public @interface ModuleNameNotExistsUpdate {

	String message() default "{module.modulename.exists.error}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}