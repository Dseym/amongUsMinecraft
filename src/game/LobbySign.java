package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Config;
import amongUs.Main;

public class LobbySign {
	
	public static List<LobbySign> signs = new ArrayList<LobbySign>();
	
	public static LobbySign getLobbySign(Lobby lobby) {
		
		for(LobbySign sign: signs)
			if(sign.getLobby().equals(lobby))
				return sign;
		
		return null;
		
	}
	
	public static LobbySign getLobbySign(Sign sign) {
		
		for(LobbySign signL: signs)
			if(signL.getSign().equals(sign))
				return signL;
		
		return null;
		
	}
	
	public static void save() {
		
		File fileSigns = new File(Main.plugin.getDataFolder() + File.separator + "signs.yml");
		try {
			
			if (!fileSigns.exists()) {
				
				fileSigns.createNewFile();
				
			} else {
				
				fileSigns.delete();
				fileSigns.createNewFile();
				
			}
				
		} catch (Exception e) {e.printStackTrace();}
		FileConfiguration configSigns = YamlConfiguration.loadConfiguration(fileSigns);
		
		for(LobbySign sign: signs) {
			
			if(!(sign.getSign().getBlock().getType() == Material.SIGN || sign.getSign().getBlock().getType() == Material.SIGN_POST || sign.getSign().getBlock().getType() == Material.WALL_SIGN))
				return;
			
			Location loc = sign.getSign().getBlock().getLocation();
			
			String strLoc = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
			
			configSigns.set(strLoc, sign.getLobby().getName());
			
		}
		
		try {
			configSigns.save(fileSigns);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void load() {
		
		FileConfiguration config = Config.loadConfig("signs");
		for(String str: config.getKeys(false)) {
			
			String[] strLoc = str.split(",");
			Location loc = new Location(Bukkit.getWorld(strLoc[0]), Integer.parseInt(strLoc[1]), Integer.parseInt(strLoc[2]), Integer.parseInt(strLoc[3]));
			Lobby lobby = Lobby.getLobby(config.getString(str));
			
			if(lobby == null || !(loc.getBlock().getType() == Material.SIGN || loc.getBlock().getType() == Material.SIGN_POST || loc.getBlock().getType() == Material.WALL_SIGN))
				return;
			
			Sign sign = (Sign)loc.getBlock().getState();
			
			signs.add(new LobbySign(lobby, sign));
			
		}
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			public void clickOnSign(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();

				if(block == null || !(block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) || e.getHand() != EquipmentSlot.HAND)
					return;
				
				LobbySign lobbyS = getLobbySign((Sign)block.getState());
				if(lobbyS == null) return;
				
				lobbyS.getLobby().join(e.getPlayer());
				
			}
			
			@EventHandler
			void playerPlaceSign(SignChangeEvent e) {
				
				String[] lines = e.getLines();
				
				if(lines[0].contains("[AmongUs]")) {
					
					Lobby lobby = Lobby.getLobby(lines[1]);
					
					if(lobby == null)
						return;
					
					Sign sign = (Sign)e.getBlock().getState();

					e.setLine(0, "[§3AmongUs§r]");
					e.setLine(1, "Lobby: " + lobby.getName());
					e.setLine(2, "Waiting...");
					
					LobbySign.signs.add(new LobbySign(lobby, sign));
					
					sign.update();
					
				}
				
			}
			
		}, Main.plugin);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(LobbySign signL: signs) {
					
					Sign sign = signL.getSign();
					Lobby lobby = signL.getLobby();
					
					String status = "";
					if(lobby.getGame() == null)
						status = "Not game";
					else if(lobby.getGame().isStart())
						status = "Game started";
					else
						status = "Players: " + lobby.getPlayers().size() + "/" + lobby.getGame().getMap().getSpawns().size();
					
					sign.setLine(0, "[§3AmongUs§r]");
					sign.setLine(1, "Lobby: " + lobby.getName());
					sign.setLine(2, status);
					sign.setLine(3, status.equalsIgnoreCase("Game started") ? "" : "§4§oClick to join");
					
					sign.update();
					
				}
				
			}
			
		}, 20, 20);
		
	}
	

	private Lobby lobby;
	private Sign sign;
	
	public LobbySign(Lobby lobby, Sign sign) {
		
		this.lobby = lobby;
		this.sign = sign;
		
	}
	
	public Lobby getLobby() {
		
		return lobby;
		
	}
	
	public Sign getSign() {
		
		return sign;
		
	}
	
}
