package tasks.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Cameras;
import amongUs.Game;
import amongUs.Main;
import amongUs.PlayerGame;
import tasks.Sabotage;
import tasks.Task;

public class SabotageCommunicate extends Sabotage {
	
	private List<Block> bar;
	private List<Block> barTask;
	private int numTask = 0;
	private int currentNum = 0;

	public SabotageCommunicate(List<Location> loc, Location locTo, Game game, Location startBar, Location endBar, Location startBarTask, Location endBarTask) {
		
		super(loc, locTo);
		
		BossBar bossBar = game.getBar();
		
		bar = blocksFromTwoPoints(startBar, endBar);
		barTask = blocksFromTwoPoints(startBarTask, endBarTask);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(active) {
					
					bossBar.setVisible(false);
					for(PlayerGame player: game.getPlayers())
						player.sendTitle("", "§cСаботаж коммун-го модуля");
					
					Cameras camera = game.getMap().getCameras();
					camera.setActive(false);
					
					for(PlayerGame player: camera.getPlayers())
						camera.exit(player);
					
					for(Task task: game.getTasks())
						task.show = false;
					
				} else {
					
					for(Task task: game.getTasks())
						task.show = true;
					
					bossBar.setVisible(true);
					
					Cameras camera = game.getMap().getCameras();
					camera.setActive(true);
					
				}
				
			}
			
		}, 20, 20);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				PlayerGame player = getPlayer(e.getPlayer());
				
				if(player == null || e.getPlayer() != player.getPlayer() || e.getHand() != EquipmentSlot.HAND || block == null || block.getType() != Material.CONCRETE_POWDER)
					return;
				
				if(block.getData() == 5 && currentNum+1 < bar.size())
					currentNum++;
				else if(block.getData() == 14 && currentNum > 0)
					currentNum--;
						
				for(Block _block: bar)
					_block.setData((byte)0);
				
				bar.get(currentNum).setData((byte)3);
				
				if(currentNum == numTask)
					complete();
				
			}
			
		}, Main.plugin);
		
	}
	
	private List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
		
        List<Block> blocks = new ArrayList<Block>();
 
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
 
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
 
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
 
        for(int x = bottomBlockX; x <= topBlockX; x++)
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                	
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                   
                    blocks.add(block);
                    
                }

        return blocks;
        
    }

	@SuppressWarnings("deprecation")
	@Override
	public void startAbsr() {
		
		for(Block block: bar)
			block.setData((byte)0);
		
		for(Block block: barTask)
			block.setData((byte)0);
		
		int random = (int)Math.floor(Math.random() * barTask.size());
		barTask.get(random).setData((byte)3);
		numTask = random;
		
		int random2 = (int)Math.floor(Math.random() * bar.size());
		bar.get(random2).setData((byte)3);
		currentNum = random2;
		
	}

}
