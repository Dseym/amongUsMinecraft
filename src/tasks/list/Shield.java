package tasks.list;

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

public class Shield extends Task {

	private List<Location> shields;
	private int progress = 0;
	
	public Shield(Location loc, Location locTo, List<Location> shields) {
		
		super(loc, locTo);
		
		this.shields = shields;
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || block.getType() != Material.WOOL)
					return;
			
				progress++;
				block.setType(Material.QUARTZ_BLOCK);
				
				if(progress > shields.size()-1)
					complete(true);
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		for(Location block: shields)
			block.getBlock().setTypeIdAndData(35, (byte)14, false);
		
		progress = 0;
		
	}

	@Override
	public void stop() {}
	
}
