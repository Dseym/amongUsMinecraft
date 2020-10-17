package tasks.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import amongUs.Main;
import amongUs.Messages;
import tasks.Task;

public class Card extends Task {
	
	private boolean active = false;

	public Card(Location loc, Location locTo, Location start, Location end) {
		
		super(loc, locTo);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerMove(PlayerMoveEvent e) {
				
				if(player == null || e.getPlayer() != player.getPlayer())
					return;
				
				Location from = e.getFrom();
				Location to = e.getTo();
				
				if(active) {
					
					if(from.distance(end) < 0.25) {
						
						complete(true);
						return;
						
					}
				
					if(from.distance(to) > 0.24) {
						
						active = false;
						player.sendMessage("§b" + Messages.cardFastTask);
						
					} else if(from.distance(to) < 0.10) {
						
						active = false;
						player.sendMessage("§b" + Messages.cardSlowTask);
						
					}
					
				} else {
					
					if(from.distance(start) < 0.25)
						active = true;
					
				}
				
			}
			
		}, Main.plugin);
		
	}

	@Override
	public void start() {
		
		active = false;
		
	}

	@Override
	public void stop() {}
	
}
