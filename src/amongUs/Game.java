package amongUs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatMessageType;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import tasks.Sabotage;
import tasks.Task;
import tasks.list.SabotageCommunicate;
import tasks.list.SabotageElectrical;
import tasks.list.SabotageOxygen;
import tasks.list.SabotageReactor;

public class Game {
	
	protected boolean confirm_eject = false;
	protected int emergency_metting = 1;
	protected int timeout_metting = 15;
	protected int time_voting = 60;
	protected int speed_player = 1;
	protected int timeout_kill = 15;
	protected int distance_kill = 2;
	protected int tasksNum = 5;
	protected boolean visual_task = true;
	protected int imposters = 1;
	
	private int emergencyMettingNum = 0;
	private List<PlayerGame> players = new ArrayList<PlayerGame>();
	private boolean isStart = false;
	private MapGame map;
	private Map<Integer, Location> killedBodies = new HashMap<Integer,Location>();
	private BossBar bar;
	private Team teamImposters;
	private Vote vote;
	public int timeoutMeeting = 15;
	private Team teamPlayers;
	public List<Integer> entityId = new ArrayList<Integer>();
	private Lobby lobby;
	private BukkitTask timerUpdate;
	
	private String impostersStr = "§4 ";

	public Game(FileConfiguration config, Lobby lobby) {
		
		this.lobby = lobby;
		reload(config);
		
	}
	
	public boolean isStart() {
		
		return isStart;
		
	}
	
	@SuppressWarnings("deprecation")
	public String start(List<Player> players) {
		
		if(players == null || players.size() < 3)
			return "Нужно больше 2х игроков";
		
		if(emergency_metting < 1)
			return "emergency_metting - не может быть меньше 0";
		
		if(time_voting < 1)
			return "time_voting - не может быть меньше 1";
		
		if(speed_player < 1)
			return "speed_player - не может быть меньше 1";
		
		if(timeout_kill < 1)
			return "timeout_kill - не может быть меньше 0";
		
		if(distance_kill < 1)
			return "distance_kill - не может быть меньше 1";
		
		if(tasksNum < 1)
			return "tasksNum - не может быть меньше 1";
		
		if(timeout_metting < 1)
			return "timeout_metting - не может быть меньше 0";

		if(imposters < 1)
			return "Предателей не может быть меньше 1";
	
		if(imposters > players.size()-imposters-1)
			return "Предателей должно быть меньше экипажа";
		
		if(map.getSpawns().size() < players.size())
			return "На карте недостаточно места для всех игроков";
		
		for(Player player: players)
			this.players.add(new PlayerGame(player));
		
		for(Door door: map.getDoors())
			door.openDoor();
		
		tpToSpawn();
		
		map.getWorld().setDifficulty(Difficulty.PEACEFUL);
		
		if(getTasks().size() < tasksNum) {
			
			Main.plugin.getLogger().warning("На карте только " + getTasks().size() + " заданий");
			for(PlayerGame player: this.players)
				player.sendMessage("На карте только §o" + getTasks().size() + "§r заданий");
			
			tasksNum = getTasks().size();
			
		}
		
		Collections.shuffle(getTasks());
		
		for(int i = tasksNum; i < getTasks().size(); i++)
			getTasks().get(i).disable();
		
		for(PlayerGame player: this.players) {
			
			player.getPlayer().getInventory().clear();
			
			ItemStack item = new org.bukkit.inventory.ItemStack(Material.BANNER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Голосование");
			item.setItemMeta(meta);
			
			player.getPlayer().getInventory().setItem(8, item);
			player.getPlayer().setHealth(player.getPlayer().getMaxHealth());
			player.getPlayer().setSaturation((float)player.getPlayer().getMaxHealth());
			
		}
		
		for(int i = 0; i < imposters; i++) {
			
			PlayerGame player = this.players.get(i);
			player.impostor = true;
			player.timeoutKill = timeout_kill;
			player.timeoutBar = Bukkit.createBossBar("Откат", BarColor.YELLOW, BarStyle.SOLID);
			player.timeoutBar.addPlayer(player.getPlayer());
			impostersStr += " " + player.getPlayer().getDisplayName();
			player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(impostersStr));
			
			Map<String, String> sabotageTypes = map.getSabotageTypes();
			int num = 0;
			for(String str: sabotageTypes.keySet()) {
				
				ItemStack item = new ItemStack(str.indexOf("door") < 0 ? Material.REDSTONE_BLOCK : Material.IRON_DOOR, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				
				meta.setDisplayName("Sabotage");
				lore.add(sabotageTypes.get(str));
				lore.add("ID: " + str);
				
				meta.setLore(lore);
				item.setItemMeta(meta);
				
				player.getPlayer().getInventory().setItem(num+9, item);
				
				num++;
				
			}
			
		}
		
		for(PlayerGame player: this.players) {
			
			if(speed_player > 9) {
				
				Main.plugin.getLogger().warning("Максимальная скорость игроков 9");
				for(PlayerGame _player: this.players)
					_player.sendMessage("Максимальная скорость игроков 9");
				
				speed_player = 9;
				
			}
			
			player.getPlayer().setWalkSpeed(((float)(speed_player+1))/10);
			
			bar.addPlayer(player.getPlayer());
			
			teamPlayers.addEntry(player.getPlayer().getUniqueId().toString());
			
			for(PotionEffect effect: player.getPlayer().getActivePotionEffects())
				player.getPlayer().removePotionEffect(effect.getType());
			
			if(player.impostor) {
				
				player.sendTitle("§4§lПредатель", impostersStr);
				teamImposters.addEntry(player.getPlayer().getName());
				
			} else {
				
				player.sendTitle("§b§lЧлен экипажа", "Обнаружено §4" + imposters + " предателя§r среди вас");
				
			}
			
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
			
		}
		
		isStart = true;		
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
				
				player.sendMessage("§2§lПобеда");
				player.sendTitle("§2§lПобеда", "");
				
			} else {
				
				player.sendMessage("§4§lПоражение");
				player.sendTitle("§4§lПоражение", "");
				
			}
		
		end();
		
	}
	
	public void membersWin() {
		
		for(PlayerGame player: players)
			if(player.impostor) {
				
				player.sendMessage("§4§lПоражение");
				player.sendTitle("§4§lПоражение", "");
				
			} else {
				
				player.sendMessage("§2§lПобеда");
				player.sendTitle("§2§lПобеда", "");
				
			}
		
		end();
		
	}
	
	@SuppressWarnings("deprecation")
	public void end() {
		
		tpToSpawn();
		
		lobby.isGameStop();
		if(timerUpdate != null)
			timerUpdate.cancel();
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option amongImposters color red");
		
		for(Sabotage sab: map.getSabotageTasks())
			sab.complete();
		for(Task task: getTasks())
			task.complete(false);
		
		for(PlayerGame player: players) {
			
			player.getPlayer().setWalkSpeed((float)0.2);
			player.getPlayer().getInventory().clear();
			
			if(player.impostor)
				player.timeoutBar.removeAll();
			
			for(PlayerGame _player: players)
				player.getPlayer().showPlayer(_player.getPlayer());
			
		}
		
		if(vote.isActive())
			vote.result();
		
		for(PlayerGame player: map.getCameras().getPlayers())
			map.getCameras().exit(player);
		
		teamImposters.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
		for(Entity ent: map.getWorld().getEntities())
			if(ent.getType() == EntityType.SNOWBALL)
				ent.remove();
		
		for(int id: killedBodies.keySet()) {
			
			PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
			
			sendPackets(destroy);
			
		}
		
		killedBodies.clear();
		
		bar.removeAll();
		
	}
	
	public Lobby getLobby() {
		
		return lobby;
		
	}
	
	public void update() {
		
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
				
				player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(impostersStr));
				
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
			
			player.sendMessage("§c§oБольше нельзя собираться");
			return;
			
		}
		
		if(!kill && timeoutMeeting > 0) {
			
			player.sendMessage("§c§oЭкипаж должен подождать " + timeoutMeeting + " до след собрания");
			return;
			
		}
		
		for(Sabotage sab: map.getSabotageTasks()) {
			
			if(!kill && (sab instanceof SabotageReactor || sab instanceof SabotageOxygen) && sab.isActive()) {
				
				player.sendMessage("§c§oВо время экстренных ситуаций, собрания запрещены");
				return;
				
			}
			
			sab.complete();
			
		}
		
		for(Task task: getTasks())
			task.complete(false);
		
		for(PlayerGame _player: map.getCameras().getPlayers())
			map.getCameras().exit(_player);
		
		tpToSpawn();
		
		for(int id: killedBodies.keySet()) {
			
			PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
			
			sendPackets(destroy);
			
		}
		
		killedBodies.clear();
		
		vote.start();
		
		timeoutMeeting = timeout_metting + vote.timeVote;
		
		for(PlayerGame _player: players) {
			
			if(_player.isLive()) {
				
				if(_player.impostor)
					_player.timeoutKill = 15 + vote.timeVote;
				
				for(PotionEffect effect: _player.getPlayer().getActivePotionEffects())
					_player.getPlayer().removePotionEffect(effect.getType());
				
				_player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
				
			}
			
			if(kill) {
				
				_player.sendMessage("§c§lСообщение о трупе§r - от §o" + player.getPlayer().getName());
				_player.sendTitle("§c§lСообщение о трупе", "");
				
			} else {
				
				_player.sendMessage("§lСрочное собрание§r - от §o" + player.getPlayer().getName());
				_player.sendTitle("§lСрочное собрание", "");
				emergencyMettingNum++;
				
			}
			
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void sendPackets(Packet packet) {
		
		for(PlayerGame player: players)
			((CraftPlayer)player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
		
	}
	
	public void setValue(Object packet, String name, Object value) {
		
		try {
			
			Field field = packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(packet, value);
			
		} catch (Exception e) {}
		
	}
	
	@SuppressWarnings("deprecation")
	public void killPlayer(PlayerGame player) {
		
		for(PlayerGame _player: players)
			_player.getPlayer().hidePlayer(player.getPlayer());
		
		player.kill();
		
	}
	
	public void imposterKillPlayer(PlayerGame player) {
		
		player.sendTitle("§c§lВас убили", "");
		
		PacketPlayOutNamedEntitySpawn body = new PacketPlayOutNamedEntitySpawn(((CraftPlayer)player.getPlayer()).getHandle());
		int id = (int)Math.floor(Math.random() * Integer.MAX_VALUE);
		
		Location loc = player.getPlayer().getLocation();
		setValue(body, "a", id);
		setValue(body, "d", loc.getY()-1.43);
		setValue(body, "f", (byte)0);
		setValue(body, "g", (byte)-90);
	
		sendPackets(body);
		
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
	
	public void reload(FileConfiguration config) {
		
		isStart = false;
		
		vote = new Vote(this);
		bar = Bukkit.createBossBar("Задания", BarColor.WHITE, BarStyle.SOLID);
		
		killedBodies.clear();
		this.players.clear();
		
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		for(Team team: scoreboard.getTeams())
			if(team.getName().equalsIgnoreCase("amongPlayers") || team.getName().equalsIgnoreCase("amongImposters") || team.getName().equalsIgnoreCase("amongTasks") || team.getName().equalsIgnoreCase("amongSabotage"))
				team.unregister();
		
		teamPlayers = scoreboard.registerNewTeam("amongPlayers");
		teamImposters = scoreboard.registerNewTeam("amongImposters");
		scoreboard.registerNewTeam("amongSabotage");
		scoreboard.registerNewTeam("amongTasks");
		
		String mapName = config.getString("map");
		
		map = MapManager.initMap(mapName, this);
		
		if(map == null) {
			
			Main.plugin.getLogger().warning("Карта " + mapName + " не найдена");
			for(PlayerGame player: this.players)
				player.sendMessage("Карта §o" + mapName + "§r не найдена");
			
			return;
			
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option amongSabotage color red");
		
		teamPlayers.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
		
		for(int i = 0; i < Math.floor(Math.random() * 3); i++) {
			
			Collections.shuffle(this.players);
			Collections.shuffle(map.getSpawns());
			
		}
		
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
