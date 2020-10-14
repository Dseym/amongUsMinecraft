# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

### Updates 1.2
1. The messages.yml config has been partially added
2. Added game autostart
3. Bug fixes

## Commands
/among help - get all commands

## Permission
1. among.start - start game
2. among.create - create game
3. among.setting - set settings game
4. among.setlobby - set lobby
5. among.command - send commands while playing

## Messages
In file AmongUs/messages.yml (will be replenished)
```
notPerm: "Нет прав"
lacksArgs: "Не хватает аргументов"
success: "Успешно"
plNotInLobby: "Вы не в лобби"
plInLobby: "Вы уже в лобби"
lobbyNotFound: "Лобби не найдено"
notGame: "Нет игры"
isGameStart: "Идет игра"
notFoundSett: "Не найдена настройка"
incorrectValue: "Неверное значение"
senderNotPl: "Вы не игрок"
notFoundConfig: "Нет такого файла настроек"
helpMenu: "\n------------Команды------------\n /among create gameConf - §eсоздать игру§r\n /among start - §eначать игру§r\n /among v (nickName/skip) - §eголосовать§r\n /among help - §eэто меню§r\n /among vopen - §eоткрыть планшет голосования§r\n /among setting sett val - §eизменить наст-ку игры§r\n /among join name - §eвойти в лобби§r\n /among leave - §eвыйти из лобби§r\n /among setlobby name - §eсоздать лобби§r\n /among list - §eсписок лобби§r\n ------------Команды------------"

visibleBody: "Рядом обнаружено тело"
sabotageLimit: "Нельзя устраивать несколько саботажей"
```

## Game config
In file AmongUs/gameConfig/example.yml (you can create your)
```
map: The Skeld
visual_task: true
confirm_eject: true
imposters: 1
emergency_metting: 1
timeout_metting: 10
time_voting: 60
speed_player: 2
timeout_kill: 15
distance_kill: 2
tasksNum: 16
spawns:
- 0.5, 98, 3.5
- 2.5, 98, 2.5
- 3.5, 98, 1.5
- 3.5, 98, -0.5
- 2.5, 98, -1.5
- 0.5, 98, -2.5
- -1.5, 98, -1.5
- -2.5, 98, -0.5
- -2.5, 98, 1.5
- -1.5, 98, 2.5
```

Video about the plugin (in Russian) - https://youtu.be/lHh4xp_ziro.

## Compile
1. Download the source code and upload it to Eclipse for example.
2. Add External JARs: [Server Core](https://getbukkit.org/get/Fpt2yFn7HRTrot5uE1b8NFWtpQlYITgK) 1.12.2.
3. Add External JARs: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Now you have the code that you can edit!

## Install for Server
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.2/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.2/world.tar) on server and unzip.
4. Have fun!

If you need to translate the plugin into English, then write to Issues.

# RUS
## Описание
Новая мини-игра в майнкрафт, основанная по канону игры Among Us! Выполняй задания, ищи imposters, или убивай если ты сам imposter!

## Команды
/among help - получить все команды

## Permission
1. among.start - начать игру
2. among.create - создать игру
3. among.setting - установить настройку игры
4. among.setlobby - установить лобби
5. among.command - отправлять команды во время игры

## Сообщения
В файле AmongUs/messages.yml (будет пополняться)
```
notPerm: "Нет прав"
lacksArgs: "Не хватает аргументов"
success: "Успешно"
plNotInLobby: "Вы не в лобби"
plInLobby: "Вы уже в лобби"
lobbyNotFound: "Лобби не найдено"
notGame: "Нет игры"
isGameStart: "Идет игра"
notFoundSett: "Не найдена настройка"
incorrectValue: "Неверное значение"
senderNotPl: "Вы не игрок"
notFoundConfig: "Нет такого файла настроек"
helpMenu: "\n------------Команды------------\n /among create gameConf - §eсоздать игру§r\n /among start - §eначать игру§r\n /among v (nickName/skip) - §eголосовать§r\n /among help - §eэто меню§r\n /among vopen - §eоткрыть планшет голосования§r\n /among setting sett val - §eизменить наст-ку игры§r\n /among join name - §eвойти в лобби§r\n /among leave - §eвыйти из лобби§r\n /among setlobby name - §eсоздать лобби§r\n /among list - §eсписок лобби§r\n ------------Команды------------"

visibleBody: "Рядом обнаружено тело"
sabotageLimit: "Нельзя устраивать несколько саботажей"
```

## Игровой конфиг(gameConfig)
В файле AmongUs/gameConfig/example.yml (вы можете создать свой)
```
map: The Skeld
visual_task: true
confirm_eject: true
imposters: 1
emergency_metting: 1
timeout_metting: 10
time_voting: 60
speed_player: 2
timeout_kill: 15
distance_kill: 2
tasksNum: 16
spawns:
- 0.5, 98, 3.5
- 2.5, 98, 2.5
- 3.5, 98, 1.5
- 3.5, 98, -0.5
- 2.5, 98, -1.5
- 0.5, 98, -2.5
- -1.5, 98, -1.5
- -2.5, 98, -0.5
- -2.5, 98, 1.5
- -1.5, 98, 2.5
```

Видео о плагине (на русском) - https://youtu.be/lHh4xp_ziro.

## Компиляция
1. Скачайте исходный код и загрузите, к примеру, в Eclipse.
2. Добавьте External Jars в проект: [Серверное ядро](https://getbukkit.org/get/Fpt2yFn7HRTrot5uE1b8NFWtpQlYITgK) 1.12.2.
3. Добавьте External Jars в проект: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Теперь у Вас есть код для редактирования!

## Установка на сервер
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.2/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.2/world.tar) на сервер и распакуйте.
4. Веселись!

Если нужен перевод на англиский, пишите в Issues.