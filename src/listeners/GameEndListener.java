package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import events.GameEndEvent;
import game.Lobby;

public class GameEndListener implements Listener {

	private Lobby lobby;
	
	public GameEndListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void start(GameEndEvent e) {
		
		if(!e.getGame().equals(lobby.getGame())) return;
		
		lobby.gameStop(e.getCause());
		
		lobby.createGame(lobby.getDefaultConfig());
		
	}
	
}
