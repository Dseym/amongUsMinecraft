# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

### Updates 1.4
1. Some added to the lobby
2. Recode manholes
3. Added new messages for messages.yml
```
 exit: "Выход"
 lobby: "Лобби"
 clickToJoin: "§4§oНажмите"
 manhole: "Люк"
 start: "Старт"
```
4. Added new command (/among impostor off/on nick - 100% chance to give the player an impostor)
5. Added some sounds
6. Fixed many bugs

[Version 1.4](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/amongUs.jar)

## Commands
/among help - get all commands

## Permission
1. among.start - /among start (for owner lobby)
2. among.create - /among create (for owner lobby)
3. among.setting - /among setting (for owner lobby)
4. among.setlobby - /among setlobby
5. among.command - send commands while playing
6. among.impostor - /among impostor
7. among.signs - creating signs

## Signs
Signs for entering the lobby. Example:
```
 Line1: [AmongUs]
 Line2: *nameLobby*
```

## ATTENTION!!!
1. The lobby and the map must be in the same world!
2. Don't create multiple lobbies in the same world!

## Change map settings(locations of tasks, sabotages, etc.)
~~The tutorial is still being finalized~~ - [Tutorial](https://github.com/Dseym/amongUsMinecraft/wiki) (There will be no tutorial! I'm making a config for maps!)

## Messages
In file AmongUs/messages.yml. [Download](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/messages.yml)

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
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/world.tar) on server and unzip.
4. Have fun!

# RUS
## Описание
Новая мини-игра в майнкрафт, основанная по канону игры Among Us! Выполняй задания, ищи imposters, или убивай если ты сам imposter!

## Команды
/among help - получить все команды

## Права
1. among.start - /among start (для основателя лобби)
2. among.create - /among create (для основателя лобби)
3. among.setting - /among setting (для основателя лобби)
4. among.setlobby - /among setlobby
5. among.command - отправлять команды во время игры
6. among.impostor - /among impostor
7. among.signs - создание табличек

## Signs
Таблички для входа в лобби. Пример:
```
 Line1: [AmongUs]
 Line2: *название_лобби*
```

## ATTENTION!!!
1. Лобби и карта должны быть в одном мире!
2. Не создавайте в одном мире несколько лобби!

## Change map settings(locations of tasks, sabotages, etc.)
~~Туториал еще дорабатывается~~ - [Туториал](https://github.com/Dseym/amongUsMinecraft/wiki) (Туториала не будет! Я делаю конфиг для карт!)

## Сообщения
В файле AmongUs/messages.yml. [Скачать](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/messages.yml)

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
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.4/world.tar) на сервер и распакуйте.
4. Веселись!