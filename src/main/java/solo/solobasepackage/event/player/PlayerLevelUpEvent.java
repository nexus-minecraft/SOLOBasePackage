package solo.solobasepackage.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerLevelUpEvent extends PlayerEvent implements Cancellable{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerLevelUpEvent(Player player) {
        this.player = player;
    }

    public int getNextLevel(){
    	return this.player.getExperienceLevel() + 1;
    }
    
    public int getExperience(){
    	return this.player.getExperience();
    }
}
