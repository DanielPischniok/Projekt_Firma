package de.jhdp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.ejb.Singleton;

@Singleton
public class ProjectService {
	
	public String generatePassword(String pwd){

		MessageDigest md;
		try {
			byte[] bytesOfMessage = pwd.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			return Base64.getEncoder().encodeToString(thedigest);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
