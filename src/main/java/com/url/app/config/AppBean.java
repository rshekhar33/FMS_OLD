package com.url.app.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shekhar Shinde
 */
@Configuration
public class AppBean {

	@Bean(name = "encryptorBean")
	public StringEncryptor stringEncryptor() {
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("fmsEncryptKey");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setConfig(config);

		return encryptor;
	}
}