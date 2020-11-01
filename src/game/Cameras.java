package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Inv;
import amongUs.Main;
import amongUs.Messages;

public class Cameras {
	
	private Map<PlayerGame, Integer> players = new HashMap<PlayerGame, Integer>();
	private List<Location> cameras;
	private Location location;
	private Map<PlayerGame, Location> locPlayers = new HashMap<PlayerGame, Location>();
	private boolean active = true;

	public Cameras(Location location, List<Location> cameras, Game game) {
		
		this.cameras = cameras;
		this.location = location;
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				PlayerGame player = game.getPlayer(e.getPlayer());
				
				if(player == null || e.getHand() != EquipmentSlot.HAND)
					return;
				
				if(players.containsKey(player)) {
					
					if(e.getItem() != null && e.getItem().getType() == Material.STONE_BUTTON)
						exit(player);
					else
						next(player);
					
				} else if(e.getClickedBlock() != null) {
					
					if(e.getClickedBlock().getLocation().distance(location) < 1) join(player);
					
				}
				
			}
			
			@EventHandler
			void playerMove(PlayerMoveEvent e) {
				
				PlayerGame player = game.getPlayer(e.getPlayer());
				
				if(player == null || !players.containsKey(player))
					return;
				
				e.setCancelled(true);
				
			}
			
		}, Main.plugin);
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public boolean isActive() {
		
		return active;
		
	}
	
	public void setActive(boolean active) {
		
		this.active = active;
		
	}
	
	public List<PlayerGame> getPlayers() {
		
		List<PlayerGame> players = new ArrayList<PlayerGame>();
		players.addAll(this.players.keySet());
		
		return players;
		
	}
	
	public void join(PlayerGame player) {
		
		if(players.containsKey(player) || !active)
			return;
		
		players.put(player, -1);
		locPlayers.put(player, player.getPlayer().getLocation().clone());
		player.getPlayer().setGameMode(GameMode.CREATIVE);
		player.getPlayer().setFlying(true);
		player.setAction(this);
		
		player.getPlayer().getInventory().setItem(8, Inv.genItem(Material.STONE_BUTTON, Messages.exit));
		player.getPlayer().getInventory().setHeldItemSlot(0);
		
		next(player);
		
	}
	
	public Location getPlayerLoc(PlayerGame player) {
		
		return locPlayers.get(player);
		
	}
	
	public void exit(PlayerGame player) {
		
		if(!players.containsKey(player))
			return;
		
		player.getPlayer().teleport(locPlayers.get(player));
		player.getPlayer().setFlying(false);
		player.getPlayer().setGameMode(GameMode.SURVIVAL);
		player.setAction(null);
		player.getPlayer().getInventory().setItem(8, null);
		player.getPlayer().getInventory().setHeldItemSlot(0);
		
		players.remove(player);
		locPlayers.remove(player);
		
	}
	
	public void next(PlayerGame player) {
		
		if(!players.containsKey(player)) return;
		
		if(players.get(player)+2 > cameras.size())
			players.replace(player, -1);
		
		players.replace(player, players.get(player)+1);
		
		player.getPlayer().teleport(cameras.get(players.get(player)));
		
	}
	
}
