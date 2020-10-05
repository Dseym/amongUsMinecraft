package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scoreboard.Scoreboard;

import amongUs.Main;
import amongUs.PlayerGame;

public abstract class Sabotage {

	protected boolean active = false;
	protected List<Location> location;
	protected Location locTo;
	public boolean show = true;
	
	private List<PlayerGame> players = new ArrayList<PlayerGame>();
	private Map<PlayerGame, Location> locPlayers = new HashMap<PlayerGame, Location>();
	private float speed = 0.2f;

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
