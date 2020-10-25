# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

### Updates 1.3
1. Added the ability to enter the lobby using signs
```
 Line1: [AmongUs]
 Line2: *nameLobby*
```
2. Added a line to messages.yml (playerNotOwnerLobby)
3. Added automatic game creation in the lobby
4. Added a lobby owner and joining the lobby by the owner's nickname (/among joinNick nick)
5. (For API) Added events for the beginning, end of the game, etc
6. Added colored armor to players
7. Fixed bug

[Version 1.3](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/amongUs.jar)

## Commands
/among help - get all commands

## Permission
1. among.start - start game
2. among.create - create game
3. among.setting - set settings game
4. among.setlobby - set lobby
5. among.command - send commands while playing

## Messages
In file AmongUs/messages.yml. [Download](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/messages.yml)

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
playerOnCount: 3
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
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/world.tar) on server and unzip.
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
В файле AmongUs/messages.yml. [Скачать](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/messages.yml)

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
playerOnCount: 3
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
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3/world.tar) на сервер и распакуйте.
4. Веселись!

Если нужен перевод на англиский, пишите в Issues.