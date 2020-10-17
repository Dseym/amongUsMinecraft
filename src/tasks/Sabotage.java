package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;

import amongUs.Main;
import amongUs.Messages;
import amongUs.PlayerGame;

public abstract class Sabotage {

	protected boolean active = false;
	protected List<Location> location;
	protected Location locTo;
	public boolean show = true;
	private int timeout = 10;
	
	private List<PlayerGame> players = new ArrayList<PlayerGame>();
	private Map<PlayerGame, Location> locPlayers = new HashMap<PlayerGame, Location>();
	private float speed = 0.2f;
	
	private static Inventory inv;
	
	public static void openMenu(PlayerGame player) {
		
		player.getPlayer().openInventory(inv);
		
	}
	
	private static ItemStack genItem(Material mat, String name, String... lores) {
		
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(lores != null)
			meta.setLore(Arrays.asList(lores));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	public static void createMenu() {
		
		inv = Bukkit.createInventory(null, 54, Messages.mapSabotage);
		
		for(int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, genItem(Material.STAINED_GLASS_PANE, "§7"));

		inv.setItem(1, genItem(Material.IRON_DOOR, "Sabotage", "§eUp Drive", Messages.sabType_doorUpDrive, "ID: doorUpDrive"));
		inv.setItem(37, genItem(Material.IRON_DOOR, "Sabotage", "§eDown Drive", Messages.sabType_doorDownDrive, "ID: doorDownDrive"));
		inv.setItem(20, genItem(Material.IRON_DOOR, "Sabotage", "§eSecurity", Messages.sabType_doorSecurity, "ID: doorSecurity"));
		inv.setItem(12, genItem(Material.IRON_DOOR, "Sabotage", "§eMedbay", Messages.sabType_doorMedbay, "ID: doorMedbay"));
		inv.setItem(29, genItem(Material.IRON_DOOR, "Sabotage", "§eElectrical", Messages.sabType_doorElectrical, "ID: doorElectrical"));
		inv.setItem(5, genItem(Material.IRON_DOOR, "Sabotage", "§eCafeteria", Messages.sabType_doorCafeteria, "ID: doorCafeteria"));
		inv.setItem(41, genItem(Material.IRON_DOOR, "Sabotage", "§eStorage", Messages.sabType_doorStorage, "ID: doorStorage"));
		
		inv.setItem(18, genItem(Material.SEA_LANTERN, "Sabotage", "§eReactor", Messages.sabType_reactor, "ID: reactor"));
		inv.setItem(51, genItem(Material.REDSTONE_ORE, "Sabotage", "§eCommunication", Messages.sabType_communicate, "ID: communicate"));
		inv.setItem(15, genItem(Material.SAPLING, "Sabotage", "§eOxygen", Messages.sabType_oxygen, "ID: oxygen"));
		inv.setItem(30, genItem(Material.REDSTONE_COMPARATOR, "Sabotage", "§eElectrical", Messages.sabType_electrical, "ID: electrical"));
		
	}
	

	public Sabotage(List<Location> location, Location locTo) {
		
		this.location = location;
		this.locTo = locTo;
		
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		List<Entity> ents = new ArrayList<Entity>();
		for(Location loc: location) {
			
			Snowball ent = (Snowball)loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
			ent.setGravity(false);
			ent.setGlowing(true);
			board.getTeam("amongSabotage").addEntry(ent.getUniqueId().toString());
			ents.add(ent);
			
		}
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(int i = 0; i < ents.size(); i++) {
					
					Entity ent = ents.get(i);
					Location loc = location.get(i);
					
					if(ent.getLocation().distance(loc) > 1)
						ent.teleport(loc);
					
					if(active)
						ent.setGlowing(true);
					else
						ent.setGlowing(false);
					
					if(!active)
						timeout--;
					else
						timeout = 10;
					
				}
				
			}
			
		}, 20, 20);
		
	}
	
	public Location getPlayerLoc(PlayerGame player) {
		
		return locPlayers.get(player);
		
	}
	
	public void complete() {
		
		active = false;
		
		List<PlayerGame> _players = new ArrayList<PlayerGame>();
		_players.addAll(players);
		for(PlayerGame player: _players)
			exit(player);
		
	}
	
	public boolean isActive() {
		
		return active;
		
	}
	
	public List<Location> getLocation() {
		
		return location;
		
	}
	
	public Location getLocTo() {
		
		return locTo;
		
	}
	
	public void start() {
		
		if(timeout > 0)
			return;
		
		active = true;
		startAbsr();
		
	}
	
	public abstract void startAbsr();

	public void join(PlayerGame player) {
		
		if(!active)
			return;
		
		if(players.contains(player))
			return;
		
		speed = player.getPlayer().getWalkSpeed();
		player.getPlayer().setWalkSpeed((float)0.2);
		players.add(player);
		player.setAction(this);
		locPlayers.put(player, player.getPlayer().getLocation().clone());
		
		player.getPlayer().teleport(locTo);
		
	}
	
	public void exit(PlayerGame player) {
		
		if(!players.contains(player))
			return;
		
		player.getPlayer().setWalkSpeed(speed);
		
		player.getPlayer().teleport(locPlayers.get(player));
		
		player.setAction(null);
		
		players.remove(player);
		locPlayers.remove(player);
		
	}
	
	public PlayerGame getPlayer(Player player) {
		
		for(PlayerGame _player: players)
			if(_player.getPlayer() == player)
				return _player;
		
		return null;
		
	}

}
