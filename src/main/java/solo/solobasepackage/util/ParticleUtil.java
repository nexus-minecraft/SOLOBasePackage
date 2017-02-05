package solo.solobasepackage.util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.nukkit.level.particle.Particle;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class ParticleUtil{
	
	private ParticleUtil(){
		
	}
	
	public static HashMap<Integer, String> idToString = new HashMap<>();
	public static HashMap<String, Integer> stringToId = new HashMap<>();
	
	@SuppressWarnings({ "deprecation", "serial" })
	public static void init(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "particleSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("물방울", Particle.TYPE_BUBBLE);
			put("bubble", Particle.TYPE_BUBBLE);
			
			put("반짝임", Particle.TYPE_CRITICAL);
			put("크리티컬", Particle.TYPE_CRITICAL);
			put("crit", Particle.TYPE_CRITICAL);
			put("critical", Particle.TYPE_CRITICAL);
			
			put("연기", Particle.TYPE_SMOKE);
			put("smoke", Particle.TYPE_SMOKE);
			
			put("불꽃", Particle.TYPE_FLAME);
			put("flame", Particle.TYPE_FLAME);
			
			put("용암", Particle.TYPE_LAVA);
			put("lava", Particle.TYPE_LAVA);
			
			put("레드스톤", Particle.TYPE_REDSTONE);
			put("redstone", Particle.TYPE_REDSTONE);
			
			put("붉은먼지", Particle.TYPE_RISING_RED_DUST);
			put("reddust", Particle.TYPE_RISING_RED_DUST);
			put("risingreddust", Particle.TYPE_RISING_RED_DUST);
			
			put("하트", Particle.TYPE_HEART);
			put("heart", Particle.TYPE_HEART);
			
			put("보라먼지", Particle.TYPE_PORTAL);
			put("포탈", Particle.TYPE_PORTAL);
			put("portal", Particle.TYPE_PORTAL);
		}});
		
		config.getAll().forEach((name, v) -> {
			int id = (int) v;
			if(! idToString.containsKey(id)){
				idToString.put(id, name);
			}
			stringToId.put(name, id);
		});
	}
	
	public static int fromString(String input){
		input = input.trim().toLowerCase().replace("_", "").replace("-", "");
		if(stringToId.containsKey(input)){
			return stringToId.get(input);
		}
		return 0;
	}
	
	public static String toString(int id){
		if(stringToId.containsKey(id)){
			return idToString.get(id);
		}
		return "";
	}
	
	public static String[] getAvailable(){
		return idToString.values().stream().toArray(String[]::new);
	}
	
}