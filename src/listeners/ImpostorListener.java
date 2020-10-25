package listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import amongUs.Inv;
import amongUs.Messages;
import game.Cameras;
import game.Lobby;
import game.Manhole;
import game.PlayerGame;
import tasks.Sabotage;
import tasks.Task;

public class ImpostorListener implements Listener {
	
	private Lobby lobby;
	
	public ImpostorListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void doingSabotage(InventoryClickEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer((Player)e.getWhoClicked());
		
		if(player == null)
			return;
		
		e.setCancelled(true);
		
		if(lobby.getGame().getVote().isActive() || !player.impostor)
			return;
	
		ItemMeta meta = e.getCurrentItem().getItemMeta();
		if (meta.getDisplayName().equalsIgnoreCase("Sabotage")) {
			
			String sabotage = meta.getLore().get(2).split("ID: ")[1];
			
			for(Sabotage sab: lobby.getGame().getSabotages())
				if(sab.isActive()) {
					
					player.sendMessage("§b§o" + Messages.sabotageLimit);
					return;
					
				}
			
			lobby.getGame().sabotage(sabotage);
			
		}
		
	}
	
	@EventHandler
	void openSabotageMenu(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if(player == null || !player.impostor || e.getItem() == null || e.getItem().getType() != Material.REDSTONE_BLOCK)
			return;
		
		Inv.showSabotageMap(player.getPlayer());
		
	}
	
	@EventHandler
	void toManhole(PlayerMoveEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if(player == null || !player.impostor || !player.isLive())
			return;
		
		Location from = e.getFrom();
		Location to = e.getTo();
		if (from.getBlock().equals(to.getBlock())) return;
		
		Location loc = player.getPlayer().getLocation();
		Manhole manhole = lobby.getGame().getMap().getManhole(loc);
		
		if(player.getPlayer().isSneaking() && player.getPlayer().getVelocity().getY() > 0 && manhole != null)
			manhole.tp(player);
		
	}
	
	@EventHandler
	void killCrewmate(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || !player.isLive() || e.getItem() == null || e.getItem().getType() != Material.IRON_SWORD || e.getHand() != EquipmentSlot.HAND || player.timeoutKill > 0)
			return;

		PlayerGame playerHitted = null;
		Location loc = player.getPlayer().getLocation();
		for (PlayerGame player2 : lobby.getGame().getPlayers()) {

			if (player2 == player || !player2.isLive())
				continue;

			Object action = player2.getAction();

			if (action != null) {

				if (action instanceof Task && ((Task) action).getPlayerLoc()
						.distance(loc) <= lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Task) action).complete(false);
					break;

				} else if (action instanceof Sabotage && ((Sabotage) action).getPlayerLoc(player2)
						.distance(loc) <= lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Sabotage) action).exit(player2);
					break;

				} else if (action instanceof Cameras && ((Cameras) action).getPlayerLoc(player2)
						.distance(loc) <= lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Cameras) action).exit(player2);
					break;

				}

			} else if (player2.getPlayer().getLocation()
					.distance(player.getPlayer().getLocation()) <= lobby.getGame().distance_kill) {

				playerHitted = player2;
				break;

			}

		}

		if (playerHitted != null && !playerHitted.impostor && !lobby.getGame().getVote().isActive()) {

			player.getPlayer().setVelocity(playerHitted.getPlayer().getLocation().toVector()
					.subtract(player.getPlayer().getLocation().toVector()));
			lobby.getGame().imposterKillPlayer(playerHitted);
			player.timeoutKill = lobby.getGame().timeout_kill;

		}
		
	}
	
}
