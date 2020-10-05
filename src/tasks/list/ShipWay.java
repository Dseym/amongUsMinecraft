package tasks.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import amongUs.Main;
import tasks.Task;

public class ShipWay extends Task {
	
	private List<Location> way = new ArrayList<Location>();
	private int progress;

	public ShipWay(Location loc, Location locTo, List<Location> way) {
		
		super(loc, locTo);
		
		this.way = way;
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || block.getType() != Material.QUARTZ_BLOCK || !block.equals(way.get(progress).getBlock()))
					return;
			
				progress++;
				block.setType(Material.STAINED_CLAY);
				
				if(progress > way.size()-1)
					complete(true);
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void start() {
		
		for(Location block: way)
			block.getBlock().setType(Material.QUARTZ_BLOCK);
			
		progress = 0;
		
	}

	@Override
	public void stop() {}
	
}
