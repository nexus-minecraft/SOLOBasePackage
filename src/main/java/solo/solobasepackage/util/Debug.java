package solo.solobasepackage.util;

import java.io.File;
import java.util.LinkedHashMap;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class Debug {

	private Debug(){
		
	}
	
	public static boolean debug = true;
	
	@SuppressWarnings({ "deprecation", "serial" })
	public static void init(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "debugSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("debug", false);
		}});
		
		debug = config.getBoolean("debug", false);
	}
	
	public static void normal(PluginBase plugin, String message){
		if(debug){
			Server.getInstance().getLogger().info("§b[" + plugin.getName() + " Debug] " + message);
		}
	}
	
	public static void alert(PluginBase plugin, String message){
		Server.getInstance().getLogger().info("§c[" + plugin.getName() + " Alert] " + message);
	}
	
	public static void critical(PluginBase plugin, String message){
		Server.getInstance().getLogger().info("§4[" + plugin.getName() + " Critical] " + message);
	}
}