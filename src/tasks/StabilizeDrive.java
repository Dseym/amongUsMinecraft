package tasks;

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

public class StabilizeDrive extends Longs {
	
	private List<Block> blocks;
	private int num = 0;

	public StabilizeDrive(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo, Location start, Location end) {
		
		super(loc, locTo, nextStepsLocation, nextStepsLocTo);
		
		this.blocks = blocksFromTwoPoints(start, end);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.CONCRETE_POWDER)
					return;
			
				blocks.get(blocks.size()-num-1).setData((byte)0);
				
				num++;
				
				blocks.get(blocks.size()-num-1).setData((byte)5);
				
				if(num+2 > blocks.size())
					nextStep();
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		num = 0;
		
		for(Block block: blocks)
			block.setData((byte)0);
		
		blocks.get(blocks.size()-1).setData((byte)5);
		
	}

	@Override
	public void stop() {}
	
}
