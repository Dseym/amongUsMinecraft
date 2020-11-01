package listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import amongUs.Main;
import amongUs.Messages;
import game.Lobby;
import game.PlayerGame;

public class CancelListener implements Listener {

	private Lobby lobby;
	
	public CancelListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void cancelEntityDamage(EntityDamageByEntityEvent e) {
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	void cancelDrop(PlayerDropItemEvent e) {
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	void cancelHunger(FoodLevelChangeEvent e) {

		e.setCancelled(true);
		
	}
	
	@EventHandler
	void cancelChat(AsyncPlayerChatEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null)
			return;
		
		e.setCancelled(true);
		
		if(!lobby.getGame().getVote().isActive()) return;
		
		for (PlayerGame _player : lobby.getGame().getPlayers())
			if (_player.isLive() == player.isLive())
				_player.sendMessage((!player.isLive() ? "§7" : "") + player.getPlayer().getDisplayName() + ": "
						+ e.getMessage());
			else if (player.isLive())
				_player.sendMessage(player.getPlayer().getDisplayName() + ": " + e.getMessage());
		
	}
	
	@EventHandler
	void cancelMove(PlayerMoveEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null)
			return;
		
		Location from = e.getFrom();
		Location to = e.getTo();
		if (from.getBlock().equals(to.getBlock())) return;
		
		if (lobby.getGame().getVote().isActive())
			e.setCancelled(true);
		
	}
	
	@EventHandler
	void cancelCommand(PlayerCommandPreprocessEvent e) {

		if(lobby.getGame() == null) return;
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if(player == null || player.getPlayer().hasPermission("among.command"))
			return;
		
		if(!e.getMessage().split(" ")[0].equalsIgnoreCase("/among")) {
			
			e.getPlayer().sendMessage(Main.tagPlugin + Messages.notPerm);
			
			e.setCancelled(true);
			
			return;
			
		}
	
	}
	
	@EventHandler
	void cancelClick(PlayerInteractEvent e) {
		
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
		
	}
	
}
