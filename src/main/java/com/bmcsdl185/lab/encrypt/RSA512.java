package com.bmcsdl185.lab.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public class RSA512 {
	private final static int KEY_LENGTH = 512;
	private final static String ALGORITHM = "RSA";

	@Autowired
	private Utils utils;

	public KeyPair createKeyPair(String password, String salt) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 20, KEY_LENGTH);
		SecretKey key = factory.generateSecret(spec);

		SecureRandom keyGenRand = SecureRandom.getInstance("SHA1PRNG");
		keyGenRand.setSeed(key.getEncoded());

		KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
		generator.initialize(KEY_LENGTH, keyGenRand);
		return generator.generateKeyPair();
	}

	public byte[] encrypt(byte[] publicKey, byte[] inputData) throws Exception {
		PublicKey key = KeyFactory.getInstance(ALGORITHM)
				.generatePublic(new X509EncodedKeySpec(publicKey));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(inputData);
	}

	public byte[] decrypt(byte[] privateKey, byte[] inputData) throws Exception {
		PrivateKey key = KeyFactory.getInstance(ALGORITHM)
				.generatePrivate(new PKCS8EncodedKeySpec(privateKey));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);

		return cipher.doFinal(inputData);
	}
}
