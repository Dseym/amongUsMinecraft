package amongUs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import game.Lobby;
import game.LobbySign;

public class Config {
	
	private static File folder;

	public static void init() {
		
		folder = Main.plugin.getDataFolder();
		
		loadConfigs();
		loadLobby();
		
	}
	
	
	private static void loadConfigs() {
		
		File gameConf = new File(folder + File.separator + "gameConfig");
		gameConf.mkdirs();
		
		try {
			
			File config = new File(folder + File.separator + "config.yml");
			File signs = new File(folder + File.separator + "signs.yml");
			File messages = new File(folder + File.separator + "messages.yml");
			File exampleConfig = new File(gameConf + File.separator + "example.yml");
			
			if(!config.exists())
				config.createNewFile();
			if(!signs.exists())
				signs.createNewFile();
			if(!messages.exists())
				messages.createNewFile();
			
			if(gameConf.list().length == 0 && !exampleConfig.exists())
				exampleConfig.createNewFile();
				
			
		} catch (Exception e) {}
		
	}
	
	private static void loadLobby() {
		
		try {
			
			File file = new File(folder + File.separator + "config.yml");
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			for(String str: config.getKeys(false)) {
				
				String[] strLoc = config.getString(str + ".location").split(",");
				Lobby lobby = new Lobby(new Location(Bukkit.getWorld(strLoc[0]), Integer.parseInt(strLoc[1]), Integer.parseInt(strLoc[2]), Integer.parseInt(strLoc[3])), str);
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
		
		File file = new File(folder + File.separator + "gameConfig" + File.separator + name + ".yml");
		if(!file.exists()) return null;
		
		return YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static FileConfiguration getDefaultGameConfig() {
		
		FileConfiguration config = new YamlConfiguration();
		
		config.set("map", "The Skeld");
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
		
		File file = new File(folder + File.separator + "gameConfig" + File.separator + name + ".yml");
		if(!file.exists()) try {file.createNewFile();} catch (IOException e) {e.printStackTrace();}
		
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		config.set(key, value);
		
		try {config.save(file);} catch (IOException e) {e.printStackTrace();}
		
	}
	
}
