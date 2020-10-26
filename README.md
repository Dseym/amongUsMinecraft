# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

### Updates 1.3.1
1. Added choice of default lobby to config
```
 lobby:
  location: ...
  defaultConfig: example
```
2. Added messages to messages.yml (owner and players)
3. Added 100% chance to become an impostor (among.impostor)
4. Fixed errors

[Version 1.3.1](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/amongUs.jar)

## Commands
/among help - get all commands

## Permission
1. among.start - start game
2. among.create - create game
3. among.setting - set settings game
4. among.setlobby - set lobby
5. among.command - send commands while playing
6. among.impostor - 100% chance to become an impostor

## Signs
Signs for entering the lobby. Example:
```
 Line1: [AmongUs]
 Line2: *nameLobby*
```

## Change map settings(locations of tasks, sabotages, etc.)
The tutorial is still being finalized - [Tutorial](https://github.com/Dseym/amongUsMinecraft/wiki)

## Messages
In file AmongUs/messages.yml. [Download](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/messages.yml)

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
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/world.tar) on server and unzip.
4. Have fun!

If you need to translate the plugin into English, then write to Issues.

# RUS
## Описание
Новая мини-игра в майнкрафт, основанная по канону игры Among Us! Выполняй задания, ищи imposters, или убивай если ты сам imposter!

## Команды
/among help - получить все команды

## Права
1. among.start - начать игру
2. among.create - создать игру
3. among.setting - установить настройку игры
4. among.setlobby - установить лобби
5. among.command - отправлять команды во время игры
6. among.impostor - 100% шанс стать самозванцем

## Signs
Таблички для входа в лобби. Пример:
```
 Line1: [AmongUs]
 Line2: *название_лобби*
```

## Change map settings(locations of tasks, sabotages, etc.)
Туториал еще дорабатывается - [Туториал](https://github.com/Dseym/amongUsMinecraft/wiki)

## Сообщения
В файле AmongUs/messages.yml. [Скачать](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/messages.yml)

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
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.3.1/world.tar) на сервер и распакуйте.
4. Веселись!

Если нужен перевод на англиский, пишите в Issues.