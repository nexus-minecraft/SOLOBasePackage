package solo.solobasepackage;

import cn.nukkit.plugin.PluginBase;
import solo.solobasepackage.util.CommandUtil;
import solo.solobasepackage.util.Debug;
import solo.solobasepackage.util.EmailUtil;
import solo.solobasepackage.util.ExperienceUtil;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.Notification;
import solo.solobasepackage.util.ParticleUtil;

public class Main extends PluginBase{
	
	public static Main instance;
	
	public static Main getInstance(){
		return instance;
	}
	
	@Override
	public void onLoad(){
		instance = this;
		this.getDataFolder().mkdirs();

		CommandUtil.init();
		Debug.init();
		EmailUtil.init();
		ExperienceUtil.init();
		Message.init();
		Notification.init();
		ParticleUtil.init();
	}
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
	
	//@Override
	//public void onDisable(){
	//	Notification.save();
	//}
}