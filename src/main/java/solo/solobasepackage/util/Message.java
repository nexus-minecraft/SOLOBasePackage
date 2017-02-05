package solo.solobasepackage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class Message {
	
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_POPUP = 1;
	public static final int TYPE_TIP = 2;
	
	public static String NORMAL_PREFIX;
	public static String NORMAL_COLOR;
	public static String NORMAL_POPUP_COLOR;
	
	public static String ALERT_PREFIX;
	public static String ALERT_COLOR;
	public static String ALERT_POPUP_COLOR;
	
	public static String USAGE_PREFIX;
	public static String USAGE_COLOR;
	public static String USAGE_POPUP_PREFIX;
	public static String USAGE_POPUP_COLOR;
	
	public static String INFORMATION_TITLE_FORMAT;
	public static String PAGE_TITLE_FORMAT;
	
	private Message(){
		
	}
	
	@SuppressWarnings({ "deprecation", "serial" })
	public static void init(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "messageFormat.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("normalPrefix", "§b§l[ 알림 ]");
			put("normalColor", "§r§7");
			put("normalPopupColor", "§b");
			
			put("alertPrefix", "§c§l[ 알림 ]");
			put("alertColor", "§r§7");
			put("alertPopupColor", "§c");
			
			put("usagePrefix", "§d§l[ 사용법 ]");
			put("usageColor", "§r§7");
			put("usagePopupPrefix", "사용법 : ");
			put("usagePopupColor", "§d");
			
			put("informationTitleFormat", "§l======< §b{TITLE} §r§l>======");
			put("pageTitleFormat", "§l======< {TITLE} §r§l(전체 {MAXPAGE}페이지 중 {PAGE}페이지) >======");
		}});
		
		NORMAL_PREFIX = config.getString("normalPrefix");
		NORMAL_COLOR = config.getString("normalColor");
		NORMAL_POPUP_COLOR = config.getString("normalPopupColor");
		
		ALERT_PREFIX = config.getString("alertPrefix");
		ALERT_COLOR = config.getString("alertColor");
		ALERT_POPUP_COLOR = config.getString("alertPopupColor");
		
		USAGE_PREFIX = config.getString("usagePrefix");
		USAGE_COLOR = config.getString("usageColor");
		USAGE_POPUP_PREFIX = config.getString("usagePopupPrefix");
		USAGE_POPUP_COLOR = config.getString("usagePopupColor");
		
		INFORMATION_TITLE_FORMAT = config.getString("informationTitleFormat");
		PAGE_TITLE_FORMAT = config.getString("pageTitleFormat");
	}
	
	public static void raw(CommandSender sender, String message){
		raw(sender, message, TYPE_TEXT);
	}
	
	public static void raw(CommandSender sender, String message, int type){
		if(type == TYPE_TEXT){
			sender.sendMessage(message);
		}else{
			if(! (sender instanceof Player)){
				sender.sendMessage("§7[팝업] " + message);
			}else{
				Player player = (Player) sender;
				if(type == TYPE_POPUP){
					player.sendPopup(message);
				}else if(type == TYPE_TIP){
					player.sendTip(message);
				}
			}
		}
	}
	
	public static void normal(CommandSender sender, String message){
		normal(sender, message, TYPE_TEXT);
	}
	
	public static void normal(CommandSender sender, String message, int type){
		if(type == TYPE_POPUP || type == TYPE_TIP){
			message = NORMAL_POPUP_COLOR + message;
		}else{
			message = (NORMAL_PREFIX.isEmpty()) ? NORMAL_COLOR + message : NORMAL_COLOR + NORMAL_PREFIX + " " + NORMAL_COLOR + message;
		}
		raw(sender, message, type);
	}
		
	public static void alert(CommandSender sender, String message){
		alert(sender, message, TYPE_TEXT);
	}
	
	public static void alert(CommandSender sender, String message, int type){
		if(type == TYPE_POPUP || type == TYPE_TIP){
			message = ALERT_POPUP_COLOR + message;
		}else{
			message = (ALERT_PREFIX.isEmpty()) ? ALERT_COLOR + message : ALERT_COLOR + ALERT_PREFIX + " " + ALERT_COLOR + message;
		}
		raw(sender, message, type);
	}
	
	public static void usage(CommandSender sender, String usage){
		usage(sender, usage, TYPE_TEXT);
	}
	
	public static void usage(CommandSender sender, String usage, int type){
		if(type == TYPE_POPUP || type == TYPE_TIP){
			usage = (USAGE_POPUP_PREFIX.isEmpty()) ? USAGE_POPUP_COLOR + usage : USAGE_POPUP_COLOR + USAGE_POPUP_PREFIX + " " + USAGE_POPUP_COLOR + usage;
		}else{
			usage  = (USAGE_PREFIX.isEmpty()) ? USAGE_COLOR + usage : USAGE_COLOR + USAGE_PREFIX + " " + USAGE_COLOR + usage;
		}
		raw(sender, usage, type);
	}
	
	public static void info(CommandSender sender, String title, ArrayList<String> information){
		info(sender, title, information.toArray(new String[0]));
	}
	
	public static void info(CommandSender sender, String title, String[] information){
		raw(sender, INFORMATION_TITLE_FORMAT.replace("{TITLE}", title) + "§r");
		for(String inf : information){
			raw(sender, inf + "§r");
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= title.length(); i++){
			sb.append("=");
		}
	}

	public static void page(CommandSender sender, String title, ArrayList<String> lines){
		page(sender, title, lines, 1);
	}
	
	public static void page(CommandSender sender, String title, ArrayList<String> lines, String page){
		page(sender, title, lines.stream().toArray(String[]::new), page);
	}
	
	public static void page(CommandSender sender, String title, ArrayList<String> lines, int page){
		page(sender, title, lines.stream().toArray(String[]::new), page);
	}

	public static void page(CommandSender sender, String title, String[] lines){
		page(sender, title, lines, 1);
	}
	
	public static void page(CommandSender sender, String title, String[] lines, String page){
		int p = 1;
		try{
			p = Integer.parseInt(page);
		}catch(Exception e){
			
		}
		page(sender, title, lines, p);
	}

	public static void page(CommandSender sender, String title, String[] lines, int page){
		int linesPerPage = 5;
		int maxPage = 0;
		int currentPage = 0;
		
		if(lines.length > 0){
			maxPage = lines.length / linesPerPage;
			if(lines.length % linesPerPage != 0){
				maxPage++;
			}
			if(page < 1){
				currentPage = 1;
			}else if(page > maxPage){
				currentPage = maxPage;
			}else{
				currentPage = page;
			}
		}

		raw(sender, PAGE_TITLE_FORMAT.replace("{TITLE}", title).replace("{MAXPAGE}", Integer.toString(maxPage)).replace("{PAGE}", Integer.toString(currentPage)) + "§r");
		
		int startIndex = ((currentPage - 1) * linesPerPage);
		int endIndex = currentPage * linesPerPage - 1;
		for(int i = startIndex; i <= endIndex; i++){
			try{
				raw(sender, lines[i] + "§r");
			}catch(Exception e){
				break;
			}
		}
	}
}
