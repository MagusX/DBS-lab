package com.bmcsdl185.lab.encrypt;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Utils {
	public String toHexString(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();

		for (byte aByte : bytes) {
			String hex = Integer.toHexString(0xFF & aByte);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public byte[] toByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}

		return Arrays.copyOfRange(data, 1, len / 2);
	}
}
