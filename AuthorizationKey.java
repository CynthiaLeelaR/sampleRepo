package com.cynthia.base;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.sun.jersey.core.util.Base64;

/**
 *@Scenario : For generating the authorization key for the  every specificuri
 *@author cylee1
 *@Date 09 Aug 2017
 *@modified 07 Sep 2017
 *
 */

public class AuthorizationKey extends MainBase {
	private static final Logger LOGGER = Logger.getLogger(AuthorizationKey.class);
	static String finalAuth;
	
	
  public static  String authKey(String url,String method) throws InvalidKeyException, InterruptedException {

	 try {
		 String urlFinal =url.replaceAll("\\?","&");
			urlFinal=urlFinal.replaceAll(" ","%20");
			urlFinal=urlFinal.replaceAll(",","%2C");
  		String message = urlFinal+"&"+method+"&";
	    String publicKey=prop.getProperty("SUPPLYITEM_PUBLIC_KEY")+":";
	    Mac hasher = Mac.getInstance("HmacSHA256");
	    hasher.init(new SecretKeySpec((prop.getProperty("SUPPLYITEM_PRIVATE_KEY")).getBytes(), "HmacSHA256"));
	    byte[] hash = hasher.doFinal(message.getBytes());
	    String hash2 = DatatypeConverter.printBase64Binary(hash);
	    String toEncrypt=publicKey+hash2;
	    byte[] encodedBytes = Base64.encode(toEncrypt.getBytes());
	    String Auth=new String(encodedBytes);
	    finalAuth="Basic "+Auth;
	    Thread.sleep(4000);
	    LOGGER.debug(finalAuth);
//	    byte[] decodedBytes = Base64.decode(encodedBytes);
//	    LOGGER.debug("decodedBytes " + new String(decodedBytes));
	  
   	}
  	
  	 catch (NoSuchAlgorithmException e) {
        
      }
	return finalAuth;
	}

}
	


