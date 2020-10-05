package tasks.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Main;
import tasks.Task;

public class StabilizeWay extends Task {
	
	private boolean active = false;

	public StabilizeWay(Location loc, Location locTo) {
		
		super(loc, locTo);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.WOOL)
					return;
				
				if(block.getData() == 11)
					active = true;
				
				if(block.getData() == 0 && active)
					complete(true);
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void start() {
		
		active = false;
		
	}

	@Override
	public void stop() {}
	
}
