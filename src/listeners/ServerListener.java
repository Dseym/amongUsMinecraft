package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	
}
