package de.jhdp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ProjectUtils {
	
	public String generatePassword(){
		String pwd = "123";
		

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
