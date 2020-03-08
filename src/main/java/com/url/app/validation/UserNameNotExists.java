package com.url.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.url.app.utility.AppValidationKey;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = UserNameNotExistsValidator.class)
public @interface UserNameNotExists {

	String message() default AppValidationKey.USER_USERNAME_EXISTS_ERROR;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}