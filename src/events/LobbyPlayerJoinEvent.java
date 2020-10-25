package events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import game.Lobby;

public class LobbyPlayerJoinEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
		
        return handlers;
        
    }
	

	private boolean cancelled = false;
	private Lobby lobby;
	private Player player;
    
    public LobbyPlayerJoinEvent(Lobby lobby, Player player) {
    	
    	this.lobby = lobby;
    	this.player = player;
    	
    }
    
    public Lobby getLobby() {
    	
    	return lobby;
    	
    }
    
    public Player getPlayer() {
    	
    	return player;
    	
    }
    
    public boolean isCancelled() {
    	
        return cancelled;
        
    }

    public void setCancelled(boolean cancel) {
    	
        cancelled = cancel;
        
    }

    public HandlerList getHandlers() {
    	
        return handlers;
        
    }
	
}
