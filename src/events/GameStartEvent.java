package events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import game.Game;

public class GameStartEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
		
        return handlers;
        
    }
	

	private boolean cancelled = false;
	private Game game;
    
    public GameStartEvent(Game game) {
    	
    	this.game = game;
    	
    }
    
    public Game getGame() {
    	
    	return game;
    	
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
