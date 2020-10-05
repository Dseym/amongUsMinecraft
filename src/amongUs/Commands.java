package amongUs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

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
			join(sender);
		else if(args[0].equalsIgnoreCase("leave"))
			leave(sender);
		else if(args[0].equalsIgnoreCase("setLobby"))
			setLobby(sender);
		else if(args[0].equalsIgnoreCase("help"))
			help(sender);
		else 
			help(sender);
		
		return true;
		
	}
	
	
	private void setLobby(CommandSender sender) {
		
		if(!sender.hasPermission("among.lobby")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		Location loc = ((Player)sender).getLocation();
		Lobby.lobby = new Lobby(loc);
		
		File file = new File(Main.plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		config.set("lobby.location", loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		
		try {config.save(file);} catch (IOException e) {}
		
		sender.sendMessage(Main.tagPlugin + "Лобби создано");
		
	}

	private void leave(CommandSender sender) {
		
		Lobby.lobby.leave((Player)sender, false);
		
	}

	private void join(CommandSender sender) {
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		Lobby.lobby.join((Player)sender);
		
	}

	private void setSetting(CommandSender sender, String[] args) {
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.setting")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame().isStart()) {
			
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
			
			setting = Lobby.lobby.getGame().getClass().getDeclaredField(args[1]);
			
		} catch (Exception e) {
			
			sender.sendMessage(Main.tagPlugin + "Нет такой настройки");
			return;
			
		}
		
		try {
			
			setting.set(Lobby.lobby.getGame(), Boolean.parseBoolean(args[2]));
			
		} catch (Exception e) {
			
			try {
				
				setting.set(Lobby.lobby.getGame(), Integer.parseInt(args[2]));
				Lobby.lobby.reloadSb();
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
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			return;
			
		}
		
		String answ = Lobby.lobby.getGame().getVote().openInv(player);
		if(!answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void start(CommandSender sender) {
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.start")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет игры");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + "Игра уже идет");
			
			return;
			
		}
		
		String response = Lobby.lobby.startGame();
		if(!response.equalsIgnoreCase("true")) {
			
			sender.sendMessage(Main.tagPlugin + response);
			return;
			
		}
		
		Main.protocollib.addPacketListener(new PacketAdapter(Main.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_HEAD_ROTATION, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_DESTROY, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.ENTITY_STATUS) {
			
			@Override
		    public void onPacketSending(PacketEvent event) {
				
				List<Integer> entityId = new ArrayList<Integer>();
				for(PlayerGame player: Lobby.lobby.getGame().getPlayers())
					if(player.getAction() != null)
						entityId.add(player.getPlayer().getEntityId());
				
				if(PacketType.Play.Server.ENTITY_DESTROY == event.getPacketType()) {
					
					int[] lastIds = (int[])event.getPacket().getIntegerArrays().read(0);
					
					int[] _ids = new int[((int[])event.getPacket().getIntegerArrays().read(0)).length];
					
					for(int i = 0; i < lastIds.length; i++)
						if(!entityId.contains(lastIds[i]))
							_ids[i] = lastIds[i];
					
					event.getPacket().getIntegerArrays().write(0, _ids);
					
					return;
					
				}
				
				if(entityId.contains(event.getPacket().getIntegers().read(0)))
					event.setCancelled(true);
				
		    }
			
		});
		
	}
	
	private void create(CommandSender sender, String[] args) {
		
		if(Lobby.lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Нет лобби");
			
			return;
			
		}
		
		if(!sender.hasPermission("among.create")) {
			
			sender.sendMessage(Main.tagPlugin + "Нет прав");
			
			return;
			
		}
		
		if(Lobby.lobby.getGame() != null && Lobby.lobby.getGame().isStart()) {
			
			sender.sendMessage(Main.tagPlugin + "Игра уже идет");
			
			return;
			
		}
		
		if(args.length == 1) {
			
			sender.sendMessage(Main.tagPlugin + "Укажите настройки игры(из папки AmongUs, пример game))");
			
			return;
			
		}
		
		Lobby lobby = Lobby.getLobby((Player)sender);
		if(lobby == null) {
			
			sender.sendMessage(Main.tagPlugin + "Вы не в лобби");
			
			return;
			
		}
		
		try {
			
			File fileConfigGame = new File(Main.plugin.getDataFolder() + File.separator + "gameConfig" + File.separator + args[1] + ".yml");
			FileConfiguration configGame = (FileConfiguration) YamlConfiguration.loadConfiguration(fileConfigGame);
			
			lobby.createGame(configGame);
			
		} catch (Exception e) {
			
			sender.sendMessage(Main.tagPlugin + "Нет такого файла настроек");
			
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
		
		if(Lobby.lobby.getGame() == null) {
			
			sender.sendMessage(Main.tagPlugin + "Сейчас нет игры");
			return;
			
		}
		
		if(args[1].equalsIgnoreCase("skip")) {
			
			String answ = Lobby.lobby.getGame().getVote().skip(player);
			if(answ.equalsIgnoreCase("true"))
				sender.sendMessage(Main.tagPlugin + "§b§oВы проголосовали");
			else
				sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
			
			return;
			
		}
		
		String answ = Lobby.lobby.getGame().getVote().vote(player, args[1]);
		if(answ.equalsIgnoreCase("true"))
			sender.sendMessage(Main.tagPlugin + "§b§oВы проголосовали");
		else
			sender.sendMessage(Main.tagPlugin + "§b§o" + answ);
		
	}
	
	private void help(CommandSender sender) {
		
		sender.sendMessage(Main.tagPlugin + "\n----------Команды----------\n"
						 + " /among create gameConf - §eсоздать игру§r\n"
						 + " /among start - §eначать игру§r\n"
						 + " /among v (nickName/skip) - §eголосовать§r\n"
						 + " /among help - §eэто меню§r\n"
						 + " /among vopen - §eоткрыть планшет голосования§r\n"
						 + " /among setting sett value - §eизменить настройку игры§r\n"
						 + " /among join - §eвойти в лобби§r\n"
						 + " /among leave - §eвыйти из лобби§r\n"
						 + " /among setlobby - §eсоздать лобби§r\n"
						 + "----------Команды----------");
		
	}

}
