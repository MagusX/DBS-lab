package com.bmcsdl185.lab.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private byte[] hash;

	public Hash(String algorithm, String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
	}

	public byte[] getHash() {
		return hash;
	}

	public String toHex() {
		char[] hexChars = new char[hash.length * 2];
		for (int j = 0; j < hash.length; j++) {
			int v = hash[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public Boolean equals(String hashString) {
		return Arrays.equals(hash, hashString.getBytes(StandardCharsets.UTF_8));
	}
}
