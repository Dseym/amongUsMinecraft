package tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Main;
import amongUs.Messages;

public class UnlockManifold extends Task {
	
	private int currentNum = 0;
	private List<Sign> signs = new ArrayList<Sign>();

	public UnlockManifold(Location loc, Location locTo, Location startSign, Location endSign) {
		
		super(loc, locTo);
		
		List<Block> blocks = blocksFromTwoPoints(startSign, endSign);
		for(Block block: blocks)
			signs.add((Sign)block.getState());
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND)
					return;
				
				if(block.getType() != Material.SIGN && block.getType() != Material.WALL_SIGN)
					return;
			
				startTimeout();
				
				int num = Integer.parseInt(((Sign)block.getState()).getLine(1));
				if(num == currentNum+1) {
					
					currentNum++;
					player.sendMessage("" + num);
					
				} else {
					
					player.sendMessage("§b§o" + Messages.unlockManifoldTask);
					complete(false);
					
				}
				
				if(num+1 > signs.size())
					complete(true);
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void start() {
		
		Collections.shuffle(signs);
		currentNum = 0;
		
		for(int i = 1; i < signs.size()+1; i++) {
			
			signs.get(i-1).setLine(1, Integer.toString(i));
			signs.get(i-1).update();
			
		}
		
	}

	@Override
	public void stop() {}
	
}
