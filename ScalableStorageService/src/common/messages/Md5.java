package common.messages;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import sun.security.util.Length;

public class Md5 {
	
	public String hashIpPort(String ip,String port) {
		byte[] bytesOfMessage = null;
		  bytesOfMessage = (ip+port).getBytes();

		  MessageDigest md = null;
		  try {
		   md = MessageDigest.getInstance("MD5");
		  } catch (NoSuchAlgorithmException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  byte[] thedigest = md.digest(bytesOfMessage);
		  
		  String MD5 = "";
		  
		  for (int i=0; i<thedigest.length;i++)
		   MD5=MD5+Integer.toHexString(thedigest[i]&0xff);
		  System.out.println(MD5); //verify-in string-human readable
		return MD5 ;
	}
	
	public String hashKeyValue(String key) {
		
		byte[] bytesOfMessage = null;
		  bytesOfMessage = (key).getBytes();

		  MessageDigest md = null;
		  try {
		   md = MessageDigest.getInstance("MD5");
		  } catch (NoSuchAlgorithmException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  byte[] thedigest = md.digest(bytesOfMessage);
		  
		  String MD5 = "";
		  
		  for (int i=0; i<thedigest.length;i++)
			  MD5=MD5+Integer.toHexString(thedigest[i]&0xff);
		  System.out.println(MD5); //verify-in string-human readable
		return MD5 ;
	}

	
	public static void sort_servers( ArrayList<String[]> S ){ //pass as an argument a list of Strings. Each string is a hex that represents a position in the circle
		int i, j;
		String[] temp;
		
		for(i = 0; i < S.size(); i++){
			for(j = 1; j < (S.size()-i); j++){
				if(Integer.parseInt(S.get(j-1).toString(),16) > Integer.parseInt(S.get(j).toString(),16) ){ //compare hex numbers
					temp = S.get(j-1);
					S.set(j-1, S.get(j));
					S.set(j, temp);
				}
			}
		}
	}
	
}