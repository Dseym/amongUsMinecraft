package sabotages;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import amongUs.Main;
import amongUs.Messages;
import game.Game;
import game.PlayerGame;

public class SabotageOxygen extends Sabotage {
	
	private String code = "";
	private String enteredCode = "";
	private BossBar bar;
	private int timeToLose = 40;
	private Location signEnterCode;
	private Location signCode;

	public SabotageOxygen(List<Location> loc, Location locTo, Game game, Location signEnterCode, Location signCode) {
		
		super(loc, locTo);
		
		this.signEnterCode = signEnterCode;
		this.signCode = signCode;
		
		bar = Bukkit.createBossBar(Messages.oxygenBar, BarColor.RED, BarStyle.SOLID);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(PlayerGame player: game.getPlayers())
					if(!bar.getPlayers().contains(player.getPlayer()))
						bar.addPlayer(player.getPlayer());
				
				if(active) {
					
					timeToLose--;
					bar.setVisible(true);
					bar.setProgress(((double)timeToLose)/40.0);
					
					for(PlayerGame player: game.getPlayers())
						player.sendTitle("", "§c" + Messages.oxygenSabotage);
					
				} else {
					
					timeToLose = 40;
					bar.setVisible(false);
					
				}
				
				if(timeToLose < 1) {
					
					bar.removeAll();
					game.impostersWin();
					
				}
				
			}
			
		}, 20, 20);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@SuppressWarnings("deprecation")
			@EventHandler
			void playerClick(PlayerInteractEvent e) {
				
				Block block = e.getClickedBlock();
				PlayerGame player = getPlayer(e.getPlayer());
				
				if(player == null || e.getPlayer() != player.getPlayer() || block == null || e.getHand() != EquipmentSlot.HAND)
					return;
				
				if(block.getType() == Material.CONCRETE_POWDER) {
					
					if(block.getData() == 5) {
						
						if(enteredCode.equalsIgnoreCase(code))
							complete();
						else
							player.sendMessage("§b§o" + Messages.oxygenCodeNotFull);
						
					}
					
					return;
					
				}
				
				if(block.getType() != Material.SIGN && block.getType() != Material.WALL_SIGN)
					return;
				
				String num = ((Sign)block.getState()).getLine(1);
				
				if(!("" + code.toCharArray()[enteredCode.length()]).equalsIgnoreCase(num)) {
					
					player.sendMessage("§b§o" + Messages.oxygenWrongNum);
					
					return;
					
				}
				
				if(enteredCode.length() < 5)
					enteredCode += num;
				
				update();
				
			}
			
		}, Main.plugin);
		
	}
	
	private void update() {
		
		Sign sign = ((Sign) signEnterCode.getBlock().getState());
		
		sign.setLine(0, "Entered:");
		sign.setLine(1, enteredCode);
		
		sign.update();
		
	}

	@Override
	public void startAbsr() {
		
		code = "";
		enteredCode = "";
		
		update();
		
		for(int i = 0; i < 6; i++) {
			
			int num = (int)Math.floor(Math.random() * 10);
			
			code += Integer.toString(num);
			
		}
		
		Sign sign = ((Sign) signCode.getBlock().getState());
		
		sign.setLine(0, "Code:");
		sign.setLine(1, code);
		
		sign.update();
		
	}

}
