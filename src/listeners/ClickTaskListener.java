package listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import game.Lobby;
import game.PlayerGame;
import tasks.Sabotage;
import tasks.Task;

public class ClickTaskListener implements Listener {
	
	private Lobby lobby;
	
	public ClickTaskListener(Lobby lobby) {
		
		this.lobby = lobby;
		
	}
	
	@EventHandler
	void task(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || e.getHand() != EquipmentSlot.HAND || e.getClickedBlock() == null)
			return;
		
		Location loc = e.getClickedBlock().getLocation();
		
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
	
	@EventHandler
	void sabotage(PlayerInteractEvent e) {
		
		PlayerGame player = lobby.getGame().getPlayer(e.getPlayer());
		if (player == null || e.getHand() != EquipmentSlot.HAND || e.getClickedBlock() == null)
			return;
		
		Location loc = e.getClickedBlock().getLocation();
		
		Sabotage sabTask = lobby.getGame().getSabotage(loc);
		
		if(player.isLive() && sabTask != null)
			if(sabTask.getLocTo().distance(loc) < 1)
				sabTask.exit(player);
			else
				sabTask.join(player);
		
	}

}
