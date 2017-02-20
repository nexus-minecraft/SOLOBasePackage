package solo.solobasepackage.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;
import solo.solobasepackage.event.notification.NotificationEvent;

public class Notification{
	
	private Notification(){
		
	}
	
	//Notification form
	//
	//[2017년 2월 6일 오후 1:27] steve님이 메일을 보냈습니다.
	
	public static String DATE_FORMAT;
	public static String NOTIFICATION_FORMAT;
	
	public static HashMap<String, ArrayList<String>> notifications = new HashMap<>();

	@SuppressWarnings({ "unchecked", "deprecation", "serial" })
	public static void init(){
		 Config config = new Config(new File(Main.getInstance().getDataFolder(), "notificationSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("dateFormat", "yyyy년 MM월 dd일 hh시 mm분");
			put("notificationFormat", "§a[{DATE}]§r {MESSAGE}");
		}});
		DATE_FORMAT = config.getString("dateFormat");
		NOTIFICATION_FORMAT = config.getString("notificationFormat");
		
		config = new Config(new File(Main.getInstance().getDataFolder(), "notificationData.yml"), Config.YAML);
		config.getAll().forEach((name, notify) -> {
			notifications.put(name, (ArrayList<String>) notify);
		});
	}
	
	public static void save(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "notificationData.yml"), Config.YAML);
		LinkedHashMap<String, Object> data = new LinkedHashMap<>();
		notifications.forEach((k, v) -> data.put(k, v));
		config.setAll(data);
		config.save();
	}
	
	public static void addNotification(CommandSender sender, String message){
		addNotification(sender.getName(), message);
	}
	
	public static void addNotification(String name, String message){
		name = name.toLowerCase();
		
		NotificationEvent ev = new NotificationEvent(name, message);
		Server.getInstance().getPluginManager().callEvent(ev);
		if(ev.isCancelled()){
			return;
		}
		message = NOTIFICATION_FORMAT.replace("{DATE}", new SimpleDateFormat(DATE_FORMAT).format(new Date())).replace("{MESSAGE}", message);
		
		if(! notifications.containsKey(name)){
			notifications.put(name, new ArrayList<String>());
		}
		notifications.get(name).add(message);
		
	}
	
	public static boolean removeNotification(CommandSender sender, int index){
		return removeNotification(sender.getName(), index);
	}
	
	public static boolean removeNotification(String name, int index){
		name = name.toLowerCase();
		if(notifications.containsKey(name) && index >= 0 && index < notifications.get(name).size()){
			notifications.get(name).remove(index);
			return true;
		}
		return false;
	}
	
	public static boolean removeAllNotifications(CommandSender sender){
		return removeAllNotifications(sender.getName());
	}
	
	public static boolean removeAllNotifications(String name){
		name = name.toLowerCase();
		if(notifications.containsKey(name)){
			return notifications.remove(name).size() != 0;
		}
		return false;
	}
	
	public static boolean hasNotification(CommandSender sender){
		return hasNotification(sender.getName());
	}
	
	public static boolean hasNotification(String name){
		name = name.toLowerCase();
		if(notifications.containsKey(name)){
			return notifications.get(name).size() != 0;
		}
		return false;
	}
	
	public static int getNotificationCount(CommandSender sender){
		return getNotificationCount(sender.getName());
	}
	
	public static int getNotificationCount(String name){
		name = name.toLowerCase();
		if(notifications.containsKey(name)){
			return notifications.get(name).size();
		}
		return 0;
		
	}
	
	public static String[] getNotifications(CommandSender sender){
		return getNotifications(sender.getName());
	}
	
	public static String[] getNotifications(String name){
		name = name.toLowerCase();
		if(notifications.containsKey(name)){
			return notifications.get(name).stream().toArray(String[]::new);
		}
		return new String[0];
		
	}
	/*
	public static boolean hasNotification(Player player){
		hasNotification(player.getName());
	}
	
	public static boolean hasNotification(String name){
		name = name.toLowerCase();
	}
	*/
	
}