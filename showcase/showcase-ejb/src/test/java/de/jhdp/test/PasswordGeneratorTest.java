package de.jhdp.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.Test;

public class PasswordGeneratorTest {
	
	@Test
	public void generatePassword(){
		String pwd = "test";
		

		MessageDigest md;
		try {
			byte[] bytesOfMessage = pwd.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			System.out.println(Base64.getEncoder().encodeToString(thedigest));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
