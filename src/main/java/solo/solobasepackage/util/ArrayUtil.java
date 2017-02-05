package solo.solobasepackage.util;

import java.util.ArrayList;

public class ArrayUtil{
	
	private ArrayUtil(){
		
	}
	
	public static String implode(String glue, ArrayList<String> array){
		return implode(glue, array.stream().toArray(String[]::new));
	}
	
	public static String implode(String glue, String[] array){
		StringBuilder sb = new StringBuilder();
		boolean f = true;
		for(String a : array){
			if(f){
				f = false;
			}else{
				sb.append(glue);
			}
			sb.append(a);
		}
		return sb.toString();
	}
}