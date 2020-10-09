package amongUs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
		else if(args[0].equalsIgnoreCase("help"))
			help(sender);
		else 
			help(sender);
		
		return true;
		
	}
	
	
	private void list(CommandSender sender) {
		
		String str = "\n";
		
		for(Lobby lobby: Lobby.lobby)
			str += " - §b" + lobby.getName() + "§r;\n";
		
		sender.sendMessage(Main.tagPlugin + str + Main.tagPlugin);
		
	}
	
	private void setLobby(CommandSender sender, String[] args) {
		
		if(!sender.hasPermission("among.lobby")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите название");
			
			return;
			
		}
		
		Location loc = ((Player)sender).getLocation();
		
		Lobby lobby = Lobby.getLobby(args[1]);
		if(lobby == null)
			Lobby.lobby.add(new Lobby(loc, args[1]));
		else
			lobby.setLoc(loc);
		
		File file = new File(Main.plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		config.set(args[1] + ".location", loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		
		try {config.save(file);} catch (IOException e) {}
		
		sender.sendMessage(Main.tagPlugin + "Лобби создано");
		
	}

	private void leave(CommandSender sender) {
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		lobby.leave((Player)sender, false);
		
	}

	private void join(CommandSender sender, String[] args) {
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите лобби (/among list)");
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby(args[1]);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет такого лобби");
			
			return;
			
		}
		
		Lobby lobby2 = Lobby.getLobby((Player)sender);
		if(lobby2 != null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы уже в лобби");
			
			return;
			
		}
		
		lobby.join((Player)sender);
		
	}

	private void setSetting(CommandSender sender, String[] args) {
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.setting")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			
			return;
			
		}
		
		if(lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + "Игра уже идет");
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите настройку");
			
			return;
			
		}
		
		if(args.length == 2) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите значение");
			
			return;
			
		}
		
		Field setting = null;
		try {
			
			setting = lobby.getGame().getClass().getDeclaredField(args[1]);
			
		} catch (Exception e) {
			
			sender.sendMessage(Main.tagPlugin + "Нет такой настройки");
			return;
			
		}
		
		try {
			
			setting.set(lobby.getGame(), Boolean.parseBoolean(args[2]));
			
		} catch (Exception e) {
			
			try {
				
				setting.set(lobby.getGame(), Integer.parseInt(args[2]));
				lobby.reloadSb();
				sender.sendMessage(Main.tagPlugin + "Настройка изменена");
				
			} catch (Exception e1) {
				
				sender.sendMessage(Main.tagPlugin + "Неверное значение");
				
			}
			
		}
		
	}
	
	private void voteOpenInv(CommandSender sender) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не игрок");
			return;
			
		}
		
		Player player = (Player)sender;
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			return;
			
		}
		
		String answ = lobby.getGame().getVote().openInv(player);
		if(!answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void start(CommandSender sender) {
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.start")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет игры");
			
			return;
			
		}
		
		if(lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + "Игра уже идет");
			
			return;
			
		}
		
		String response = lobby.startGame();
		if(!response.equalsIgnoreCase("true")) {
			
			sender.sendMessage(Main.tagPlugin + response);
			return;
			
		}
		
	}
	
	private void create(CommandSender sender, String[] args) {
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.create")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(lobby.getGame() != null && lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + "Игра уже идет");
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите настройки игры(из папки AmongUs, пример game))");
			
			return;
			
		}
		
		try {
			
			File fileConfigGame = new File(Main.plugin.getDataFolder() + File.separator + "gameConfig" + File.separator + args[1] + ".yml");
			FileConfiguration configGame = (FileConfiguration) YamlConfiguration.loadConfiguration(fileConfigGame);
			
			lobby.createGame(configGame);
			
		} catch (Exception e) {
			
			sender.sendMessage(Main.tagPlugin + "Нет такого файла настроек");
			e.printStackTrace();
			
		}
		
	}
	
	private void vote(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не игрок");
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите ник или skip");
			return;
			
		}
		
		Player player = (Player)sender;
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			return;
			
		}
		
		if(args[1].equalsIgnoreCase("skip")) {
			
			String answ = lobby.getGame().getVote().skip(player);
			if(answ.equalsIgnoreCase("true"))
				sender.sendMessage(Main.tagPlugin + "§b§oВы проголосовали");
			else
				sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
			
			return;
			
		}
		
		String answ = lobby.getGame().getVote().vote(player, args[1]);
		if(answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§oВы проголосовали");
		else
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void help(CommandSender sender) {
		
		sender.sendMessage(Main.tagPlugin + "\n------------Команды------------\n"
						 + " /among create gameConf - §eсоздать игру§r\n"
						 + " /among start - §eначать игру§r\n"
						 + " /among v (nickName/skip) - §eголосовать§r\n"
						 + " /among help - §eэто меню§r\n"
						 + " /among vopen - §eоткрыть планшет голосования§r\n"
						 + " /among setting sett val - §eизменить наст-ку игры§r\n"
						 + " /among join name - §eвойти в лобби§r\n"
						 + " /among leave - §eвыйти из лобби§r\n"
						 + " /among setlobby name - §eсоздать лобби§r\n"
						 + " /among list - §eсписок лобби§r\n"
						 + "------------Команды------------");
		
	}

}
