package listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import game.Lobby;
import game.PlayerGame;

public class MeetingListener implements Listener {

	private Lobby lobby;
	
	public MeetingListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void voting(PlayerInteractEvent e) {

		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || e.getHand() != EquipmentSlot.HAND || e.getItem() == null || e.getItem().getType() != Material.BOOK)
			return;
		
		Bukkit.dispatchCommand(e.getPlayer(), "among vopen");
		
	}
	
	@EventHandler
	void doingMeeting(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || lobby.getGame().getVote().isActive() || e.getClickedBlock() == null)
			return;
		
		Location loc = e.getClickedBlock().getLocation();

		if (lobby.getGame().getMap().getMetting().distance(loc) < 1 && player.isLive())
			lobby.getGame().meeting(player, false);
		
	}
	
}
