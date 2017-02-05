package solo.solobasepackage.util;

import java.util.HashMap;
import java.util.HashSet;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.MoveEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;

@Deprecated
public abstract class VisibleObject{
	
	public static HashMap<String, HashMap<String, VisibleObject>> visibleObjects = new HashMap<>();
	
	public static VisibleObject get(Position pos){
		try{
			return visibleObjects.get(pos.level.getFolderName()).get(Integer.toString(pos.getFloorX()) + ":" + Integer.toString(pos.getFloorY()) + ":" + Integer.toString(pos.getFloorZ()));
		}catch(Exception e){
			return null;
		}
	}
	
	public static VisibleObject remove(Position pos){
		try{
			return visibleObjects.get(pos.level.getFolderName()).remove(Integer.toString(pos.getFloorX()) + ":" + Integer.toString(pos.getFloorY()) + ":" + Integer.toString(pos.getFloorZ()));
		}catch(Exception e){
			return null;
		}
	}
	
	public String levelName;
	public int x;
	public int y;
	public int z;
	
	public long eid;
	protected AddEntityPacket addEntityPacket;
	protected MoveEntityPacket moveEntityPacket;
	protected RemoveEntityPacket removeEntityPacket;
	
	protected HashSet<String> viewers = new HashSet<String>();
	
	public VisibleObject(Position pos){
		this(pos, pos.getLevel().getFolderName());
	}
	
	public VisibleObject(Vector3 vec, String levelName){
		this(vec.getFloorX(), vec.getFloorY(), vec.getFloorZ(), levelName);
	}
	
	public VisibleObject(int x, int y, int z, String levelName){
		this.x = x;
		this.y = y;
		this.z = z;
		this.levelName = levelName;
		
		this.init();
	}

	protected void init(){
		this.eid = Entity.entityCount++;
		
		this.addEntityPacket = new AddEntityPacket();
	}
	
	public String getHash(){
		return this.levelName + ":" + Integer.toString(this.x) + ":" + Integer.toString(this.y) + ":" + Integer.toString(this.z);
	}
	
	public int getNetworkId(){
		return 15; // villager
	}
	
	public long getEid(){
		return this.eid;
	}
	
	public boolean isViewing(Player player){
		return this.viewers.contains(player.getName().toLowerCase());
	}
	
	public void spawnTo(Player player){
		this.spawnTo(player, player.getLevel());
	}
	 
	public void spawnTo(Player player, Level level){
		if(level == null || ! this.levelName.equals(level.getFolderName())){
			return;
		}
		player.dataPacket(this.addEntityPacket);
		player.dataPacket(this.moveEntityPacket);
		this.viewers.add(player.getName().toLowerCase());
		//this.viewers.put(player.getName().toLowerCase(), player);
	}
	
	public void spawnToAll(){
		Server.getInstance().getOnlinePlayers().values().forEach((p) -> this.spawnTo(p));
	}
	
	public void despawnFrom(Player player){
		if(this.viewers.contains(player.getName().toLowerCase())){
			player.dataPacket(this.removeEntityPacket);
			this.viewers.remove(player.getName().toLowerCase());
		}
	}
	
	public void despawnFromAll(){
		Server.getInstance().getOnlinePlayers().values().forEach((p) -> this.despawnFrom(p));
	}
}
