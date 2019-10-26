package com.jiaoyu.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * Ê¹ÓÃmd5µÄËã·¨½øÐÐ¼ÓÃÜ
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Ã»ÓÐmd5Õâ¸öËã·¨£¡");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16½øÖÆÊý×Ö
		// Èç¹ûÉú³ÉÊý×ÖÎ´Âú32Î»£¬ÐèÒªÇ°Ãæ²¹0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
		System.out.println(md5("123"));
	}

}

