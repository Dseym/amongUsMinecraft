package listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import game.Lobby;

public class ServerListener implements Listener {
	
	private Lobby lobby;
	
	public ServerListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}

	@EventHandler
	void playerDisconnect(PlayerQuitEvent e) {
		
		Player player = e.getPlayer();
		
		if(!lobby.getPlayers().contains(e.getPlayer()))
			return;
		
		lobby.leave(player, true);
		
		if(lobby.getGame() != null)
			lobby.getGame().getPlayers().remove(lobby.getGame().getPlayer(player));
		
	}
	
	@EventHandler
	void clickToExit(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if(!lobby.getPlayers().contains(e.getPlayer()) || e.getItem() == null || e.getItem().getType() != Material.IRON_DOOR)
			return;
		
		lobby.leave(player, false);
		
	}
	
	@EventHandler
	void clickToStart(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if(!lobby.getPlayers().contains(e.getPlayer()) || e.getItem() == null || e.getItem().getType() != Material.WOOD_BUTTON)
			return;
		
		Bukkit.dispatchCommand(player, "among start");
		
	}
	
}
