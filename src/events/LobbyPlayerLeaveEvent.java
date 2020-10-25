package events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import game.Lobby;

public class LobbyPlayerLeaveEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
		
        return handlers;
        
    }
	
	private Lobby lobby;
	private Player player;
    
    public LobbyPlayerLeaveEvent(Lobby lobby, Player player) {
    	
    	this.lobby = lobby;
    	this.player = player;
    	
    }
    
    public Lobby getLobby() {
    	
    	return lobby;
    	
    }
    
    public Player getPlayer() {
    	
    	return player;
    	
    }

    public HandlerList getHandlers() {
    	
        return handlers;
        
    }
	
}
