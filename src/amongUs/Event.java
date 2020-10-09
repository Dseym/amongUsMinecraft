package amongUs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import tasks.Sabotage;
import tasks.Task;

public class Event implements Listener {

	private Lobby lobby;
	
	public Event(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	private void isVisibleKilledBody(PlayerGame player) {
		
		if(!player.isLive())
			return;
		
		for(Location loc: lobby.getGame().getKilledBodies())
			if(loc.distance(player.getPlayer().getLocation()) < 4) {
				
				player.getPlayer().resetTitle();
				player.sendTitle("", "Рядом обнаружено тело (ЛКМ)");
				
			}
		
	}
	
	private void isImposterToManhole(PlayerGame player) {
		
		if(!player.impostor || !player.isLive())
			return;
		
		Location loc = player.getPlayer().getLocation();
		Manhole manhole = lobby.getGame().getMap().getManhole(loc);
		
		if(player.getPlayer().isSneaking() && player.getPlayer().getVelocity().getY() > 0 && manhole != null)
			manhole.tp(player);
		
	}
	
	@EventHandler
	void chatIsCommand(PlayerCommandPreprocessEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
			if(player == null || player.getPlayer().hasPermission("among.command"))
				return;
			
			if(!e.getMessage().split(" ")[0].equalsIgnoreCase("/among")) {
				
				e.getPlayer().sendMessage(Main.tagPlugin + "Во время игры - запрещено");
				
				e.setCancelled(true);
				
				return;
				
			}
			
		} catch (Exception e2) {}
		
	}
	
	@EventHandler
	void chatEvent(AsyncPlayerChatEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
			if (player == null)
				return;
			
			for (PlayerGame _player : lobby.getGame().getPlayers())
				if (_player.isLive() == player.isLive())
					_player.sendMessage((!player.isLive() ? "§7" : "") + player.getPlayer().getDisplayName() + ": "
							+ e.getMessage());
				else if (player.isLive())
					_player.sendMessage(player.getPlayer().getDisplayName() + ": " + e.getMessage());
			e.setCancelled(true);
			
		} catch (Exception e2) {}
		
	}
	
	@EventHandler
	void isPlayerMove(PlayerMoveEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
			if (player == null)
				return;
			Location from = e.getFrom();
			Location to = e.getTo();
			if (!from.getBlock().equals(to.getBlock())) {

				isVisibleKilledBody(player);
				isImposterToManhole(player);

				if (lobby.getGame().getVote().isActive())
					e.setCancelled(true);

			}
			
		} catch (Exception e2) {}
		
	}
	
	private void checkTask(PlayerGame player, Location loc) {
		
		Task task = lobby.getGame().getTask(loc);
		if(task == null)
			return;
		
		if(!player.impostor)
			if(task.getLocTo().distance(loc) < 1)
				task.complete(false);
			else
				task.start(player);
		else
			task.fakeProgress();
		
	}
	
	private void checkSabotage(PlayerGame player, Location loc) {
		
		Sabotage sabTask = lobby.getGame().getSabotage(loc);
		
		if(player.isLive() && sabTask != null)
			if(sabTask.getLocTo().distance(loc) < 1)
				sabTask.exit(player);
			else
				sabTask.join(player);
		
	}
	
	private void imposterKill(PlayerGame player) {
		
		if (player.timeoutKill > 0)
			return;

		PlayerGame playerHitted = null;
		Location loc = player.getPlayer().getLocation();
		for (PlayerGame player2 : lobby.getGame().getPlayers()) {

			if (player2 == player || !player2.isLive())
				continue;

			Object action = player2.getAction();

			if (action != null) {

				if (action instanceof Task && ((Task) action).getPlayerLoc()
						.distance(loc) < lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Task) action).complete(false);
					break;

				} else if (action instanceof Sabotage && ((Sabotage) action).getPlayerLoc(player2)
						.distance(loc) < lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Sabotage) action).exit(player2);
					break;

				} else if (action instanceof Cameras && ((Cameras) action).getPlayerLoc(player2)
						.distance(loc) < lobby.getGame().distance_kill) {

					playerHitted = player2;
					((Cameras) action).exit(player2);
					break;

				}

			} else if (player2.getPlayer().getLocation()
					.distance(player.getPlayer().getLocation()) < lobby.getGame().distance_kill) {

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
	
	@EventHandler
	void isPlayerClick(PlayerInteractEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
			if (player == null || e.getHand() != EquipmentSlot.HAND)
				return;
			
			e.setCancelled(true);
			
			if (e.getItem() != null && e.getItem().getType() == Material.IRON_SWORD) {
				
				imposterKill(player);
				
				return;
				
			}
			
			if (e.getItem() != null && e.getItem().getType() == Material.BRICK_STAIRS) {
				
				if (player.isLive()) {
					
					List<Location> killedBodies = new ArrayList<Location>();
					killedBodies.addAll(lobby.getGame().getKilledBodies());
					for (Location loc : killedBodies)
						if (loc.distance(e.getPlayer().getLocation()) < 4)
							lobby.getGame().meeting(player, true);
					
				}
				
				return;
				
			}
			
			if (e.getItem() != null && e.getItem().getType() == Material.REDSTONE_BLOCK) {
				
				Sabotage.openMenu(player);
				
				return;
				
			}
			
			if (e.getItem() != null && e.getItem().getType() == Material.BANNER) {
				
				Bukkit.dispatchCommand(e.getPlayer(), "among vopen");
				
				return;
				
			}
			
			if(lobby.getGame().getVote().isActive())
				return;
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

				Location loc = e.getClickedBlock().getLocation();

				if (lobby.getGame().getMap().getMetting().distance(loc) < 1 && player.isLive())
					lobby.getGame().meeting(player, false);

				checkTask(player, loc);

				checkSabotage(player, loc);

			}
			
		} catch (Exception e2) {}
		
	}
	
	@EventHandler
	void isEntityHit(EntityDamageByEntityEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer((Player) e.getEntity());
			PlayerGame player2 = lobby.getGame().getPlayer((Player) e.getDamager());
			if (player == null && player2 == null)
				return;
			e.setCancelled(true);
			
		} catch (Exception e2) {
			
			e.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	void isImposterClickToSabotage(InventoryClickEvent e) {
		
		try {
		
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
						
						player.sendMessage("§b§oНельзя устраивать несколько саботажей");
						return;
						
					}
				
				lobby.getGame().sabotage(sabotage);
				
			}
			
		} catch (Exception e2) {}
		
	}
	
	@EventHandler
	void dropItem(PlayerDropItemEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer((Player) e.getPlayer());
			if (player == null)
				return;
			e.setCancelled(true);
			
		} catch (Exception e2) {}
		
	}
	
	@EventHandler
	void hunger(FoodLevelChangeEvent e) {
		
		try {
			
			PlayerGame player = lobby.getGame().getPlayer((Player) e.getEntity());
			if (player == null)
				return;
			e.setCancelled(true);
			
		} catch (Exception e2) {}
		
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
