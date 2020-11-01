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

public class ClearOxygen extends Task {
	
	private List<Block> pole;
	private Block currentBlock;

	public ClearOxygen(Location loc, Location locTo, Location start, Location end) {
		
		super(loc, locTo);
		
		pole = blocksFromTwoPoints(start, end);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.WOOL)
					return;
				
				if(block.getData() == 4)
					currentBlock = block;
				
				if(block.getData() == 0 && currentBlock != null) {
					
					currentBlock.setTypeIdAndData(251, (byte)7, false);
					currentBlock = null;
					startTimeout();
					
				}
				
				for(Block _block: pole)
					if(_block.getTypeId() == 35)
						return;
				
				complete(true);
				
			}
			
		}, Main.plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		for(Block block: pole)
			block.setTypeIdAndData(251, (byte)7, false);
		
		for(int i = 0; i < 7; i++) {
			
			int random = (int)Math.floor(Math.random() * pole.size());
			
			pole.get(random).setTypeIdAndData(35, (byte)4, false);	
			
		}
		
	}

	@Override
	public void stop() {}

}
