package solo.solobasepackage.language;

import java.util.HashMap;

public class Language {

	private Language(){
		
	}
	
	public static HashMap<String, HashMap<String, String>> langs = new HashMap<>();
	
	public static void register(String lang, String key, String message){
		if(! langs.containsKey(lang)){
			langs.put(lang, new HashMap<String, String>());
		}
		langs.get(lang).put(key, message);
	}
	
	public static String get(String lang, String key, String... strings){
		if(langs.containsKey(lang)){
			if(langs.get(lang).containsKey(key)){
				String ret = langs.get(lang).get(key);
				for(int i = 0; i < strings.length; i++){
					ret.replace("{%" + Integer.toString(i) + "}", strings[i]);
				}
				return ret;
			}
		}
		return null;
	}
}
