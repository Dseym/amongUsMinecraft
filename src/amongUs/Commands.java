package amongUs;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import game.Game;
import game.Lobby;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0)
			help(sender);
		else if(args[0].equalsIgnoreCase("start"))
			start(sender);
		else if(args[0].equalsIgnoreCase("create"))
			create(sender, args);
		else if(args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("vote"))
			vote(sender, args);
		else if(args[0].equalsIgnoreCase("vopen"))
			voteOpenInv(sender);
		else if(args[0].equalsIgnoreCase("setting"))
			setSetting(sender, args);
		else if(args[0].equalsIgnoreCase("join"))
			join(sender, args);
		else if(args[0].equalsIgnoreCase("leave"))
			leave(sender);
		else if(args[0].equalsIgnoreCase("setLobby"))
			setLobby(sender, args);
		else if(args[0].equalsIgnoreCase("list"))
			list(sender);
		else if(args[0].equalsIgnoreCase("joinNick"))
			joinNick(sender, args);
		else if(args[0].equalsIgnoreCase("help"))
			help(sender);
		else 
			help(sender);
		
		return true;
		
	}
	
	public boolean checkPerm(CommandSender sender, String perm) {
		
		if(!sender.hasPermission(perm)) {
			
			sender.sendMessage(Messages.notPerm);
			
			return false;
			
		}
		
		return true;
		
	}
	
	
	private void list(CommandSender sender) {
		
		String str = "\n";
		
		for(Lobby lobby: Lobby.lobby)
			str += " - §b" + lobby.getName() + "§r\n";
		
		sender.sendMessage(Main.tagPlugin + str + Main.tagPlugin);
		
	}
	
	private void setLobby(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		if(checkPerm(sender, "among.lobby")) return;
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		Location loc = ((Player)sender).getLocation();
		
		Lobby lobby = Lobby.getLobby(args[1]);
		if(lobby == null)
			Lobby.lobby.add(new Lobby(loc, args[1], Config.getDefaultGameConfig()));
		else
			lobby.setLoc(loc);
		
		String key = args[1] + ".location";
		String value = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
		
		Config.saveConfig("config", key, value);
		
		key = args[1] + ".defaultConfig";
		value = "example";
		
		Config.saveConfig("config", key, value);
		
		sender.sendMessage(Main.tagPlugin + Messages.success);
		
	}

	private void leave(CommandSender sender) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.plNotInLobby);
			
			return;
			
		}
		
		lobby.leave((Player)sender, false);
		
	}

	private void join(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby(args[1]);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lobbyNotFound);
			
			return;
			
		}
		
		lobby.join((Player)sender);
		
	}

	private void setSetting(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.plNotInLobby);
			
			return;
			
		}
		
		if(!sender.getName().equalsIgnoreCase(lobby.getOwner().getName()) && checkPerm(sender, "among.setting")) return;
		
		if(lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		if(args[1].equalsIgnoreCase("list")) {
			
			listSettings(sender, lobby);
			
			return;
			
		}
		
		if(!sender.hasPermission("among.setting") && !sender.hasPermission("among.setting")) {
			
			sender.sendMessage(Main.tagPlugin + Messages.notPerm);
			
			return;
			
		}
		
		if(args.length == 2) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		Field setting = null;
		try {
			
			setting = lobby.getGame().getClass().getDeclaredField(args[1]);
			
		} catch (Exception e) {
			
			sender.sendMessage(Main.tagPlugin + Messages.notFoundSett);
			return;
			
		}
		
		try {
			
			setting.set(lobby.getGame(), Boolean.parseBoolean(args[2]));
			
		} catch (Exception e) {
			
			try {
				
				setting.set(lobby.getGame(), Integer.parseInt(args[2]));
				lobby.reloadSb();
				sender.sendMessage(Main.tagPlugin + Messages.success);
				
			} catch (Exception e1) {
				
				sender.sendMessage(Main.tagPlugin + Messages.incorrectValue);
				
			}
			
		}
		
	}
	
	private void listSettings(CommandSender sender, Lobby lobby) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Game game = lobby.getGame();
		
		sender.sendMessage(Main.tagPlugin +
						   "\nimposters: " + game.imposters +
						   "\nconfirm_eject: " + game.confirm_eject +
						   "\nemergency_metting: " + game.emergency_metting +
						   "\ntimeout_metting: " + game.timeout_metting +
						   "\ntime_voting: " + game.time_voting +
						   "\nspeed_player: " + game.speed_player +
						   "\ntimeout_kill: " + game.timeout_kill +
						   "\ndistance_kill: " + game.distance_kill +
						   "\ntasksNum: " + game.tasksNum +
						   "\nvisual_task: " + game.visual_task +
						   "\n" + Main.tagPlugin
						  );
		
	}
	
	private void voteOpenInv(CommandSender sender) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Player player = (Player)sender;
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.plNotInLobby);
			
			return;
			
		}
		
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.notGame);
			return;
			
		}
		
		String answ = lobby.getGame().getVote().openInv(player);
		if(!answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void start(CommandSender sender) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.plNotInLobby);
			
			return;
			
		}
		
		if(!sender.getName().equalsIgnoreCase(lobby.getOwner().getName()) && checkPerm(sender, "among.start")) return;
		
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.notGame);
			
			return;
			
		}
		
		if(lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		String response = lobby.startGame();
		if(!response.equalsIgnoreCase("true")) {
			
			sender.sendMessage(Main.tagPlugin + response);
			return;
			
		}
		
	}
	
	private void create(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.plNotInLobby);
			
			return;
			
		}
		
		if(!sender.getName().equalsIgnoreCase(lobby.getOwner().getName()) && checkPerm(sender, "among.create")) return;
		
		if(lobby.getGame() != null && lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + Messages.isGameStart);
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		FileConfiguration configGame = Config.loadGameConfig(args[1]);
		if(configGame == null)
			sender.sendMessage(Main.tagPlugin + Messages.notFoundConfig);
		else
			lobby.createGame(configGame);
		
	}
	
	private void vote(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			return;
			
		}
		
		Player player = (Player)sender;
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lobbyNotFound);
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.notGame);
			return;
			
		}
		
		if(args[1].equalsIgnoreCase("skip")) {
			
			String answ = lobby.getGame().getVote().skip(player);
			if(answ.equalsIgnoreCase("true"))
				sender.sendMessage(Main.tagPlugin + "§b§o" + Messages.success);
			else
				sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
			
			return;
			
		}
		
		String answ = lobby.getGame().getVote().vote(player, args[1]);
		if(answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§o" + Messages.success);
		else
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void joinNick(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + Messages.senderNotPl);
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + Messages.lacksArgs);
			
			return;
			
		}
		
		Player p = Bukkit.getPlayer(args[1]);
		if(p == null || Lobby.getLobby(p) == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.playerNotFound);
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobbyForOwner(p);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + Messages.playerNotOwnerLobby);
			
			return;
			
		}
		
		Lobby.getLobbyForOwner(p).join((Player)sender);
		
	}
	
	private void help(CommandSender sender) {
		
		sender.sendMessage(Main.tagPlugin + Messages.helpMenu);
		
	}

}
