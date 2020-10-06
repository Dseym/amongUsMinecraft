package amongUs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Lobby {
	
	public static Lobby lobby;

	private Location loc;
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, Location> locPlayers = new HashMap<Player, Location>();
	private Game game;
	private Scoreboard board;
	
	
	public static Lobby getLobby(Player player) {
		
		for(Player _player: lobby.getPlayers())
			if(_player == player)
				return lobby;
		
		return null;
		
	}
	
	
	public Lobby(Location loc) {
		
		this.loc = loc;
		Bukkit.getPluginManager().registerEvents(new Event(this), Main.plugin);
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("game", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("Конфиг");
		obj.getScore("Нет игры").setScore(0);;
		
	}
	
	public void createGame(FileConfiguration config) {
		
		if(game != null)
			game.end();
		
		game = new Game(config, this);
		
		reloadSb();
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "Создана игра для этого лобби");
		
	}
	
	public void isGameStop() {
		
		for(Player player: players) {
			
			player.setScoreboard(board);
			player.teleport(loc);
			
		}
		
		game = null;
		
	}
	
	public void reloadSb() {
		
		if(game == null)
			return;
		
		for(String str: board.getEntries())
			board.resetScores(str);
		
		Objective obj = board.getObjective("game");

		obj.getScore("imposters: " + game.imposters).setScore(9);
		obj.getScore("confirm_eject: " + game.confirm_eject).setScore(8);
		obj.getScore("emergency_metting: " + game.emergency_metting).setScore(7);
		obj.getScore("timeout_metting: " + game.timeout_metting).setScore(6);
		obj.getScore("time_voting: " + game.time_voting).setScore(5);
		obj.getScore("speed_player: " + game.speed_player).setScore(4);
		obj.getScore("timeout_kill: " + game.timeout_kill).setScore(3);
		obj.getScore("distance_kill: " + game.distance_kill).setScore(2);
		obj.getScore("tasksNum: " + game.tasksNum).setScore(1);
		obj.getScore("visual_task: " + game.visual_task).setScore(0);
		
	}
	
	public String startGame() {
		
		String answ = game.start(players);
		
		if(answ.equalsIgnoreCase("true"))
			for(Player player: players)
				player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		
		return answ;
		
	}
	
	public Game getGame() {
		
		return game;
		
	}
	
	public List<Player> getPlayers() {
		
		return players;
		
	}
	
	public Location getLocation() {
		
		return loc;
		
	}
	
	public void join(Player player) {
		
		if(players.contains(player)) {
			
			player.sendMessage(Main.tagPlugin + "Вы уже в лобби");
			
			return;
			
		}
		
		if(game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + "Сейчас идет игра");
			
			return;
			
		}
		
		player.sendMessage(Main.tagPlugin + "Вы вошли в лобби");
		
		player.setScoreboard(board);
		
		locPlayers.put(player, player.getLocation());
		player.getPlayer().teleport(loc.clone());
		player.setGameMode(GameMode.ADVENTURE);
		
		players.add(player);
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§eИгрок §o" + player.getDisplayName() + "§r§e присоединился к лобби (" + players.size() + ")");
		
	}
	
	public void leave(Player player, boolean disconnect) {
		
		if(!players.contains(player)) {
			
			player.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		if(!disconnect && game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + "Сейчас идет игра");
			
			return;
			
		}
		
		player.sendMessage(Main.tagPlugin + "Вы вышли из лобби");
		
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		
		player.teleport(locPlayers.get(player));
		player.setGameMode(GameMode.SURVIVAL);
		
		locPlayers.remove(player);
		players.remove(player);
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§eИгрок §o" + player.getDisplayName() + "§r§e покинул лобби (" + players.size() + ")");
		
	}
	
}
