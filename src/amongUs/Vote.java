package amongUs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Vote {
	
	private Game game;
	private boolean active = false;
	private BossBar bar;
	public int timeVote = 60;
	private Map<PlayerGame, PlayerGame> votes = new HashMap<PlayerGame, PlayerGame>();
	private List<PlayerGame> skipping = new ArrayList<PlayerGame>();
	private Inventory invVote;
	
	public Vote(Game game) {
		
		this.game = game;
		
		timeVote = game.time_voting;
		
		bar = Bukkit.createBossBar("Голосование", BarColor.GREEN, BarStyle.SOLID);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {@Override public void run() {tick();}}, 20, 20);
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			void playerClickInv(InventoryClickEvent e) {
				
				ItemStack item = e.getCurrentItem();
				
				if(item == null || !e.getClickedInventory().equals(invVote))
					return;
				
				if(!item.getItemMeta().getDisplayName().equalsIgnoreCase("Пропустить"))
					Bukkit.dispatchCommand(e.getWhoClicked(), "among v " + item.getItemMeta().getDisplayName());
				else
					Bukkit.dispatchCommand(e.getWhoClicked(), "among v skip");
				
				e.getWhoClicked().closeInventory();
				
			}
			
		}, Main.plugin);
		
	}
	
	public void tick() {
		
		if(active)
			timeVote--;
		else
			timeVote = game.time_voting;
		
		bar.setProgress(((double)timeVote)/game.time_voting);
		
		if(timeVote < 1)
			result();
		
	}
	
	public String vote(Player player1, String player2) {
		
		if(!active)
			return "Сейчас нет голосования";
		
		PlayerGame whoVoting = game.getPlayer(player1);
		if(whoVoting == null)
			return "Вы не в игре";
		
		if(!whoVoting.isLive())
			return "Вы мертвы";
		
		if(votes.containsKey(whoVoting) || skipping.contains(whoVoting))
			return "Вы уже голосовали";
		
		PlayerGame player = game.getPlayer(Bukkit.getPlayer(player2));
		if(player == null)
			return "Такого игрока нет в игре";
		
		if(!player.isLive())
			return "Этот игрок мертв";

		
		votes.put(whoVoting, player);
		
		if(votes.size() + skipping.size() + 1 > game.getLivePlayers().size())
			result();
		
		return "true";
		
	}
	
	public void start() {
		
		if(active)
			return;
		
		skipping.clear();
		votes.clear();
		active = true;
		timeVote = game.time_voting;
		
		invVote = Bukkit.createInventory(null, 36, "Голосование");
		
		for(PlayerGame player: game.getPlayers()) {
			
			bar.addPlayer(player.getPlayer());
			
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(player.getPlayer().getName());
			item.setItemMeta(meta);
			
			invVote.addItem(item);
			
		}
		
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Пропустить");
		item.setItemMeta(meta);
		
		invVote.setItem(35, item);
		
	}
	
	public String openInv(Player player) {
		
		if(!active)
			return "Сейчас нет голосования";
		
		PlayerGame whoVoting = game.getPlayer(player);
		if(whoVoting == null)
			return "Вы не в игре";
		
		if(!whoVoting.isLive())
			return "Вы мертвы";
		
		if(votes.containsKey(whoVoting) || skipping.contains(whoVoting))
			return "Вы уже голосовали";
		
		player.openInventory(invVote);
		
		return "true";
		
	}
	
	public String skip(Player player) {
		
		if(!active)
			return "Сейчас нет голосования";
		
		PlayerGame whoVoting = game.getPlayer(player);
		if(whoVoting == null)
			return "Вы не в игре";
		
		if(!whoVoting.isLive())
			return "Вы мертвы";
		
		if(votes.containsKey(whoVoting) || skipping.contains(whoVoting))
			return "Вы уже голосовали";
		
		skipping.add(whoVoting);
		
		if(votes.size() + skipping.size() + 1 > game.getLivePlayers().size())
			result();
		
		return "true";
		
	}
	
	public boolean isActive() {
		
		return active;
		
	}
	
	public void result() {
		
		if(!active)
			return;
		
		for(PlayerGame player: game.getPlayers()) {
			
			if(player.impostor && player.isLive())
				player.timeoutKill = game.timeout_kill;
			
			if(!(votes.containsKey(player) || skipping.contains(player)))
				skip(player.getPlayer());
			
		}
		
		bar.removeAll();
		
		active = false;
		game.timeoutMeeting = game.timeout_metting;
		
		if(skipping.size() > votes.size()) {
			
			for(PlayerGame player: game.getPlayers())
				player.sendTitle("Голосование скипнуто", "");
			
		} else if(skipping.size() == votes.size()) {
			
			for(PlayerGame player: game.getPlayers())
				player.sendTitle("Мы не смогли принять единого решения", "");
			
		} else {
			
			Map<PlayerGame, List<PlayerGame>> votes = new HashMap<PlayerGame, List<PlayerGame>>();
			PlayerGame player = null;
			
			for(PlayerGame _player: this.votes.keySet()) {
				
				List<PlayerGame> list;
				
				if(votes.containsKey(this.votes.get(_player)))
					list = votes.get(this.votes.get(_player));
				else
					list = new ArrayList<PlayerGame>();
				
				list.add(_player);
				
				votes.put(this.votes.get(_player), list);
				
			}
			
			int lastVotes = 0;
			for(PlayerGame _player: votes.keySet())
				if(votes.get(_player).size() > lastVotes) {

					player = _player;
					lastVotes = votes.get(_player).size();
					
				}
			
			for(PlayerGame list: votes.keySet())
				if(list != player && votes.get(list).size() == lastVotes) {
					
					player.sendTitle("Мы не смогли принять единого решения", "");
					return;
					
				}
			
			game.killPlayer(player);
			for(PlayerGame _player: game.getPlayers())
				_player.sendTitle(player.getPlayer().getDisplayName() + (game.confirm_eject ? (player.impostor ? " был предателем" : " не был предателем") : " был изгнан"), "");
			
		}
		
	}
	
}
