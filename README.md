# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

### Updates
1. Added a multi-game
2. Changed sabotage menu
3. Changed mechanics killing and reporting
4. Added a change in the spawn of players during the meeting(in gameConf)
5. Changed sabotage oxygen
6. Bug fixes

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

## Commands
/among help - get all commands

## Permission
1. among.start - start game
2. among.create - create game
3. among.setting - set settings game
4. among.setlobby - set lobby
5. among.command - send commands while playing

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
2. Add External JARs: [Server Core](https://getbukkit.org/download/craftbukkit) 1.12.2.
3. Add External JARs: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Now you have the code that you can edit!

## Install for Server
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.1/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.1/world.tar) on server and unzip.
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
2. Добавьте External Jars в проект: [Серверное ядро](https://getbukkit.org/download/craftbukkit) 1.12.2.
3. Добавьте External Jars в проект: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Теперь у Вас есть код для редактирования!

## Установка на сервер
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.1/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.1/world.tar) на сервер и распакуйте.
4. Веселись!

Если нужен перевод на англиский, пишите в Issues.