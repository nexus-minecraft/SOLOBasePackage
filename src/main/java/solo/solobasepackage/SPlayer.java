package solo.solobasepackage;

import cn.nukkit.Player;
import cn.nukkit.entity.Attribute;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.protocol.UpdateAttributesPacket;
import solo.solobasepackage.event.player.PlayerLevelUpEvent;
import solo.solobasepackage.util.ExperienceUtil;

public class SPlayer extends Player{
	
	public SPlayer(SourceInterface interfaz, Long clientID, String ip, int port){
		super(interfaz, clientID, ip, port);
	}
	
	@Override
	public void sendAttributes() {
		UpdateAttributesPacket pk = new UpdateAttributesPacket();
		pk.entityId = 0;
		System.out.println(ExperienceUtil.getCurrentPercent(this.getExperienceLevel(), this.getExperience()));
		pk.entries = new Attribute[]{
				Attribute.getAttribute(Attribute.MAX_HEALTH).setMaxValue(this.getMaxHealth()).setValue(health > 0 ? (health < getMaxHealth() ? health : getMaxHealth()) : 0),
				Attribute.getAttribute(Attribute.MAX_HUNGER).setValue(this.getFoodData().getLevel()),
				Attribute.getAttribute(Attribute.MOVEMENT_SPEED).setValue(this.getMovementSpeed()),
				Attribute.getAttribute(Attribute.EXPERIENCE_LEVEL).setValue(this.getExperienceLevel()),
				Attribute.getAttribute(Attribute.EXPERIENCE).setValue((float) ExperienceUtil.getCurrentPercent(this.getExperienceLevel(), this.getExperience()))
		};
		this.dataPacket(pk);
	}
	
	@Override
	public void addExperience(int add){
		if(add == 0){
			return;
		}
		int now = this.getExperience();
		int added = now + add;
		int level = this.getExperienceLevel();
		int addedLevel = ExperienceUtil.expToLevel(added);
		if(level < addedLevel){
			PlayerLevelUpEvent ev = new PlayerLevelUpEvent(this);
			this.server.getPluginManager().callEvent(ev);
			if(! ev.isCancelled()){
				level++; //Level Up
			}
		}
		this.setExperience(added, level);
	}
	
	@Override
	public void sendExperience(int exp){
		if(this.spawned){
			this.setAttribute(Attribute.getAttribute(Attribute.EXPERIENCE).setValue((float) ExperienceUtil.getCurrentPercent(this.getExperienceLevel(), this.getExperience())));
		}
	}
}