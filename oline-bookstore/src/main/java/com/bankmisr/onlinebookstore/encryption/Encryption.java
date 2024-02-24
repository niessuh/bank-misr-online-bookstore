package com.bankmisr.onlinebookstore.encryption;

import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Encryption implements PasswordEncoder {

	@Value("${encryption.key.pass}")
	private String keyPass;

	StrongTextEncryptor textEncryptor = new StrongTextEncryptor();

	@PostConstruct
	private void init() {
		textEncryptor.setPassword(keyPass);
	}

	public String decrypt(String encryptedPass) {

		return textEncryptor.decrypt(encryptedPass);

	}

	@Override
	public String encode(CharSequence rawPassword) {
		return textEncryptor.encrypt(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence pass, String encryptedPass) {

		String decryptedText = decrypt(encryptedPass);

		return pass.equals(decryptedText);
	}

}