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

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import tasks.Sabotage;

public class Main extends JavaPlugin {

	public static String tagPlugin;
	public static Main plugin;
	public static ProtocolManager protocollib;
	
	public void onEnable() {
		
		tagPlugin = ChatColor.RESET + "[" + ChatColor.BLUE + getName() + ChatColor.RESET + "] ";
		plugin = this;
		
		Messages.init();
		
		getCommand("among").setExecutor((CommandExecutor)new Commands());
		
		protocollib = ProtocolLibrary.getProtocolManager();
		
		File gameConf = new File(getDataFolder() + File.separator + "gameConfig");
		gameConf.mkdirs();
		
		try {
			
			File config = new File(getDataFolder() + File.separator + "config.yml");
			
			if(!config.exists()) {
				
				config.createNewFile();
				new File(gameConf + File.separator + "example.yml").createNewFile();
				new File(getDataFolder() + File.separator + "messages.yml").createNewFile();
				
			}
			
		} catch (Exception e) {}
		
		try {
			
			File file = new File(getDataFolder() + File.separator + "config.yml");
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			for(String str: config.getKeys(false)) {
				
				String[] strLoc = config.getString(str + ".location").split(",");
				Lobby.lobby.add(new Lobby(new Location(Bukkit.getWorld(strLoc[0]), Integer.parseInt(strLoc[1]), Integer.parseInt(strLoc[2]), Integer.parseInt(strLoc[3])), str));
			
			}
			
		} catch (Exception e) {}
		
		getLogger().info("Started!");
		
		getLogger().info("ProtocolLib connecting...");
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				
				protocollib.addPacketListener(new PacketAdapter(Main.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_HEAD_ROTATION, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_DESTROY, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.ENTITY_STATUS) {
					
					@Override
				    public void onPacketSending(PacketEvent event) {
						
						List<Integer> entityId = new ArrayList<Integer>();
						Lobby lobby = Lobby.getLobby(event.getPlayer());
						if(lobby == null || lobby.getGame() == null)
							return;
						for(PlayerGame player: lobby.getGame().getPlayers())
							if(player.getAction() != null)
								entityId.add(player.getPlayer().getEntityId());
						
						if(PacketType.Play.Server.ENTITY_DESTROY == event.getPacketType()) {
							
							int[] lastIds = (int[])event.getPacket().getIntegerArrays().read(0);
							
							int[] _ids = new int[((int[])event.getPacket().getIntegerArrays().read(0)).length];
							
							for(int i = 0; i < lastIds.length; i++)
								if(!entityId.contains(lastIds[i]))
									_ids[i] = lastIds[i];
							
							event.getPacket().getIntegerArrays().write(0, _ids);
							
							return;
							
						}
						
						if(entityId.contains(event.getPacket().getIntegers().read(0)))
							event.setCancelled(true);
						
				    }
					
				});
				
				protocollib.addPacketListener(new PacketAdapter(Main.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EQUIPMENT) {
					
					@Override
				    public void onPacketSending(PacketEvent event) {
						
						Lobby lobby = Lobby.getLobby(event.getPlayer());
						if(lobby == null || lobby.getGame() == null)
							return;
						
						event.setCancelled(true);
						
				    }
					
				});
				
				getLogger().info("ProtocolLib successfully connected!");
				
			}
			
		};
		
		Bukkit.getScheduler().runTaskLater(this, runnable, 60);
		
		Sabotage.createMenu();
		
	}
	
	public void onDisable() {
		
		try {
			
			for(Lobby lobby: Lobby.lobby)
				lobby.getGame().end();
			
		} catch (Exception e) {}
		
		try {
			
			for(Lobby lobby: Lobby.lobby) {
				
				List<Player> players = new ArrayList<Player>();
				players.addAll(lobby.getPlayers());
				for(Player player: players)
					lobby.leave(player, true);
			
			}
			
		} catch (Exception e) {}
		
	}
	
}