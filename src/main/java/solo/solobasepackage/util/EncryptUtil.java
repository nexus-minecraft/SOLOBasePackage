package solo.solobasepackage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptUtil{
	
	private EncryptUtil(){
		
	}
	
	public static String certificationCode(){
		return certificationCode(2);
	}
	
	public static String certificationCode(int level){
		int length = 4;
		char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		if(level < 2){
			//NOTHING DO
		}else if(level >= 2){
			length = 6;
			if(level >= 3){
				chars = new char[]{
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
						'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
						'U', 'V', 'W', 'X', 'Y', 'Z'
						};
				if(level >= 4){
					length = 8;
					if(level >= 5){
						chars = new char[]{
								'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
								'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
								'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
								'U', 'V', 'W', 'X', 'Y', 'Z',
								'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
								'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
								'u', 'v', 'w', 'x', 'y', 'z'
								};
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			sb.append(chars[new Random().nextInt(chars.length - 1)]);
		}
		return sb.toString();
	}

	public static String sha256(String raw){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(raw.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
}