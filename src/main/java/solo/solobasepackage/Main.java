package solo.solobasepackage;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCreationEvent;
import cn.nukkit.plugin.PluginBase;
import solo.solobasepackage.util.Debug;
import solo.solobasepackage.util.EmailUtil;
import solo.solobasepackage.util.ExperienceUtil;
import solo.solobasepackage.util.Message;

public class Main extends PluginBase implements Listener{
	
	public static Main instance;
	
	public static Main getInstance(){
		return instance;
	}
	
	@Override
	public void onLoad(){
		instance = this;
		this.getDataFolder().mkdirs();
		
		ExperienceUtil.init();
		Debug.init();
		Message.init();
		EmailUtil.init();
	}
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerCreation(PlayerCreationEvent event){
		event.setPlayerClass(SPlayer.class);
	}
	
}