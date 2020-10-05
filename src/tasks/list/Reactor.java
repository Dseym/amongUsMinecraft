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
import org.bukkit.scheduler.BukkitTask;

import amongUs.Main;
import tasks.Task;

public class Reactor extends Task {
	
	private List<Location> buttons;
	private List<Location> bar;
	private int progress = 0;
	private String series = "";
	private boolean nowShow = false;
	private BukkitTask timerShow;
	private int currentClick = 0;

	public Reactor(Location loc, Location locTo, List<Location> bar, List<Location> buttons) {
		
		super(loc, locTo);
		
		this.buttons = buttons;
		this.bar = bar;
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND || nowShow || block.getType() != Material.WOOL)
					return;
			
				int num = 0;
				for(int i = 0; i < buttons.size(); i++)
					if(block.getLocation().distance(buttons.get(i)) < 0.5) {
						
						num = i;
						
						break;
						
					}
				
				block.setData((byte)10);
				
				startTimeout();
				
				int nextClick = Integer.parseInt("" + series.split(",")[currentClick]);
				if(num == nextClick) {
					
					if(progress > currentClick) {
						
						Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {block.setData((byte)7);}}, 10);
						currentClick++;
						
					} else {
						
						if(progress > bar.size()-2) {
							
							complete(true);
							return;
							
						}
						
						bar.get(progress).getBlock().setData((byte)5);
						progress++;
						currentClick = 0;
						Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {showSeries(0);}}, 15);
						
					}
					
				} else {
					
					player.sendMessage("§bЗадание: §oневерно");
					complete(false);
					
				}
				
			}
			
		}, Main.plugin);
		
	}
	
	@SuppressWarnings("deprecation")
	private void showSeries(int i) {
		
		for(Location block: buttons)
			block.getBlock().setData((byte)7);
		
		nowShow = true;
		
		if(i-1 < progress) {
		
			int num = Integer.parseInt(series.split(",")[i]);
			
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {buttons.get(num).getBlock().setData((byte)11);}}, 5);
			
			timerShow = Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					
					buttons.get(num).getBlock().setData((byte)7);
					
					showSeries(i+1);
					
				}
				
			}, 15);
			
			startTimeout();
			
		} else {
			
			timerShow.cancel();
			nowShow = false;
			
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		
		series = "";
		progress = 0;
		currentClick = 0;
		for(Location block: buttons)
			block.getBlock().setData((byte)7);
		for(Location block: bar)
			block.getBlock().setData((byte)7);
		
		for(int i = 0; i < bar.size(); i++) {
			
			int num = (int)Math.floor(Math.random() * buttons.size());
			
			series += Integer.toString(num) + ",";
			
		}
		
		series = series.substring(0, series.length()-1);
		
		nowShow = true;
		
		Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {showSeries(0);}}, 30);
		
	}

	@Override
	public void stop() {
		
		if(timerShow != null && !timerShow.isCancelled())
			timerShow.cancel();
		
	}

}
