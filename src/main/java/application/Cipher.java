package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cipher {
	private String loginHash = "", passwordHash = "";
	
	public String cipherLogin(String log) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(log.getBytes());
		StringBuffer buffer = new StringBuffer();
		byte[] bytee = messageDigest.digest();
		for(byte b : bytee) {
			buffer.append(String.format("%02x", b & 0xFF));
		}
		System.gc();
		loginHash = buffer.toString();
		buffer.delete(0, buffer.length());
		return loginHash;
	}
	
	public String cipherPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(pass.getBytes());
		StringBuffer buffer = new StringBuffer();
		byte[] bytee = messageDigest.digest();
		for(byte b : bytee) {
			buffer.append(String.format("%02x", b & 0xFF));
		}
		System.gc();
		passwordHash = buffer.toString();
		buffer.delete(0, buffer.length());
		return passwordHash;
	}

}
