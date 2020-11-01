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
		fewPlayer = "Нужно больше 2х игроков",
		fewCrewmate = "Предателей должно быть меньше экипажа",
		fewSpawnsOnMap = "На карте недостаточно места для всех игроков",
		mapNotFound = "Карта не найдена",
		gameCreated = "Создана игра для этого лобби",
		maxPlayers = "Максимум игроков",
		plJoinToLobby = "Вы вошли в лобби",
		plLeaveFromLobby = "Вы вышли из лобби",
		plJoinToLobbyMessPlayers = "Игрок §o@player@§r§e присоединился к лобби (@countPlayers@)",
		plLeaveFromLobbyMessPlayers = "Игрок §o@player@§r§e покинул лобби (@countPlayers@)",
		countToGame = "До начала игры: @time@",
		configMess = "Конфиг",
		plNotInGame = "Вы не в игре",
		playerNotFound = "Игрок не найден",
		plDied = "Игрок мертв",
		playerNotOwnerLobby = "Игрок не владелец лобби",
		owner = "Основатель",
		players = "Игроки",
		exit = "Выход",
		lobby = "Лобби",
		clickToJoin = "§4§oНажмите",
		manhole = "Люк",
		start = "Старт",
		helpMenu = "\n------------Команды------------\n"
				 + " /among create gameConf - §eсоздать игру§r\n"
				 + " /among start - §eначать игру§r\n"
				 + " /among v nick/skip - §eголосовать§r\n"
				 + " /among help - §eэто меню§r\n"
				 + " /among vopen - §eоткрыть планшет голосования§r\n"
				 + " /among setting sett val - §eизменить наст-ку игры§r\n"
				 + " /among join name - §eвойти в лобби§r\n"
				 + " /among leave - §eвыйти из лобби§r\n"
				 + " /among setlobby name - §eсоздать лобби§r\n"
				 + " /among list - §eсписок лобби§r\n"
				 + " /among setting list - §eпоказать настройки игры§r\n"
				 + " /among joinNick nick - §eзайти в лобби с указанным основателем§r\n"
				 + " /among impostor off/on nick - §eвыкл/вкл игроку 100% шанс самозванца§r\n"
				 + "------------Команды------------",
		
		visibleBody = "Рядом обнаружено тело",
		sabotageLimit = "Нельзя устраивать несколько саботажей",
		vote = "Голосование",
		report = "Репорт",
		rollback = "Откат",
		mapSabotage = "Карта саботажа",
		knife = "Нож",
		impostor = "Предатель",
		crewmate = "Член экипажа",
		impostersNum = "Обнаружено §4@impostersNum@ предателя§r среди вас",
		win = "Победа",
		lose = "Поражение",
		emergencyMeetingLimit = "Больше нельзя собираться",
		emergencyMeetingTimeout = "Экипаж должен подождать @timeout@ до след собрания",
		emergencyMeetingAndSabotage = "Во время экстренных ситуаций, собрания запрещены",
		emergencyMeeting = "Срочное собрание§r - от §o@player@",
		reportBody = "Сообщение о трупе§r - от §o@player@",
		playerDied = "Вас убили",
		tasks = "Задания",
		skipVote = "Пропустить",
		voteNotFound = "Сейчас нет голосования",
		youDied = "Вы мертвы",
		youYetVoted = "Вы уже проголосовали",
		voteSkipped = "Голосование скипнуто",
		notSingleDesition = "Мы не смогли принять единого решения",
		beImpostor = " был предателем",
		notBeImpostor = " не был предателем",
		beEject = " был изгнан",
		stepComplete = "Задание: §oэтап пройден",
		taskComplete = "Задание выполнено",
		taskTimeout = "Задание: §oтайм-аут",
		
		calibrateDistrTask = "Задание: §oпромах",
		cardSlowTask = "Задание: §oслишком медленно",
		cardFastTask = "Задание: §oслишком быстро",
		reactorTask = "Задание: §oневерно",
		communicateSabotage = "Саботаж коммун-го модуля",
		electricalSabotage = "Саботаж электричества",
		oxygenSabotage = "Саботаж кислорода",
		reactorSabotage = "Саботаж реактора",
		oxygenBar = "Кислород",
		reactorBar = "Реактор",
		oxygenCodeNotFull = "Введите код полностью",
		oxygenWrongNum = "Неверная цифра",
		unlockManifoldTask = "Задание: §oневерно",
		
		sabType_doorUpDrive = "Закрыть двери в верхнем движке",
		sabType_doorDownDrive = "Закрыть двери в нижнем движке",
		sabType_doorSecurity = "Закрыть двери в охране",
		sabType_doorMedbay = "Закрыть двери в медотсеке",
		sabType_doorElectrical = "Закрыть двери в электричестве",
		sabType_doorCafeteria = "Закрыть двери в столовой",
		sabType_doorStorage = "Закрыть двери в хранилище",
		sabType_reactor = "Дестабилизировать реактор",
		sabType_communicate = "Вывести из строя коммун-ый модуль",
		sabType_oxygen = "Повредить кислород",
		sabType_electrical = "Повредить проводку";
		
	
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
		if(config.contains("fewPlayer"))
			fewPlayer = config.getString("fewPlayer");
		if(config.contains("fewCrewmate"))
			fewCrewmate = config.getString("fewCrewmate");
		if(config.contains("fewSpawnsOnMap"))
			fewSpawnsOnMap = config.getString("fewSpawnsOnMap");
		if(config.contains("mapNotFound"))
			mapNotFound = config.getString("mapNotFound");
		if(config.contains("gameCreated"))
			gameCreated = config.getString("gameCreated");
		if(config.contains("maxPlayers"))
			maxPlayers = config.getString("maxPlayers");
		if(config.contains("plJoinToLobby"))
			plJoinToLobby = config.getString("plJoinToLobby");
		if(config.contains("plLeaveFromLobby"))
			plLeaveFromLobby = config.getString("plLeaveFromLobby");
		if(config.contains("plJoinToLobbyMessPlayers"))
			plJoinToLobbyMessPlayers = config.getString("plJoinToLobbyMessPlayers");
		if(config.contains("plLeaveFromLobbyMessPlayers"))
			plLeaveFromLobbyMessPlayers = config.getString("plLeaveFromLobbyMessPlayers");
		if(config.contains("countToGame"))
			countToGame = config.getString("countToGame");
		if(config.contains("configMess"))
			configMess = config.getString("configMess");
		if(config.contains("plNotInGame"))
			plNotInGame = config.getString("plNotInGame");
		if(config.contains("plDied"))
			plDied = config.getString("plDied");
		if(config.contains("vote"))
			vote = config.getString("vote");
		if(config.contains("report"))
			report = config.getString("report");
		if(config.contains("rollback"))
			rollback = config.getString("rollback");
		if(config.contains("mapSabotage"))
			mapSabotage = config.getString("mapSabotage");
		if(config.contains("knife"))
			knife = config.getString("knife");
		if(config.contains("impostor"))
			impostor = config.getString("impostor");
		if(config.contains("crewmate"))
			crewmate = config.getString("crewmate");
		if(config.contains("impostersNum"))
			impostersNum = config.getString("impostersNum");
		if(config.contains("win"))
			win = config.getString("win");
		if(config.contains("lose"))
			lose = config.getString("lose");
		if(config.contains("emergencyMeetingLimit"))
			emergencyMeetingLimit = config.getString("emergencyMeetingLimit");
		if(config.contains("emergencyMeetingTimeout"))
			emergencyMeetingTimeout = config.getString("emergencyMeetingTimeout");
		if(config.contains("emergencyMeetingAndSabotage"))
			emergencyMeetingAndSabotage = config.getString("emergencyMeetingAndSabotage");
		if(config.contains("emergencyMeeting"))
			emergencyMeeting = config.getString("emergencyMeeting");
		if(config.contains("reportBody"))
			reportBody = config.getString("reportBody");
		if(config.contains("playerDied"))
			playerDied = config.getString("playerDied");
		if(config.contains("tasks"))
			tasks = config.getString("tasks");
		if(config.contains("skipVote"))
			skipVote = config.getString("skipVote");
		if(config.contains("voteNotFound"))
			voteNotFound = config.getString("voteNotFound");
		if(config.contains("youDied"))
			youDied = config.getString("youDied");
		if(config.contains("youYetVoted"))
			youYetVoted = config.getString("youYetVoted");
		if(config.contains("voteSkipped"))
			voteSkipped = config.getString("voteSkipped");
		if(config.contains("notSingleDesition"))
			notSingleDesition = config.getString("notSingleDesition");
		if(config.contains("beImpostor"))
			beImpostor = config.getString("beImpostor");
		if(config.contains("notBeImpostor"))
			notBeImpostor = config.getString("notBeImpostor");
		if(config.contains("beEject"))
			beEject = config.getString("beEject");
		if(config.contains("stepComplete"))
			stepComplete = config.getString("stepComplete");
		if(config.contains("taskComplete"))
			taskComplete = config.getString("taskComplete");
		if(config.contains("taskTimeout"))
			taskTimeout = config.getString("taskTimeout");
		if(config.contains("calibrateDistrTask"))
			calibrateDistrTask = config.getString("calibrateDistrTask");
		if(config.contains("cardSlowTask"))
			cardSlowTask = config.getString("cardSlowTask");
		if(config.contains("cardFastTask"))
			cardFastTask = config.getString("cardFastTask");
		if(config.contains("reactorTask"))
			reactorTask = config.getString("reactorTask");
		if(config.contains("communicateSabotage"))
			communicateSabotage = config.getString("communicateSabotage");
		if(config.contains("electricalSabotage"))
			electricalSabotage = config.getString("electricalSabotage");
		if(config.contains("oxygenSabotage"))
			oxygenSabotage = config.getString("oxygenSabotage");
		if(config.contains("reactorSabotage"))
			reactorSabotage = config.getString("reactorSabotage");
		if(config.contains("oxygenBar"))
			oxygenBar = config.getString("oxygenBar");
		if(config.contains("reactorBar"))
			reactorBar = config.getString("reactorBar");
		if(config.contains("oxygenCodeNotFull"))
			oxygenCodeNotFull = config.getString("oxygenCodeNotFull");
		if(config.contains("oxygenWrongNum"))
			oxygenWrongNum = config.getString("oxygenWrongNum");
		if(config.contains("sabType_doorUpDrive"))
			sabType_doorUpDrive = config.getString("sabType_doorUpDrive");
		if(config.contains("sabType_doorDownDrive"))
			sabType_doorDownDrive = config.getString("sabType_doorDownDrive");
		if(config.contains("sabType_doorSecurity"))
			sabType_doorSecurity = config.getString("sabType_doorSecurity");
		if(config.contains("sabType_doorMedbay"))
			sabType_doorMedbay = config.getString("sabType_doorMedbay");
		if(config.contains("sabType_doorElectrical"))
			sabType_doorElectrical = config.getString("sabType_doorElectrical");
		if(config.contains("sabType_doorCafeteria"))
			sabType_doorCafeteria = config.getString("sabType_doorCafeteria");
		if(config.contains("sabType_doorStorage"))
			sabType_doorStorage = config.getString("sabType_doorStorage");
		if(config.contains("sabType_reactor"))
			sabType_reactor = config.getString("sabType_reactor");
		if(config.contains("sabType_communicate"))
			sabType_communicate = config.getString("sabType_communicate");
		if(config.contains("sabType_oxygen"))
			sabType_oxygen = config.getString("sabType_oxygen");
		if(config.contains("sabType_electrical"))
			sabType_electrical = config.getString("sabType_electrical");
		if(config.contains("playerNotOwnerLobby"))
			playerNotOwnerLobby = config.getString("playerNotOwnerLobby");
		if(config.contains("owner"))
			owner = config.getString("owner");
		if(config.contains("players"))
			players = config.getString("players");
		if(config.contains("exit"))
			exit = config.getString("exit");
		if(config.contains("lobby"))
			lobby = config.getString("lobby");
		if(config.contains("clickToJoin"))
			clickToJoin = config.getString("clickToJoin");
		if(config.contains("manhole"))
			manhole = config.getString("manhole");
		if(config.contains("start"))
			start = config.getString("start");
		
	}
	
}
