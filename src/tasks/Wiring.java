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

public class Wiring extends Longs {
	
	private List<Block> blue;
	private List<Block> red;
	private List<Block> yellow;
	private List<Block> green;

	public Wiring(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo, List<Location> wiresStart, List<Location> wiresEnd) {
		
		super(loc, locTo, nextStepsLocation, nextStepsLocTo);
		
		blue = blocksFromTwoPoints(wiresStart.get(0), wiresEnd.get(0));
		red = blocksFromTwoPoints(wiresStart.get(1), wiresEnd.get(1));
		yellow = blocksFromTwoPoints(wiresStart.get(2), wiresEnd.get(2));
		green = blocksFromTwoPoints(wiresStart.get(3), wiresEnd.get(3));
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.WOOL)
					return;
				
				int type = 0;
				
				for(Block _block: blue)
					if(_block.getLocation().distance(block.getLocation()) < 1)
						type = 11;
				for(Block _block: red)
					if(_block.getLocation().distance(block.getLocation()) < 1)
						type = 14;
				for(Block _block: yellow)
					if(_block.getLocation().distance(block.getLocation()) < 1)
						type = 4;
				for(Block _block: green)
					if(_block.getLocation().distance(block.getLocation()) < 1)
						type = 5;
				
				if(type != 0) {
					
					block.setData((byte)type);
					startTimeout();
					
				}
				
				for(Block _block: blue)
					if(_block.getData() == 0)
						return;
				for(Block _block: red)
					if(_block.getData() == 0)
						return;
				for(Block _block: yellow)
					if(_block.getData() == 0)
						return;
				for(Block _block: green)
					if(_block.getData() == 0)
						return;
				
				nextStep();
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		for(Block block: blue)
			block.setTypeId(35);
		for(Block block: red)
			block.setTypeId(35);
		for(Block block: yellow)
			block.setTypeId(35);
		for(Block block: green)
			block.setTypeId(35);
		
	}

	@Override
	public void stop() {}
	
}
