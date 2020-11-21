package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import amongUs.Main;
import amongUs.Messages;
import events.LobbyPlayerJoinEvent;
import events.LobbyPlayerLeaveEvent;
import listeners.CancelListener;
import listeners.ClickTaskListener;
import listeners.GameEndListener;
import listeners.ImpostorListener;
import listeners.MeetingListener;
import listeners.ReportBodyListener;
import listeners.ServerListener;

public class Lobby {
	
	public static List<Lobby> lobby = new ArrayList<Lobby>();

	public static Lobby getLobby(String name) {
		
		for(Lobby lobby: lobby)
			if(name.equalsIgnoreCase(lobby.getName()))
				return lobby;
		
		return null;
		
	}
	
	public static Lobby getLobbyForOwner(Player p) {
		
		for(Lobby lobby: Lobby.lobby)
			if(lobby.owner != null && lobby.owner.equals(p))
				return lobby;
		
		return null;
		
	}
	
	public static Lobby getLobby(Player player) {
		
		for(Lobby lobby: lobby)
			if(lobby.getPlayers().contains(player))
				return lobby;
		
		return null;
		
	}
	
	
	private Location loc;
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, Location> locPlayers = new HashMap<Player, Location>();
	private Game game;
	private Scoreboard board;
	private String name;
	private int time = 60;
	private Player owner;
	private FileConfiguration defaultConfig;
	
	public Lobby(Location loc, String name, FileConfiguration defaultConfig) {
		
		this.loc = loc;
		this.name = name;
		this.defaultConfig = defaultConfig;
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("game", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(Messages.configMess);
		obj.getScore(Messages.notGame).setScore(0);
		
		createGame(defaultConfig);
		
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
		
		Bukkit.getPluginManager().registerEvents(new CancelListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new ClickTaskListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new ImpostorListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new MeetingListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new ReportBodyListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new ServerListener(this), Main.plugin);
		Bukkit.getPluginManager().registerEvents(new GameEndListener(this), Main.plugin);
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public FileConfiguration getDefaultConfig() {
		
		return defaultConfig;
		
	}
	
	public void setLoc(Location loc) {
		
		this.loc = loc;
		
	}
	
	public void gameStop(String cause) {
		
		if(game != null && game.isStart())
			game.end(cause);
		
		List<Player> _players = new ArrayList<Player>();
		
		_players.addAll(players);
		
		for(Player player: _players)
			player.setGameMode(GameMode.SPECTATOR);
		
		if(cause.equalsIgnoreCase("Plugin Disable")) {
			
			for(Player player: _players)
				leave(player, true);
			
			return;
			
		}
		
		Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(Player player: _players)
					leave(player, true);
				
			}
			
		}, 5*20);
		
		game = null;
		
	}
	
	public Player getOwner() {
		
		return owner;
		
	}
	
	public void createGame(FileConfiguration config) {
		
		if(game != null && game.isStart())
			game.end("New game");
		
		game = new Game(config, loc);
		
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
	
	public void reloadSb() {
		
		if(game == null)
			return;
		
		for(String str: board.getEntries())
			board.resetScores(str);
		
		Objective obj = board.getObjective("game");

		obj.getScore(Messages.countToGame.replace("@time@", "" + time)).setScore(8);
		obj.getScore(Messages.owner + ": " + (owner != null ? owner.getName() : "")).setScore(7);
		obj.getScore(Messages.players + ": " + players.size() + "/" + game.getMap().getSpawns().size()).setScore(6);
		obj.getScore(" ").setScore(5);
		obj.getScore(Messages.map + game.getMap().getName()).setScore(4);
		obj.getScore(Messages.imposters + game.imposters).setScore(3);
		obj.getScore(Messages.confirmEject + game.confirm_eject).setScore(2);
		obj.getScore(Messages.tasks + ": " + game.tasksNum).setScore(1);
		obj.getScore(Messages.visualTasks + game.visual_task).setScore(0);
		
	}
	
	public String startGame() {
		
		String answ = game.checkParametrs(players);
		
		if(answ.equalsIgnoreCase(Messages.success)) {
			
			for(Player player: players)
				player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			
			game.start(players);
			
		}
		
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
		
		if(game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		if(game != null && game.getMap().getSpawns().size()-1 < players.size()) {
			
			player.sendMessage(Main.tagPlugin + Messages.maxPlayers);
			
			return;
			
		}
		
		LobbyPlayerJoinEvent playerJoin = new LobbyPlayerJoinEvent(this, player);
		Bukkit.getPluginManager().callEvent(playerJoin);
		if(playerJoin.isCancelled()) return;
		
		player.sendMessage(Main.tagPlugin + Messages.plJoinToLobby);
		player.setScoreboard(board);
		locPlayers.put(player, player.getLocation());
		player.teleport(loc.clone());
		player.setGameMode(GameMode.ADVENTURE);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
		Kits.lobby(player);
		player.getInventory().setHeldItemSlot(0);
		
		if(players.size() == 0)
			owner = player;
		
		players.add(player);
		player.setResourcePack(Main.textures);
		
		reloadSb();
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§e" + Messages.plJoinToLobbyMessPlayers.replace("@player@", player.getDisplayName()).replace("@countPlayers@", "" + players.size()));
		
	}
	
	public void leave(Player player, boolean disconnect) {
		
		if(!disconnect && game != null && game.isStart()) {
			
			player.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		LobbyPlayerLeaveEvent playerLeave = new LobbyPlayerLeaveEvent(this, player);
		Bukkit.getPluginManager().callEvent(playerLeave);
		
		time = 60;
		
		player.sendMessage(Main.tagPlugin + Messages.plLeaveFromLobby);
		
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		
		player.teleport(locPlayers.get(player));
		player.setGameMode(GameMode.SURVIVAL);
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.getInventory().clear();
		player.getInventory().setHeldItemSlot(0);
		
		locPlayers.remove(player);
		players.remove(player);
		
		if(owner.equals(player))
			owner = null;
		
		if(players.size() != 0)
			owner = players.get(0);
		
		reloadSb();
		
		for(Player _player: players)
			_player.sendMessage(Main.tagPlugin + "§e" + Messages.plLeaveFromLobbyMessPlayers.replace("@player@", player.getDisplayName()).replace("@countPlayers@", "" + players.size()));
		
	}
	
}
