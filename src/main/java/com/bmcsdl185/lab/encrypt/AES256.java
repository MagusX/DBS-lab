package com.bmcsdl185.lab.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.HashMap;

@Service
public class AES256 {
	Logger logger = LoggerFactory.getLogger(AES256.class);
	private static HashMap<String, SecretKeySpec> storedKeys = new HashMap<>();
	@Autowired
	private Utils utils;

	public HashMap<String, SecretKeySpec> getStoredKeys() {
		return storedKeys;
	}

	public SecretKeySpec getKey(String name) {
		return storedKeys.get(name);
	}

	public SecretKeySpec getKeyIfNullCreate(String name, String password, String salt) {
		SecretKeySpec key = getKey(name);
		if (key == null) {
			try {
				return createKey(name, password, salt);
			} catch (Exception e) {
				logger.error("{}", e);
				return null;
			}
		}
		return key;
	}

	public SecretKeySpec createKey(String name, String password, String salt) {
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256); // AES-256
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			SecretKeySpec key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			storedKeys.put(name, key);
			return key;
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	public byte[] encrypt(String input, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
			BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(input.getBytes());
	}

	public String decrypt(byte[] cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(cipherText);
		return new String(plainText);
	}

	public String decrypt(String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		byte[] cipherBytes = utils.toByteArray(cipherText);
		return decrypt(cipherBytes, key);
	}
}
