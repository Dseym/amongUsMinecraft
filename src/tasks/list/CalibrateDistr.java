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
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Main;
import tasks.Task;

public class CalibrateDistr extends Task {
	
	private List<Block> pole = new ArrayList<Block>();
	private List<Block> blocks;
	private List<Block> success;

	public CalibrateDistr(Location loc, Location locTo, Location poleStart, Location poleEnd) {
		
		super(loc, locTo);
		
		for(Block block: blocksFromTwoPoints(poleStart, poleEnd))
			if(block.getType() == Material.WOOL)
				pole.add(block);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {@Override public void run() {tick();}}, 20, 20);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || block.getType() != Material.CONCRETE_POWDER)
					return;
			
				boolean yes = false;
				for(Block _block: pole)
					if(!success.contains(_block) && _block.getData() == 15 && _block.getLocation().distance(block.getLocation()) < 1.1) {
						
						success.add(_block);
						blocks.remove(_block);
						
						yes = true;
						break;
						
					}
				
				startTimeout();
				
				if(!yes) {
					
					player.sendMessage("§b§oЗадание: промах");
					complete(false);
					
				}
				
				if(blocks.size() < 1)
					complete(true);
				
			}
			
		}, Main.plugin);
		
	}
	
	@SuppressWarnings("deprecation")
	private void tick() {
		
		if(!inProgress)
			return;
		
		List<Block> list = new ArrayList<Block>();
		list.addAll(blocks);
		
		for(Block block: list) {
			
			int num = pole.indexOf(block)+1;
			
			block.setData((byte)0);
			
			if(num+1 > pole.size())
				num = 0;
			
			Block _block = pole.get(num);
			
			blocks.set(blocks.indexOf(block), pole.get(num));
			
			_block.setData((byte)15);
			
		}
		
		for(Block block: success)
			block.setData((byte)5);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		for(Block block: pole)
			block.setData((byte)0);
		
		blocks = new ArrayList<Block>();
		success = new ArrayList<Block>();
		
		for(int i = 0; i < 4; i++)
			blocks.add(pole.get(i));
		
	}

	@Override
	public void stop() {}

}
