package csf.common;

import org.apache.commons.codec.binary.Base64;

public class EncryptDecrypt {
	
	public static void main(String args[]) {
		
		
		String strpwd = "";
		int option = 1;
		
		switch(option) {
		case 1:
		{
			byte[] encoded = Base64.encodeBase64(strpwd.getBytes());
			String encodedString = new String(encoded);
			System.out.println("Encoded Password : "+encodedString);
		}break;
		
		case 2:
		{
			byte[] decoded = Base64.decodeBase64(strpwd.getBytes());
			String decodedString = new String(decoded);
			System.out.println("Decoded Password : "+decodedString);
		}break;
		default:
			System.out.println("Enter Valid option");		
		}
	}
}
