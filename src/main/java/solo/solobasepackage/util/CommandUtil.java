package solo.solobasepackage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class CommandUtil{
	
	private CommandUtil(){
		
	}
	
	@SuppressWarnings({ "deprecation", "serial", "unchecked" })
	public static void init(){
		Config setting = new Config(new File(Main.getInstance().getDataFolder(), "commandSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("unregister", new ArrayList<String>(){{
				//add("version");
				//add("plugins");
				add("seed");
				//add("help");
				//add("stop");
				//add("tell");
				add("defaultgamemode");
				//add("ban");
				//add("ban-ip");
				//add("pardon");
				//add("pardon-ip");
				//add("say");
				add("me");
				//add("list");
				add("difficulty");
				//add("kick");
				//add("op");
				//add("deop");
				//add("whitelist");
				//add("save-on");
				//add("save-off");
				//add("save-all");
				//add("give");
				//add("effect");
				//add("enchant");
				//add("particle");
				//add("gamemode");
				add("kill");
				add("spawnpoint");
				//add("setworldspawn");
				//add("tp");
				//add("time");
				//add("timings");
				add("reload");
				//add("weather");
				add("xp");
			}});
		}});

		unregisterCommand("reload"); // reload is not safe due to my plugins
		for(String name : ((ArrayList<String>) setting.get("unregister"))){
			unregisterCommand(name);
		}
	}
	
	public static void registerCommand(Command command){
		Server.getInstance().getCommandMap().register(command.getName(), command);
	}
	
	public static void unregisterCommand(String name){
		unregisterCommand(Server.getInstance().getCommandMap().getCommand(name));
	}

	public static void unregisterCommand(Command command){
		if(command == null){
			return;
		}
		unregisterCommand(new Command[]{command});
	}
	
	public static void unregisterCommand(Collection<Command> commands){
		unregisterCommand(commands.stream().toArray(Command[]::new));
	}
	
	public static void unregisterCommand(Command[] commands){
		if(commands.length == 0){
			return;
		}
		for(Command command : commands){
			Set<String> toRemove = new HashSet<String>();
			SimpleCommandMap commandMap = Server.getInstance().getCommandMap();
			for(Map.Entry<String, Command> entry : commandMap.getCommands().entrySet()){
				if(entry.getValue() == command){
					toRemove.add(entry.getKey());
				}
			}
			for(String k : toRemove){
				commandMap.getCommands().remove(k);
			}
			
			command.unregister(Server.getInstance().getCommandMap());
		}
		Server.getInstance().getOnlinePlayers().values().forEach(p -> p.sendCommandData());
	}
}