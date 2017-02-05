package solo.solobasepackage;

import cn.nukkit.plugin.PluginBase;
import solo.solobasepackage.util.Debug;
import solo.solobasepackage.util.EmailUtil;
import solo.solobasepackage.util.ExperienceUtil;
import solo.solobasepackage.util.Message;
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
		
		ExperienceUtil.init();
		Debug.init();
		Message.init();
		EmailUtil.init();
		ParticleUtil.init();
	}
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
}