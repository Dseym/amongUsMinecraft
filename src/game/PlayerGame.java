package game;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import amongUs.Main;

public class PlayerGame {
	
	public boolean impostor = false;
	public int timeoutKill = 15;
	public BossBar timeoutBar;
	public Color color;
	public int countAction = 0;
	
	private Object currentAction;
	
	private Player player;
	private boolean isLive = true;
	
	public PlayerGame(Player player) {
		
		this.player = player;
		
	}
	
	public void setAction(Object action) {
		
		currentAction = action;
		
	}
	
	public Object getAction() {
		
		return currentAction;
		
	}
	
	public boolean isLive() {
		
		return isLive;
		
	}
	
	public Player getPlayer() {
		
		return player;
		
	}
	
	public void sendMessage(String mess) {
		
		player.sendMessage(Main.tagPlugin + mess);
		
	}
	
	public void sendTitle(String mess, String subMess) {
		
		player.sendTitle(mess, subMess, 10, 80, 0);
		
	}
	
	public void kill() {
		
		isLive = false;
		
		getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 4));
		
	}
	
}
