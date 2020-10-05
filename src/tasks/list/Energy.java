package tasks.list;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Main;
import tasks.Longs;

public class Energy extends Longs {
	
	private List<Block> slider;

	public Energy(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo, Location startSlider, Location endSlider, Location button) {
		
		super(loc, locTo, nextStepsLocation, nextStepsLocTo);
		
		slider = blocksFromTwoPoints(startSlider, endSlider);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.WOOL)
					return;
				
				if(step == 1) {
					
					if(slider.contains(block) && block.getData() == 0)
						block.setData((byte)4);
					
					for(Block _block: slider)
						if(_block.getData() == 0)
							return;
					
					nextStep();
					
					return;
					
				}
				
				if(button.distance(block.getLocation()) < 1)
					nextStep();
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		for(Block block: slider)
			block.setData((byte)0);
		
	}

	@Override
	public void stop() {}
	
}
