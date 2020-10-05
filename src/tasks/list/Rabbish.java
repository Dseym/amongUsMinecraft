package tasks.list;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import amongUs.Main;
import tasks.Longs;

public class Rabbish extends Longs {
	
	private List<Block> rabbish;
	private boolean process = false;
	private BukkitTask timerTask;
	private List<Block> ground;

	public Rabbish(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo, Location startRabbish, Location endRabbish, Location startGround, Location endGround, boolean visual) {
		
		super(loc, locTo, nextStepsLocation, nextStepsLocTo);
		
		rabbish = blocksFromTwoPoints(startRabbish, endRabbish);
		
		ground = blocksFromTwoPoints(startGround, endGround);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || block.getType() != Material.LEVER || process)
					return;
			
				startTimeout();
				
				process = true;
				
				for(Block block2: ground)
					block2.setType(Material.AIR);
				
				timerTask = Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {nextStep();}}, 2*25);
				
				if(visual)
					lastLocPlayer.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, lastLocPlayer, 1);
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void start() {
		
		process = false;
		
		for(Block block: rabbish)
			if(Math.floor(Math.random() * 2) == 1)
				block.setType(Material.SAND);
			else
				block.setType(Material.GRAVEL);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		
		if(timerTask != null && !timerTask.isCancelled())
			timerTask.cancel();
		
		for(Block block: ground)
			block.setTypeIdAndData(251, (byte)13, false);
		
	}
	
}
