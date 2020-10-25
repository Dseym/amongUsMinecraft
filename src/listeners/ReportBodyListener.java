package listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Messages;
import game.Lobby;
import game.PlayerGame;

public class ReportBodyListener implements Listener {
	
	private Lobby lobby;
	
	public ReportBodyListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void visibleBody(PlayerMoveEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if(player == null || !player.isLive())
			return;
		
		Location from = e.getFrom();
		Location to = e.getTo();
		if (from.getBlock().equals(to.getBlock())) return;
		
		for(Location loc: lobby.getGame().getKilledBodies())
			if(loc.distance(player.getPlayer().getLocation()) < 4) {
				
				player.getPlayer().resetTitle();
				player.sendTitle("", Messages.visibleBody);
				
			}
		
	}
	
	@EventHandler
	void report(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || !player.isLive() || e.getHand() != EquipmentSlot.HAND || e.getItem() == null || e.getItem().getType() != Material.CLAY_BRICK)
			return;

		List<Location> killedBodies = new ArrayList<Location>();
		killedBodies.addAll(lobby.getGame().getKilledBodies());
		for (Location loc : killedBodies)
			if (loc.distance(e.getPlayer().getLocation()) < 4)
				lobby.getGame().meeting(player, true);
		
	}

}
