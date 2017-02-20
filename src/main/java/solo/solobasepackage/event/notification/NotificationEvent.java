package solo.solobasepackage.event.notification;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class NotificationEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }
	
	protected final String name;
	protected String message;
	
	public NotificationEvent(String playerName, String message){
		this.name = playerName;
		this.message = message;
	}
	
	public String getPlayerName(){
		return this.name;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
}
