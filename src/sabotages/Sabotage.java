package sabotages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import amongUs.Main;
import game.PlayerGame;

public abstract class Sabotage {

	protected boolean active = false;
	protected List<Location> location;
	protected Location locTo;
	public boolean show = true;
	private int timeout = 10;
	
	private List<PlayerGame> players = new ArrayList<PlayerGame>();
	private Map<PlayerGame, Location> locPlayers = new HashMap<PlayerGame, Location>();
	private float speed = 0.2f;
	
	public Sabotage(List<Location> location, Location locTo) {
		
		this.location = location;
		this.locTo = locTo;
		List<Entity> ents = new ArrayList<Entity>();
		for(Location loc: location) {
			
			if(!loc.getChunk().isLoaded()) loc.getChunk().load();
			
			Egg ent = (Egg)loc.getWorld().spawnEntity(loc, EntityType.EGG);
			ent.setGravity(false);
			ent.setGlowing(true);
			ents.add(ent);
			
		}
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(Location loc: location)
					if(!loc.getChunk().isLoaded()) loc.getChunk().load();
				
				for(int i = 0; i < ents.size(); i++) {
					
					Entity ent = ents.get(i);
					Location loc = location.get(i);
					
					if(ent.getLocation().distance(loc) > 1)
						ent.teleport(loc);
					
					ent.setGlowing(active);
					
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
