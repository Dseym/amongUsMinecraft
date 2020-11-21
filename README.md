# amongUsMinecraft
Плагин. Играй в Среди Нас в майнкрафт! / Plugin. Play Among Us in Minecraft!

# ENG
## Description
New minecraft mini-game based on the canon of the game Among Us! Complete tasks, look for imposters, or kill if you are an imposter yourself!

### Updates 1.5
1. Added new values ​​to config.yml
2. Added map editing to mapConfig
3. Added scoreboard in game
4. Added textures
5. Added new messages in messages.yml
```
 error: "Произошла ошибка"
 imposters: "Самозванцев: "
 confirmEject: "Подтверждение: "
 visualTasks: "Визуальное: "
 time: "Время: "
 map: "Карта: "
 you: "Ты: "
 live: "Жив"
 kills: "Убийств: "
```
6. Fixed bugs
7. Minor changes

[Version 1.5](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/amongUs.jar)

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

## Change map settings(locations of tasks, sabotages, etc.)
In file AmongUs/mapConfig/example.yml (you can create your) [Download](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/mapConfig.yml)

## Messages
In file AmongUs/messages.yml [Download](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/messages.yml)

## Game config
In file AmongUs/gameConfig/example.yml (you can create your)
```
map: mapConfig
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
```

Video about the plugin (in Russian) - https://youtu.be/lHh4xp_ziro.

## Compile
1. Download the source code and upload it to Eclipse for example.
2. Add External JARs: [Server Core](https://getbukkit.org/get/Fpt2yFn7HRTrot5uE1b8NFWtpQlYITgK) 1.12.2.
3. Add External JARs: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Now you have the code that you can edit!

## Install for Server
1. Download the compiled [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/amongUs.jar) and upload it to your server.
2. Download [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) and upload it to your server.
3. Upload [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/world.tar) on server and unzip.
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

## Change map settings(locations of tasks, sabotages, etc.)
В файле AmongUs/mapConfig/example.yml (вы можете создать свой) [Скачать](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/mapConfig.yml)

## Сообщения
В файле AmongUs/messages.yml. [Скачать](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/messages.yml)

## Игровой конфиг(gameConfig)
В файле AmongUs/gameConfig/example.yml (вы можете создать свой)
```
map: mapConfig
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
```

Видео о плагине (на русском) - https://youtu.be/lHh4xp_ziro.

## Компиляция
1. Скачайте исходный код и загрузите, к примеру, в Eclipse.
2. Добавьте External Jars в проект: [Серверное ядро](https://getbukkit.org/get/Fpt2yFn7HRTrot5uE1b8NFWtpQlYITgK) 1.12.2.
3. Добавьте External Jars в проект: [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) 1.12.2.
4. Теперь у Вас есть код для редактирования!

## Установка на сервер
1. Скачайте скомпилированный [AmongUs](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/amongUs.jar) и загрузите на сервер.
2. Скачайте [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997) и загрузите на сервер.
3. Загрузите [world.tar](https://github.com/Dseym/amongUsMinecraft/releases/download/AmongUs1.5/world.tar) на сервер и распакуйте.
4. Веселись!