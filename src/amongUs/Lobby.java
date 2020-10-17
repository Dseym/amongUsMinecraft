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
	
	public static List<Lobby> lobby = new ArrayList<Lobby>();

	private Location loc;
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, Location> locPlayers = new HashMap<Player, Location>();
	private Game game;
	private Scoreboard board;
	private String name;
	private int time = 60;
	
	public static Lobby getLobby(Player player) {
		
		for(Lobby lobby: lobby)
			for(Player _player: lobby.getPlayers())
				if(_player == player)
					return lobby;
		
		return null;
		
	}
	
	public static Lobby getLobby(String name) {
		
		for(Lobby lobby: lobby)
			if(name.equalsIgnoreCase(lobby.getName()))
				return lobby;
		
		return null;
		
	}
	
	
	public String getName() {
		
		return name;
		
	}
	
	public void setLoc(Location loc) {
		
		this.loc = loc;
		
	}
	
	public Lobby(Location loc, String name) {
		
		this.loc = loc;
		this.name = name;
		Bukkit.getPluginManager().registerEvents(new Event(this), Main.plugin);
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("game", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(Messages.configMess);
		obj.getScore(Messages.notGame).setScore(0);
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(game == null || game.isStart())
					return;
				
				if(game.playerOnCount != -1 && players.size() > game.playerOnCount-1)
					time--;
				else
					time = 60;
				
				reloadSb();
				
				if(time < 1) {
					
					String response = startGame();
					if(!response.equalsIgnoreCase("true")) {
						
						time = 60;
						
						for(Player player: players)
							player.sendMessage(Main.tagPlugin + response);
						
						return;
						
					}
					
				}
				
			}
			
		}, 20, 20);
		
	}
	
	public void createGame(FileConfiguration config) {
		
		if(game != null)
			game.end();
		
		game = new Game(config, this, loc);
		
		if(game.getMap() == null) {
			
			game = null;
			
			return;
			
		}
		
		reloadSb();
		
		int numPlayer = 0;
		for(Player _player: players) {
			
			numPlayer++;
			
			_player.sendMessage(Main.tagPlugin + Messages.gameCreated);
			
			if(numPlayer > game.getMap().getSpawns().size())
				leave(_player, false);
			
		}
		
	}
	
	public void isGameStop() {
		
		List<Player> _players = new ArrayList<Player>();
		
		_players.addAll(players);
		
		for(Player player: _players)
			leave(player, true);
		
		game = null;
		
	}
	
	public void reloadSb() {
		
		if(game == null)
			return;
		
		for(String str: board.getEntries())
			board.resetScores(str);
		
		Objective obj = board.getObjective("game");

		obj.getScore(Messages.countToGame.replace("@time@", "" + time)).setScore(5);
		obj.getScore(" ").setScore(4);
		obj.getScore("Number imposters: " + game.imposters).setScore(3);
		obj.getScore("Confirm Eject: " + game.confirm_eject).setScore(2);
		obj.getScore("Number Tasks: " + game.tasksNum).setScore(1);
		obj.getScore("Visual Tasks: " + game.visual_task).setScore(0);
		
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
			
			player.sendMessage(Main.tagPlugin + Messages.plInLobby);
			
			return;
			
		}
		
		if(game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		if(game != null && game.getMap().getSpawns().size()-1 < players.size()) {
			
			player.sendMessage(Main.tagPlugin + Messages.maxPlayers);
			
			return;
			
		}
		
		player.sendMessage(Main.tagPlugin + Messages.plJoinToLobby);
		
		player.setScoreboard(board);
		
		locPlayers.put(player, player.getLocation());
		player.getPlayer().teleport(loc.clone());
		player.setGameMode(GameMode.ADVENTURE);
		
		players.add(player);
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§e" + Messages.plJoinToLobbyMessPlayers.replace("@player@", player.getDisplayName()).replace("@countPlayers@", "" + players.size()));
		
	}
	
	public void leave(Player player, boolean disconnect) {
		
		if(!disconnect && game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		time = 60;
		
		player.sendMessage(Main.tagPlugin + Messages.plLeaveFromLobby);
		
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		
		player.teleport(locPlayers.get(player));
		player.setGameMode(GameMode.SURVIVAL);
		
		locPlayers.remove(player);
		players.remove(player);
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§e" + Messages.plLeaveFromLobbyMessPlayers.replace("@player@", player.getDisplayName()).replace("@countPlayers@", "" + players.size()));
		
	}
	
}
