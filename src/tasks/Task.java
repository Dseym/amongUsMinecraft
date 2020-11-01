package tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import amongUs.Main;
import amongUs.Messages;
import game.PlaySound;
import game.PlayerGame;

public abstract class Task {

	private boolean isEnable = true;
	public boolean show = true;
	protected boolean isComplete = false;
	protected boolean inProgress = false;
	protected Location location;
	protected Location locTo;
	
	protected BukkitTask timer;
	protected PlayerGame player;
	protected Location lastLocPlayer;
	protected float speed = 0.2f;
	
	public Task(Location loc, Location locTo) {
		
		location = loc;
		this.locTo = locTo;
		
		if(!loc.getChunk().isLoaded()) loc.getChunk().load();
		
		Snowball ent = (Snowball)loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
		ent.setGravity(false);
		ent.setGlowing(true);
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		board.getTeam("amTas" + loc.getWorld().getName()).addEntry(ent.getUniqueId().toString());
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(!loc.getChunk().isLoaded()) loc.getChunk().load();
				
				if(ent.getLocation().distance(location) > 1)
					ent.teleport(location);
				
				if(isComplete || !show)
					ent.setGlowing(false);
				else
					ent.setGlowing(true);
				
			}
			
		}, 20, 20);
		
	}
	
	public void disable() {
		
		isEnable = false;
		isComplete = true;
		
	}
	
	public void fakeProgress() {
		
		if(isComplete)
			return;
		
		if(inProgress)
			return;
		
		inProgress = true;
		
		Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				inProgress = false;
				
			}
			
		}, ((int)(5+Math.floor(Math.random() * 10)))*20);
		
	}
	
	public Location getPlayerLoc() {
		
		return lastLocPlayer;
		
	}
	
	public boolean isEnable() {
		
		return isEnable;
		
	}
	
	public boolean start(PlayerGame player) {
		
		if(isComplete)
			return false;
		
		if(inProgress)
			return false;
		
		this.player = player;
		
		lastLocPlayer = player.getPlayer().getLocation().clone();
		
		inProgress = true;
		
		player.setAction(this);
		
		start();
		
		player.getPlayer().teleport(locTo);
		speed = player.getPlayer().getWalkSpeed();
		player.getPlayer().setWalkSpeed((float)0.2);
		
		startTimeout();
		
		return true;
		
	}
	
	public boolean isComplete() {
		
		return isComplete;
		
	}
	
	public void startTimeout() {
		
		if(timer != null && !timer.isCancelled())
			timer.cancel();
		
		timer = Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {player.sendMessage(Messages.taskTimeout); complete(false);}}, 15*20);
		
	}
	
	public abstract void start();
	
	public abstract void stop();
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public Location getLocTo() {
		
		return locTo;
		
	}
	
	protected List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
		
        List<Block> blocks = new ArrayList<Block>();
 
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
 
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
 
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
 
        for(int x = bottomBlockX; x <= topBlockX; x++)
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                	
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                   
                    blocks.add(block);
                    
                }

        return blocks;
        
    }
	
	public void complete(boolean success) {
		
		if(!inProgress)
			return;
		
		stop();
		
		player.getPlayer().setWalkSpeed(speed);
		player.setAction(null);
		timer.cancel();
		timer = null;
		if(success)
			player.sendTitle(Messages.taskComplete, "");
		
		player.getPlayer().teleport(lastLocPlayer);
		lastLocPlayer = null;
		
		if(success) {
			
			player.sendTitle(Messages.taskComplete, "");
			
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				
				@Override public void run() {isComplete = success; inProgress = false;}
				
			}, ((int)Math.floor(Math.random() * 4)+5)*20);
			
			PlaySound.TASK_COMPLETE.play(player.getPlayer());
			
		} else {
			
			isComplete = success;
			inProgress = false;
			
		}
		
		player = null;
		
	}
	
}
