package managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import amongUs.Main;
import game.Lobby;
import game.LobbySign;

public class ConfigManager {
	
	private static File folder;
	private static File gameConfig;
	private static File mapConfig;

	public static void init() {
		
		folder = Main.plugin.getDataFolder();
		folder.mkdirs();
		
		gameConfig = new File(folder + File.separator + "gameConfig");
		gameConfig.mkdirs();
		
		mapConfig = new File(folder + File.separator + "mapConfig");
		mapConfig.mkdirs();
		
		loadConfigs();
		
		FileConfiguration config = loadConfig("config");
		if(config.contains("tagPlugin")) Main.tagPlugin = config.getString("tagPlugin");
		if(config.contains("texture")) Main.textures = config.getString("texture");
		
		new BukkitRunnable() {
			
			@Override
			public void run() {loadLobby();}
			
		}.runTaskLater(Main.plugin, 10);
		
	}
	
	
	private static void loadConfigs() {
		
		try {
			
			File config = new File(folder + File.separator + "config.yml");
			File signs = new File(folder + File.separator + "signs.yml");
			File messages = new File(folder + File.separator + "messages.yml");
			File exampleGameConfig = new File(gameConfig + File.separator + "example.yml");
			File exampleMapConfig = new File(mapConfig + File.separator + "example.yml");
			
			if(!config.exists())
				config.createNewFile();
			if(!signs.exists())
				signs.createNewFile();
			if(!messages.exists())
				messages.createNewFile();
			
			if(gameConfig.list().length == 0)
				exampleGameConfig.createNewFile();
			if(mapConfig.list().length == 0)
				exampleMapConfig.createNewFile();
				
		} catch (Exception e) {}
		
	}
	
	private static void loadLobby() {
		
		try {
			
			File file = new File(folder + File.separator + "config.yml");
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			for(String str: config.getKeys(false)) {
				
				if(str.indexOf("tagPlugin") > -1 || str.indexOf("texture") > -1) continue;
				String[] strLoc = config.getString(str + ".location").split(",");
				FileConfiguration defaultConfig;
				if((defaultConfig = loadGameConfig(config.getString(str + ".defaultConfig"))) == null)
					defaultConfig = getDefaultGameConfig();
				
				Lobby lobby = new Lobby(new Location(Bukkit.getWorld(strLoc[0]), Integer.parseInt(strLoc[1]), Integer.parseInt(strLoc[2]), Integer.parseInt(strLoc[3])), str, defaultConfig);
				Lobby.lobby.add(lobby);
				
			}
			
			LobbySign.load();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void saveConfig(String name, String key, Object value) {
		
		File file = new File(folder + File.separator + name + ".yml");
		if(!file.exists()) try {file.createNewFile();} catch (IOException e) {e.printStackTrace();}
		
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		config.set(key, value);
		
		try {config.save(file);} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public static FileConfiguration loadConfig(String name) {
		
		File file = new File(folder + File.separator + name + ".yml");
		if(!file.exists()) return null;
		
		return YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static FileConfiguration loadGameConfig(String name) {
		
		File file = new File(gameConfig + File.separator + name + ".yml");
		if(!file.exists()) return null;
		
		return YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static FileConfiguration loadMapConfig(String name) {
		
		File file = new File(mapConfig + File.separator + name + ".yml");
		if(!file.exists()) return null;
		
		return YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static FileConfiguration getDefaultGameConfig() {
		
		FileConfiguration config = new YamlConfiguration();
		
		config.set("map", "the_skeld");
		config.set("imposters", 2);
		config.set("confirm_eject", true);
		config.set("emergency_metting", 1);
		config.set("timeout_metting", 15);
		config.set("time_voting", 60);
		config.set("speed_player", 1);
		config.set("timeout_kill", 15);
		config.set("distance_kill", 1);
		config.set("tasksNum", 16);
		config.set("visual_task", true);
		config.set("playerOnCount", 4);
		
		List<String> spawns = new ArrayList<String>();
		spawns.add("0.5, 98, 3.5");
		spawns.add("2.5, 98, 2.5");
		spawns.add("3.5, 98, 1.5");
		spawns.add("3.5, 98, -0.5");
		spawns.add("2.5, 98, -1.5");
		spawns.add("0.5, 98, -2.5");
		spawns.add("-1.5, 98, -1.5");
		spawns.add("-2.5, 98, -0.5");
		spawns.add("-2.5, 98, 1.5");
		spawns.add("-1.5, 98, 2.5");
		
		config.set("spawns", spawns);
		
		return config;
		
	}
	
	public static void saveGameConfig(String name, String key, Object value) {
		
		File file = new File(gameConfig + File.separator + name + ".yml");
		if(!file.exists()) try {file.createNewFile();} catch (IOException e) {e.printStackTrace();}
		
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		config.set(key, value);
		
		try {config.save(file);} catch (IOException e) {e.printStackTrace();}
		
	}
	
}
