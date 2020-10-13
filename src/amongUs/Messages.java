package amongUs;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

	public static String
		notPerm = "Нет прав",
		lacksArgs = "Не хватает аргументов",
		success = "Успешно",
		plNotInLobby = "Вы не в лобби",
		plInLobby = "Вы уже в лобби",
		lobbyNotFound = "Лобби не найдено",
		notGame = "Нет игры",
		isGameStart = "Идет игра",
		notFoundSett = "Не найдена настройка",
		incorrectValue = "Неверное значение",
		senderNotPl = "Вы не игрок",
		notFoundConfig = "Нет такого файла настроек",
		helpMenu = "\n------------Команды------------\n"
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
				 + "------------Команды------------",
		
		visibleBody = "Рядом обнаружено тело",
		sabotageLimit = "Нельзя устраивать несколько саботажей";
	
	
	public static void init() {
		
		File fileConfig = new File(Main.plugin.getDataFolder() + File.separator + "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
		
		if(config.contains("notPerm"))
			notPerm = config.getString("notPerm");
		if(config.contains("lacksArgs"))
			lacksArgs = config.getString("lacksArgs");
		if(config.contains("success"))
			success = config.getString("success");
		if(config.contains("plNotInLobby"))
			plNotInLobby = config.getString("plNotInLobby");
		if(config.contains("plInLobby"))
			plInLobby = config.getString("plInLobby");
		if(config.contains("lobbyNotFound"))
			lobbyNotFound = config.getString("lobbyNotFound");
		if(config.contains("notGame"))
			notGame = config.getString("notGame");
		if(config.contains("isGameStart"))
			isGameStart = config.getString("isGameStart");
		if(config.contains("notFoundSett"))
			notFoundSett = config.getString("notFoundSett");
		if(config.contains("incorrectValue"))
			incorrectValue = config.getString("incorrectValue");
		if(config.contains("senderNotPl"))
			senderNotPl = config.getString("senderNotPl");
		if(config.contains("notFoundConfig"))
			notFoundConfig = config.getString("notFoundConfig");
		if(config.contains("helpMenu"))
			helpMenu = config.getString("helpMenu");
		if(config.contains("visibleBody"))
			visibleBody = config.getString("visibleBody");
		if(config.contains("sabotageLimit"))
			sabotageLimit = config.getString("sabotageLimit");
		
	}
	
}
