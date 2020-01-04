package com.url.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FmsApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(FmsApplicationTests.class);

	@Autowired
	private StringEncryptor encryptorBean;

	@Test
	public void contextLoads() {
	}

	@Test
	public void jasyptEncDecr() {
		String plainText = "fms";
		logger.info("plainText : {}", plainText);

		String encryptText = encryptorBean.encrypt(plainText);
		logger.info("encryptText : {}", encryptText);

		String decryptText = encryptorBean.decrypt(encryptText);
		logger.info("decryptText : {}", decryptText);

		assertEquals(plainText, decryptText);
	}
}