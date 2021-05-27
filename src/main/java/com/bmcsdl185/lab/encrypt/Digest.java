package com.bmcsdl185.lab.encrypt;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class Digest {
	public byte[] hashBytes(String text, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return md.digest(text.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
