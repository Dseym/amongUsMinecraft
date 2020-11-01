package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import amongUs.Config;
import amongUs.Main;
import amongUs.Messages;
import amongUs.Protocol;
import events.GameEndEvent;
import events.GameStartEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.minecraft.server.v1_12_R1.Packet;
import sabotages.Sabotage;
import sabotages.SabotageCommunicate;
import sabotages.SabotageElectrical;
import sabotages.SabotageOxygen;
import sabotages.SabotageReactor;
import tasks.Task;

public class Game {
	
	public boolean confirm_eject = false;
	public int emergency_metting = 1;
	public int timeout_metting = 15;
	public int time_voting = 60;
	public int speed_player = 1;
	public int timeout_kill = 15;
	public int distance_kill = 2;
	public int tasksNum = 5;
	public boolean visual_task = true;
	public int imposters = 1;
	public int playerOnCount = 5;
	public List<String> chanceImposter = new ArrayList<String>();
	
	private int emergencyMettingNum = 0;
	private List<PlayerGame> players = new ArrayList<PlayerGame>();
	private boolean isStart = false;
	private MapGame map;
	private Map<Integer, Location> killedBodies = new HashMap<Integer, Location>();
	private BossBar bar;
	private Vote vote;
	public int timeoutMeeting = 15;
	private Team teamPlayers;
	private BukkitTask timerUpdate;
	
	private String impostersStr = " ";

	public Game(FileConfiguration config, Location loc) {
		
		create(config, loc);
		
	}
	
	public boolean isStart() {
		
		return isStart;
		
	}
	
	public String checkParametrs(List<Player> players) {
		
		if(players == null || players.size() < 3)
			return Messages.fewPlayer;
	
		if(emergency_metting < 1)
			return "emergency_metting - " + Messages.incorrectValue;
		
		if(time_voting < 1)
			return "time_voting - " + Messages.incorrectValue;
		
		if(speed_player < 1)
			return "speed_player - " + Messages.incorrectValue;
		
		if(timeout_kill < 1)
			return "timeout_kill - " + Messages.incorrectValue;
		
		if(distance_kill < 1)
			return "distance_kill - " + Messages.incorrectValue;
		
		if(tasksNum < 1)
			return "tasksNum - " + Messages.incorrectValue;
		
		if(timeout_metting < 1)
			return "timeout_metting - " + Messages.incorrectValue;
	
		if(imposters < 1)
			return "imposters - " + Messages.incorrectValue;
		
		if(getTasks().size() < tasksNum)
			return "tasksNum - " + Messages.incorrectValue;
		
		if(speed_player > 9)
			return "speed_player - " + Messages.incorrectValue;
	
		if(imposters > players.size()-imposters-1)
			return Messages.fewCrewmate;
		
		if(map.getSpawns().size() < players.size())
			return Messages.fewSpawnsOnMap;
		
		return Messages.success;
		
	}
	
	@SuppressWarnings("deprecation")
	public String start(List<Player> players) {
		
		GameStartEvent gameStart = new GameStartEvent(this);
		Bukkit.getPluginManager().callEvent(gameStart);
		if(gameStart.isCancelled()) return "Cancel";
		
		int i = 0;
		for(Player player: players) {
			
			PlayerGame pg = new PlayerGame(player);
			
			this.players.add(pg);
			Color color;
			try {
				
				color = Color.values()[i];
			
			} catch(Exception e) {
				
				color = Color.values()[0];

				Main.plugin.getLogger().info("Not enough colors"); Kits.colorArmor(player.getPlayer(), color);
				
			}
			
			pg.color = color;
			
			i++;
			
		}
		
		for(Door door: map.getDoors())
			door.openDoor();
		
		map.getWorld().setDifficulty(Difficulty.PEACEFUL);
		
		Collections.shuffle(getTasks());
		
		for(i = tasksNum; i < getTasks().size(); i++)
			getTasks().get(i).disable();
		
		Collections.shuffle(this.players);
		
		tpToSpawn();
		
		for(i = 0; i < imposters; i++) {
			
			PlayerGame player = this.players.get(i);
			
			for(PlayerGame pg: this.players)
				if(!pg.impostor && chanceImposter.contains(pg.getPlayer().getName())) {
					
					player = pg;
					break;
					
				}
			
			player.impostor = true;
			player.timeoutKill = timeout_kill;
			player.timeoutBar = Bukkit.createBossBar(Messages.rollback, BarColor.YELLOW, BarStyle.SOLID);
			player.timeoutBar.addPlayer(player.getPlayer());
			impostersStr += " " + player.color.getChatColor() + player.getPlayer().getDisplayName();
			
		}
		
		Collections.shuffle(this.players);

		for(PlayerGame player: this.players) {
			
			player.getPlayer().setHealth(player.getPlayer().getMaxHealth());
			player.getPlayer().setSaturation((float)player.getPlayer().getMaxHealth());
			
			player.getPlayer().getInventory().setHeldItemSlot(0);
			
			player.getPlayer().setWalkSpeed(((float)(speed_player+1))/10);
			
			bar.addPlayer(player.getPlayer());
			
			teamPlayers.addPlayer(player.getPlayer());
			
			for(PotionEffect effect: player.getPlayer().getActivePotionEffects())
				player.getPlayer().removePotionEffect(effect.getType());
			
			if(player.impostor) {
				
				player.sendTitle("§4§l" + Messages.impostor, impostersStr);
				Kits.imposter(player.getPlayer());
				
			} else {
				
				player.sendTitle("§b§l" + Messages.crewmate, Messages.impostersNum.replace("@impostersNum@", "" + imposters));
				Kits.crewmate(player.getPlayer());
				
			}
			
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
			
			Kits.colorArmor(player.getPlayer(), player.color);
			
		}
		
		timerUpdate = Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {@Override public void run() {update();}}, 20, 20);
		
		return "true";
		
	}
	
	public Vote getVote() {
		
		return vote;
		
	}
	
	public List<PlayerGame> getLivePlayers() {
		
		List<PlayerGame> _players = new ArrayList<PlayerGame>();
		
		for(PlayerGame player: getPlayers())
			if(player.isLive())
				_players.add(player);
		
		return _players;
		
	}
	
	public void impostersWin() {
		
		for(PlayerGame player: players)
			if(player.impostor) {
				
				player.sendMessage("§2§l" + Messages.win);
				player.sendTitle("§2§l" + Messages.win, "");
				
			} else {
				
				player.sendMessage("§4§l" + Messages.lose);
				player.sendTitle("§4§l" + Messages.lose, "");
				
			}
		
		end("Impostor Win");
		
	}
	
	public void membersWin() {
		
		for(PlayerGame player: players)
			if(player.impostor) {
				
				player.sendMessage("§4§l" + Messages.lose);
				player.sendTitle("§4§l" + Messages.lose, "");
				
			} else {
				
				player.sendMessage("§2§l" + Messages.win);
				player.sendTitle("§2§l" + Messages.win, "");
				
			}
		
		end("Crewmate Win");
		
	}
	
	@SuppressWarnings("deprecation")
	public void end(String cause) {
		
		isStart = false;
		
		tpToSpawn();
		
		if(timerUpdate != null)
			timerUpdate.cancel();
		
		if(vote.isActive())
			vote.result();
		
		for(Sabotage sab: map.getSabotageTasks())
			sab.complete();
		for(Task task: getTasks())
			task.complete(false);
		for(PlayerGame player: map.getCameras().getPlayers())
			map.getCameras().exit(player);
		
		for(PlayerGame player: players) {
			
			for(PotionEffect effect: player.getPlayer().getActivePotionEffects())
				player.getPlayer().removePotionEffect(effect.getType());

			player.getPlayer().setWalkSpeed((float)0.2);
			player.getPlayer().getInventory().clear();
			
			if(player.impostor)
				player.timeoutBar.removeAll();
			
			for(PlayerGame _player: players)
				player.getPlayer().showPlayer(_player.getPlayer());
			
			player.sendMessage(impostersStr);
			
		}
		
		for(int id: killedBodies.keySet())
			sendPackets(Protocol.packetEntityDestroy(id));
		
		killedBodies.clear();
		
		bar.removeAll();
		
		GameEndEvent gameEnd = new GameEndEvent(this, cause);
		Bukkit.getPluginManager().callEvent(gameEnd);
		
	}
	
	private void update() {
		
		isStart = true;
		double completeTask = 0.0;
		for(Task task: getTasks())
			if(task.isComplete() && task.isEnable())
				completeTask++;
		
		bar.setProgress(completeTask/((double)tasksNum));
		
		if(completeTask+1 > getTasks().size())
			membersWin();
		
		int impostersNum = 0;
		int membersNum = 0;
		for(PlayerGame player: players)
			if(player.isLive() && player.impostor) {
				
				try {player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(impostersStr));} catch(Exception e) {}
				
				impostersNum++;
				
				if(player.timeoutKill > 0) {
					
					player.timeoutKill--;
					try {
						
						player.timeoutBar.setProgress(((double)player.timeoutKill) / ((double)timeout_kill));
						player.timeoutBar.setVisible(true);
						
					} catch (Exception e) {}
					
				} else {
					
					player.timeoutBar.setVisible(false);
					
				}
				
			}
		
		for(PlayerGame player: players)
			if(player.isLive() && !player.impostor)
				membersNum++;
		
		if(impostersNum+1 > membersNum)
			impostersWin();
		if(impostersNum == 0)
			membersWin();
		
		if(timeoutMeeting > 0)
			timeoutMeeting--;
		
		map.getWorld().setTime(16000);
		
	}
	
	public BossBar getBar() {
		
		return bar;
		
	}
	
	public PlayerGame getPlayer(Player player) {
		
		for(PlayerGame _player: players)
			if(_player.getPlayer() == player)
				return _player;
	
		return null;
		
	}
	
	public List<PlayerGame> getPlayers() {
		
		return players;
		
	}
	
	public List<Location> getKilledBodies() {
		
		List<Location> locBodies = new ArrayList<Location>();
		locBodies.addAll(killedBodies.values());
		
		return locBodies;
		
	}
	
	private void tpToSpawn() {
		
		for(int i = 0; i < players.size(); i++) {
			
			players.get(i).getPlayer().teleport(map.getSpawns().get(i));
			
			players.get(i).getPlayer().setGameMode(GameMode.SURVIVAL);
			
		}
		
	}
	
	public void meeting(PlayerGame player, boolean kill) {
		
		if(vote.isActive())
			return;
		
		if(!kill && emergency_metting-1 < emergencyMettingNum) {
			
			player.sendMessage("§c§o" + Messages.emergencyMeetingLimit);
			return;
			
		}
		
		if(!kill && timeoutMeeting > 0) {
			
			player.sendMessage("§c§o" + Messages.emergencyMeetingTimeout.replace("@timeout@", "" + timeoutMeeting));
			return;
			
		}
		
		for(Sabotage sab: map.getSabotageTasks()) {
			
			if(!kill && (sab instanceof SabotageReactor || sab instanceof SabotageOxygen) && sab.isActive()) {
				
				player.sendMessage("§c§o" + Messages.emergencyMeetingAndSabotage);
				return;
				
			}
			
			sab.complete();
			
		}
		
		for(Task task: getTasks())
			task.complete(false);
		
		for(PlayerGame _player: map.getCameras().getPlayers())
			map.getCameras().exit(_player);
		
		tpToSpawn();
		
		for(int id: killedBodies.keySet())
			sendPackets(Protocol.packetEntityDestroy(id));
		
		killedBodies.clear();
		
		vote.start();
		
		timeoutMeeting = timeout_metting + vote.timeVote;
		
		for(PlayerGame _player: players) {
			
			PlaySound.VOTE.play(_player.getPlayer());
			
			if(_player.isLive()) {
				
				if(_player.impostor)
					_player.timeoutKill = 15 + vote.timeVote;
				
				for(PotionEffect effect: _player.getPlayer().getActivePotionEffects())
					_player.getPlayer().removePotionEffect(effect.getType());
				
				_player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
				
			}
			
			if(kill) {
				
				_player.sendMessage("§c§l" + Messages.reportBody.replace("@player@", player.getPlayer().getName()));
				_player.sendTitle("§c§l" + Messages.reportBody.replace("@player@", player.getPlayer().getName()), "");
				
			} else {
				
				_player.sendMessage("§l" + Messages.emergencyMeeting.replace("@player@", player.getPlayer().getName()));
				_player.sendTitle("§l" + Messages.emergencyMeeting.replace("@player@", player.getPlayer().getName()), "");
				emergencyMettingNum++;
				
			}
			
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void sendPackets(Packet packet) {
		
		for(PlayerGame player: players)
			((CraftPlayer)player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
		
	}
	
	@SuppressWarnings("deprecation")
	public void killPlayer(PlayerGame player) {
		
		for(PlayerGame _player: players)
			_player.getPlayer().hidePlayer(player.getPlayer());
		
		player.kill();
		
	}
	
	public void imposterKillPlayer(PlayerGame player) {
		
		player.sendTitle("§c§l" + Messages.playerDied, "");
		
		int id = (int)Math.floor(Math.random() * Integer.MAX_VALUE);
		sendPackets(Protocol.packetNamedSpawnEntitySpawn(player.getPlayer(), id));
		
		killedBodies.put(id, player.getPlayer().getLocation());
		
		killPlayer(player);
		
		return;
		
	}
	
	public Task getTask(Location loc) {
		
		for(Task task: getTasks())
			if(task.getLocation().distance(loc) < 1 || task.getLocTo().distance(loc) < 1)
				return task;
		
		return null;
		
	}
	
	public Sabotage getSabotage(Location loc) {
		
		for(Sabotage sab: map.getSabotageTasks())
			if(sab.getLocTo().distance(loc) < 1)
				return sab;
			else
				for(Location _loc: sab.getLocation())
					if(_loc.distance(loc) < 1)
						return sab;
		
		return null;
		
	}
	
	public List<Sabotage> getSabotages() {
		
		return map.getSabotageTasks();
		
	}
	
	public List<Task> getTasks() {
		
		return map.getTasks();
		
	}
	
	public void create(FileConfiguration config, Location loc) {
		
		String mapName = config.getString("map");
		if(!MapManager.checkMap(mapName)) {
			
			Main.plugin.getLogger().warning(Messages.mapNotFound);
			for(PlayerGame player: this.players)
				player.sendMessage(Messages.mapNotFound);
			
			config = Config.getDefaultGameConfig();
			
		}
		
		for(Entity ent: loc.getWorld().getEntities())
			if(ent.getType() == EntityType.SNOWBALL)
				ent.remove();
		
		isStart = false;
		vote = new Vote(this);
		bar = Bukkit.createBossBar(Messages.tasks, BarColor.WHITE, BarStyle.SOLID);
		
		killedBodies.clear();
		this.players.clear();
		
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		for(Team team: scoreboard.getTeams())
			if(team.getName().equalsIgnoreCase("amPl" + loc.getWorld().getName()) || team.getName().equalsIgnoreCase("amImp" + loc.getWorld().getName()) || team.getName().equalsIgnoreCase("amTas" + loc.getWorld().getName()) || team.getName().equalsIgnoreCase("amSab" + loc.getWorld().getName()))
				team.unregister();
		
		teamPlayers = scoreboard.registerNewTeam("amPl" + loc.getWorld().getName());
		teamPlayers.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
		teamPlayers.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
		scoreboard.registerNewTeam("amSab" + loc.getWorld().getName());
		scoreboard.registerNewTeam("amTas" + loc.getWorld().getName());
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option amSab" + loc.getWorld().getName() + " color red");
		
		map = MapManager.init(mapName, this, config, loc.getWorld());
		
		imposters = config.getInt("imposters");
		confirm_eject = config.getBoolean("confirm_eject");
		emergency_metting = config.getInt("emergency_metting");
		timeout_metting = config.getInt("timeout_metting");
		time_voting = config.getInt("time_voting");
		speed_player = config.getInt("speed_player");
		timeout_kill = config.getInt("timeout_kill");
		distance_kill = config.getInt("distance_kill");
		tasksNum = config.getInt("tasksNum");
		visual_task = config.getBoolean("visual_task");
		playerOnCount = config.getInt("playerOnCount");
		
	}
	
	public MapGame getMap() {
		
		return map;
		
	}
	
	public void setMap(MapGame map) {
		
		this.map = map;
		
	}
	
	public void sabotage(String sabotageType) {
		
		Door door = null;
		if(sabotageType.indexOf("door") > -1)
			door = map.getDoor(sabotageType.split("door")[1]);
		
		if(door != null)
			door.closeDoor();
		else if(sabotageType.equalsIgnoreCase("reactor")) {
			for(Sabotage sabTask: map.getSabotageTasks())
				if(sabTask instanceof SabotageReactor)
					sabTask.start();
				
			}
		else if(sabotageType.equalsIgnoreCase("electrical"))
			for(Sabotage sabTask: map.getSabotageTasks()) {
				if(sabTask instanceof SabotageElectrical)
					sabTask.start();
				
			}
		else if(sabotageType.equalsIgnoreCase("oxygen"))
			for(Sabotage sabTask: map.getSabotageTasks()) {
				if(sabTask instanceof SabotageOxygen)
					sabTask.start();
				
			}
		else if(sabotageType.equalsIgnoreCase("communicate"))
			for(Sabotage sabTask: map.getSabotageTasks()) {
				if(sabTask instanceof SabotageCommunicate)
					sabTask.start();
				
			}
		
		
	}
	
}
