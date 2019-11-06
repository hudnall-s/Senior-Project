package com.sam.rest.Authentication;

import org.apache.commons.codec.binary.Hex;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Authentication {
	
	
	public static boolean authenticate(String hash , int userId)
	{
		String hashedPassword = Sql.getUserPassHash(userId);
		if (hashedPassword.compareTo(hash) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// duplicate code, rename for authenticate, leaving now to not break anything
	
	public static boolean checkPassword(String hash, int userId) {
		String hashedPassword = Sql.getUserPassHash(userId);
		if (hashedPassword.compareTo(hash) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String getHash(int userId, String password) throws Exception {
		if (userId == 0) {
			throw new Exception();
		}
		String salt = getSalt(userId);
		int iterations = 10000;
		int keyLength = 4096;
		char[] passwordChars = password.toCharArray();
		byte[] saltBytes = salt.getBytes();

		byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
		String hashedString = Hex.encodeHexString(hashedBytes);
		return hashedString;
	}

	private static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations,
			final int keyLength) {

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			return res;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getSalt(int userId) {
		return Sql.getSalt(userId);
	}

	public static void generateHashSalt(int userId) {
		int n = 30;
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		Sql.insertSalt(userId, sb.toString());
	}
}
