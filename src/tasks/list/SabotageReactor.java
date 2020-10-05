package tasks.list;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import amongUs.Game;
import amongUs.Main;
import amongUs.PlayerGame;
import tasks.Sabotage;

public class SabotageReactor extends Sabotage {
	
	private int timeToLose = 40;
	private boolean but1 = false;
	private boolean but2 = false;
	private BossBar bar;

	public SabotageReactor(List<Location> loc, Location locTo, Game game, Location button1, Location button2) {
		
		super(loc, locTo);
		
		bar = Bukkit.createBossBar("Реактор", BarColor.RED, BarStyle.SOLID);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(but1 && but2)
					complete();
				
				but1 = false;
				but2 = false;
				
				for(PlayerGame player: game.getPlayers())
					if(!bar.getPlayers().contains(player.getPlayer()))
						bar.addPlayer(player.getPlayer());
				
				if(active) {
					
					timeToLose--;
					bar.setVisible(true);
					bar.setProgress(((double)timeToLose)/40.0);
					
					for(PlayerGame player: game.getPlayers())
						player.sendTitle("", "§cСаботаж реактора");
					
				} else {
					
					timeToLose = 40;
					bar.setVisible(false);
					
				}
				
				if(timeToLose < 1) {
					
					bar.removeAll();
					game.impostersWin();
					
				}
				
			}
			
		}, 20, 20);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				PlayerGame player = getPlayer(e.getPlayer());
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || block.getType() != Material.WOOD_BUTTON)
					return;
			
				if(button1.distance(block.getLocation()) < 1)
					but1 = true;
				
				if(button2.distance(block.getLocation()) < 1)
					but2 = true;
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void startAbsr() {}

}
