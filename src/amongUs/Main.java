package amongUs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import game.Lobby;
import game.LobbySign;
import managers.ConfigManager;
import managers.InvManager;
import managers.ProtocolLibManager;

public class Main extends JavaPlugin {

	public static String tagPlugin;
	public static Main plugin;
	public static String textures = "https://getfile.dokpub.com/yandex/get/https://yadi.sk/d/3ENP1fxAx9cJkA";
	
	public void onEnable() {
		
		tagPlugin = ChatColor.RESET + "[" + ChatColor.BLUE + getName() + ChatColor.RESET + "] ";
		plugin = this;
		
		Messages.init();
		ConfigManager.init();
		InvManager.init();
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {ProtocolLibManager.init();}
			
		};
		
		Bukkit.getScheduler().runTaskLater(this, runnable, 60);
		
		getCommand("among").setExecutor((CommandExecutor)new Commands());
		
		getLogger().info("Started!");
		getLogger().info("ProtocolLib connecting...");
		
	}
	
	public void onDisable() {
		
		try {
			
			for(Lobby lobby: Lobby.lobby)
				lobby.gameStop("Plugin disable");

			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		LobbySign.save();
		
	}
	
}