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

public class Refuel extends Longs {
	
	private List<Block> bar;
	private int progress = 0;

	public Refuel(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo, Location start, Location end) {
		
		super(loc, locTo, nextStepsLocation, nextStepsLocTo);
		
		bar = blocksFromTwoPoints(start, end);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.CONCRETE_POWDER)
					return;
			
				progress++;
				
				bar.get((int)Math.floor(progress/4)).setData((byte)4);
				
				if(progress+2 > bar.size()*4)
					nextStep();
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		progress = 0;
		for(Block block: bar)
			block.setData((byte)0);
		
	}

	@Override
	public void stop() {}

}
