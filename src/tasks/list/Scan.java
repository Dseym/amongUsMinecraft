package tasks.list;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import amongUs.Main;
import tasks.Task;

public class Scan extends Task {
	
	private BukkitTask timerTask;
	private boolean start = false;
	private List<Block> bar;
	private int progress = 0;
	private boolean visual = true;

	public Scan(Location loc, Location locTo, Location startScan, Location startLoc, Location endLoc, boolean visual) {
		
		super(loc, locTo);
		
		this.visual = visual;
		
		bar = blocksFromTwoPoints(startLoc, endLoc);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerMove(PlayerMoveEvent e) {
				
				if(player == null || e.getPlayer() != player.getPlayer() || start)
					return;
				
				if(startScan.distance(e.getPlayer().getLocation()) < 0.4) {
					
					progressTick();
					player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bar.size()*40, 9));
					
				}
				
			}
			
		}, Main.plugin);
		
	}
	
	private void progressTick() {
		
		start = true;
		
		timerTask = Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				progressTick();
				
				bar.get(bar.size()-progress-1).setData((byte)5);
				
				startTimeout();
				
				if(visual)
					lastLocPlayer.getWorld().spawnParticle(Particle.HEART, lastLocPlayer, 4);
				
				progress++;
				
				if(progress > bar.size()-2)
					complete(true);
				
			}
			
		}, 40);
		
	}

	@Override
	public void start() {
		
		start = false;
		for(Block block: bar)
			block.setType(Material.WOOL);
		
		player.getPlayer().teleport(location);
		
	}

	@Override
	public void stop() {
		
		if(timerTask != null && !timerTask.isCancelled())
			timerTask.cancel();
		
		player.getPlayer().removePotionEffect(PotionEffectType.SLOW);
		
	}

}
