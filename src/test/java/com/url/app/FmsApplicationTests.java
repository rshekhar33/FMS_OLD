package com.url.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.url.app.dto.User;
import com.url.app.validation.ValidationActivateSequence;

@SpringBootTest
public class FmsApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(FmsApplicationTests.class);

	@Autowired
	private StringEncryptor stringEncryptor;

	@Autowired
	private LocalValidatorFactoryBean localValidatorFactoryBean;

	@Test
	public void contextLoads() {
	}

	@Test
	public void jasyptEncDecr() {
		final String plainText = "fms";
		logger.info("plainText : {}", plainText);

		final String encryptText = stringEncryptor.encrypt(plainText);
		logger.info("encryptText : {}", encryptText);

		final String decryptText = stringEncryptor.decrypt(encryptText);
		logger.info("decryptText : {}", decryptText);

		assertEquals(plainText, decryptText);
	}

	@Test
	public void validationTest() {
		final User user = new User();

		final Validator validator = localValidatorFactoryBean.getValidator();
		final Set<ConstraintViolation<User>> violations = validator.validate(user, ValidationActivateSequence.class);
		logger.info("violations : {}", violations);

		assertEquals(2, violations.size());
	}
}