package amongUs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Main extends JavaPlugin {

	public static String tagPlugin;
	public static Main plugin;
	public static ProtocolManager protocollib;
	
	public void onEnable() {
		
		getDataFolder().mkdirs();
		
		tagPlugin = ChatColor.RESET + "[" + ChatColor.BLUE + getName() + ChatColor.RESET + "] ";
		plugin = this;
		
		getCommand("among").setExecutor((CommandExecutor)new Commands());
		
		protocollib = ProtocolLibrary.getProtocolManager();
		
		getDataFolder().mkdirs();
		
		try {
			
			File config = new File(getDataFolder() + File.separator + "config.yml");
			
			if(!config.exists())
				config.createNewFile();
			
		} catch (Exception e) {}
		
		try {
			
			File file = new File(getDataFolder() + File.separator + "config.yml");
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			String[] strLoc = config.getString("lobby.location").split(",");
			Lobby.lobby = new Lobby(new Location(Bukkit.getWorld(strLoc[0]), Integer.parseInt(strLoc[1]), Integer.parseInt(strLoc[2]), Integer.parseInt(strLoc[3])));
		
		} catch (Exception e) {}
		
		getLogger().info("Started!");
		
	}
	
	public void onDisable() {
		
		try {
			
			Lobby.lobby.getGame().end();
			
		} catch (Exception e) {}
		
		try {
			
			List<Player> players = new ArrayList<Player>();
			players.addAll(Lobby.lobby.getPlayers());
			for(Player player: players)
				Lobby.lobby.leave(player, true);
			
		} catch (Exception e) {}
		
	}
	
}