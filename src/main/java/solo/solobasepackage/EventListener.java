package solo.solobasepackage;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import solo.solobasepackage.event.player.PlayerFloorMoveEvent;

public class EventListener{

	private Map<String, Position> movePos = new HashMap<>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		
		Position currentPos = new Position(player.getFloorX(), player.getFloorY(), player.getFloorZ());
		
		if(this.movePos.containsKey(name)){
			Position oldPos = this.movePos.get(name);
			if(currentPos.x != oldPos.x || currentPos.y != oldPos.y || currentPos.z != oldPos.z){
				PlayerFloorMoveEvent ev = new PlayerFloorMoveEvent(player, oldPos, currentPos);
				Server.getInstance().getPluginManager().callEvent(ev);
				if(! ev.isCancelled()){
					this.movePos.put(name, currentPos);
				}else{
					event.setTo(new Location(oldPos.x + 0.5, oldPos.y, oldPos.z + 0.5, event.getTo().getYaw(), event.getTo().getPitch(), event.getTo().getLevel()));
				}
			}
		}else{
			this.movePos.put(name, currentPos);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		this.movePos.remove(event.getPlayer().getName().toLowerCase());
	}
	
}