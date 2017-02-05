package solo.solobasepackage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class ExperienceUtil{
	
	public static ArrayList<Integer> requireExp;;
	
	private ExperienceUtil(){
		
	}
	
	@SuppressWarnings({ "deprecation", "serial", "unchecked" })
	public static void init(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "experienceSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("requireExp", new ArrayList<Integer>(){{
				add(0); // 0 Level
				add(100); // 1 Level
				add(320);
				add(600);
				add(950);
				add(1300); // 5 Level
				add(2000);
				add(3800);
				add(6300);
				add(9700);
				add(14700); // 10 Level
				add(20500);
				add(27000);
				add(34800);
				add(43800);
				add(55300); // 15 Level
				add(69100);
				add(84600);
				add(102600);
				add(123600);
				add(147600); // 20 Level
				add(175100);
				add(205100);
				add(238100);
				add(275100);
				add(318100);
				add(365600);
				add(415600);
				add(473600);
				add(543600);
				add(626600); // 30 Level
				add(726600);
				add(851600);
				add(1011600);
				add(1204000);
				add(1443000);
				add(1719000);
				add(2032000);
				add(2421000);
				add(2925000);
				add(3674000); // 40 Level
				add(4653000);
				add(5857000);
				add(7501000);
				add(9500000);
				add(12434000);
				add(16204000);
				add(21203000);
				add(27407000);
				add(34901000);
				add(43907000); // 50 Level
				add(53908000);
				add(64103000);
				add(74908000);
				add(86401000);
				add(99802000);
				add(115054000);
				add(132144000);
				add(151201000);
				add(171207000);
				add(191296000); // 60 Level
				add(213532000);
				add(237395000);
				add(262513000);
				add(289512000);
				add(318724000);
				add(348712000);
				add(379921000);
				add(412423000);
				add(446498000);
				add(483454000); // 70 Level
				add(522420000);
				add(563945000);
				add(607153000);
				add(654329000);
				add(704304000); // 75 Level
				add(757390000);
				add(815378000);
				add(875343000);
				add(936378000);
				add(1000000000); // 80 Level
			}});
		}});
		requireExp = (ArrayList<Integer>) config.get("requireExp");
	}
	
	public static int expToLevel(int exp){
		for(int lv = 0; lv < requireExp.size(); lv++){
			if(getTotalRequireExp(lv) < exp){
				continue;
			}
			return lv;
		}
		return getMaxLevel();
	}
	
	public static int getMaxLevel(){
		return requireExp.size() - 1;
	}
	
	public static int getTotalRequireExp(int level){
		if(level <= 0){
			return requireExp.get(0);
		}else if(getMaxLevel() > level){
			return requireExp.get(level);
		}
		return Integer.MAX_VALUE;
	}
	
	public static int getRequireExp(int level){
		if(level <= 0){
			return getTotalRequireExp(0);
		}else if(getMaxLevel() > level){
			return getTotalRequireExp(level) - getTotalRequireExp(level - 1);
		}
		return Integer.MAX_VALUE - getTotalRequireExp(level);
	}
	
	public static double getCurrentPercent(int level, int experience){
		double next = getRequireExp(level + 1);
		double current = experience - getTotalRequireExp(level - 1);
		double percent = current / next;
		if(percent > 100){
			percent = 100;
		}else if(percent < 0){
			percent = 0;
		}
		//System.out.println("level : " + level);
		//System.out.println("current exp : " + experience);
		//System.out.println("percent : " + percent);
		return percent;
	}
}