package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import amongUs.Messages;
import sabotages.Sabotage;
import sabotages.SabotageCommunicate;
import sabotages.SabotageElectrical;
import sabotages.SabotageOxygen;
import sabotages.SabotageReactor;
import tasks.CalibrateDistr;
import tasks.Card;
import tasks.ClearOxygen;
import tasks.Energy;
import tasks.Meteor;
import tasks.Rabbish;
import tasks.Reactor;
import tasks.Refuel;
import tasks.Scan;
import tasks.Shield;
import tasks.ShipWay;
import tasks.StabilizeDrive;
import tasks.StabilizeWay;
import tasks.Task;
import tasks.UnlockManifold;
import tasks.Upload_Download;
import tasks.Wiring;

public class MapManager {
	
	public static World world;
	
	public static boolean checkMap(String name) {
		
		if(name.equalsIgnoreCase("The Skeld"))
			return true;
		else
			return false;
		
	}

	public static MapGame init(String name, Game game, FileConfiguration config, World _world) {
		
		if(checkMap(name))
			world = _world;
		else
			return null;
		
		Location emergencyMetting = new Location(world, 0.5, 99, 0.5);
		List<Location> spawns = initSpawns(config);
		List<Task> tasks = initTasks(game);
		tasks.addAll(initLongTasks(game));
		List<Manhole> manholes = initManholes();
		List<Door> doors = initDoors();
		Map<String, String> sabotageTypes = initSabotageTypes();
		List<Sabotage> sabotageTasks = initSabotage(game);
		
		List<Location> cameras = new ArrayList<Location>();
		cameras.addAll(Arrays.asList(new Location(world, -0.5, 101, 12.5, -6, 53), new Location(world, 17.5, 105, 42.5, -40, 77), new Location(world, 20.5, 102, 1.5, 180, 65), new Location(world, 14, 105, -35.5, 0, 55)));
		Cameras camera = new Cameras(new Location(world, 12, 99, 30), cameras, game);
		
		return new MapGame(emergencyMetting, spawns, tasks, manholes, doors, sabotageTypes, sabotageTasks, camera);
		
	}
	
	private static List<Location> initSpawns(FileConfiguration config) {
		
		List<Location> spawns = new ArrayList<Location>();
		
		for(String strLoc: config.getStringList("spawns")) {
			
			Location loc = new Location(world, Double.parseDouble(strLoc.split(", ")[0]), Double.parseDouble(strLoc.split(", ")[1]), Double.parseDouble(strLoc.split(", ")[2]));
			
			spawns.add(loc);
			
		}
		
		return spawns;
		
	}
	
	private static Map<String, String> initSabotageTypes() {
		
		Map<String, String> sabotageTypes = new HashMap<String, String>();
		
		sabotageTypes.put("doorUpDrive", Messages.sabType_doorUpDrive);
		sabotageTypes.put("doorDownDrive", Messages.sabType_doorDownDrive);
		sabotageTypes.put("doorSecurity", Messages.sabType_doorSecurity);
		sabotageTypes.put("doorMedbay", Messages.sabType_doorMedbay);
		sabotageTypes.put("doorElectrical", Messages.sabType_doorElectrical);
		sabotageTypes.put("doorCafeteria", Messages.sabType_doorCafeteria);
		sabotageTypes.put("doorStorage", Messages.sabType_doorStorage);
		
		sabotageTypes.put("reactor", Messages.sabType_reactor);
		sabotageTypes.put("communicate", Messages.sabType_communicate);
		sabotageTypes.put("oxygen", Messages.sabType_oxygen);
		sabotageTypes.put("electrical", Messages.sabType_electrical);
		
		return sabotageTypes;
		
	}
	
	private static List<Sabotage> initSabotage(Game game) {
		
		List<Sabotage> sabotageTasks = new ArrayList<Sabotage>();
		
		List<Location> loc = new ArrayList<Location>();
		loc.addAll(Arrays.asList(new Location(world, 30, 99, 58), new Location(world, 7, 99, 57)));
		sabotageTasks.add(new SabotageReactor(loc, new Location(world, -17, 50, 20), game, new Location(world, -19, 51, 20), new Location(world, -21, 51, 20)));
		
		loc = new ArrayList<Location>();
		loc.addAll(Arrays.asList(new Location(world, 50, 99, -11)));
		sabotageTasks.add(new SabotageCommunicate(loc, new Location(world, -17, 50, 10), game, new Location(world, -23, 50, 10), new Location(world, -23, 56, 10), new Location(world, -23, 50, 12), new Location(world, -23, 56, 12)));
		
		loc = new ArrayList<Location>();
		List<Location> checkers = new ArrayList<Location>();
		loc.addAll(Arrays.asList(new Location(world, 32, 99, 22)));
		checkers.addAll(Arrays.asList(new Location(world, -23, 53, 2), new Location(world, -23, 53, 0), new Location(world, -23, 53, -2), new Location(world, -23, 51, 2), new Location(world, -23, 51, 0), new Location(world, -23, 51, -2)));
		sabotageTasks.add(new SabotageElectrical(loc, new Location(world, -17, 50, 0), game, checkers));
		
		loc = new ArrayList<Location>();
		loc.addAll(Arrays.asList(new Location(world, 6, 99, -14), new Location(world, 21, 99, -18)));
		sabotageTasks.add(new SabotageOxygen(loc, new Location(world, -17, 50, -10), game, new Location(world, -23, 51, -7), new Location(world, -23, 51, -13)));
		
		return sabotageTasks;
		
	}
	
	private static List<Door> initDoors() {
		
		List<Door> doors = new ArrayList<Door>();
		
		List<Location> locFrom;
		List<Location> locTo;
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 27, 98, 44), new Location(world, 37, 98, 32)));
		locTo.addAll(Arrays.asList(new Location(world, 27, 101, 42), new Location(world, 35, 101, 32)));
		doors.add(new Door("downDrive", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 9, 98, 42), new Location(world, -1, 98, 32)));
		locTo.addAll(Arrays.asList(new Location(world, 9, 101, 44), new Location(world, 1, 101, 32)));
		doors.add(new Door("upDrive", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 19, 98, 36)));
		locTo.addAll(Arrays.asList(new Location(world, 17, 101, 36)));
		doors.add(new Door("security", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 3, 98, 20)));
		locTo.addAll(Arrays.asList(new Location(world, 3, 101, 18)));
		doors.add(new Door("medbay", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 39, 98, 20)));
		locTo.addAll(Arrays.asList(new Location(world, 39, 101, 22)));
		doors.add(new Door("electrical", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 12, 98, -1), new Location(world, 1, 98, 12), new Location(world, -1, 98, -12)));
		locTo.addAll(Arrays.asList(new Location(world, 12, 101, 1), new Location(world, -1, 101, 12), new Location(world, 1, 101, -12)));
		doors.add(new Door("cafeteria", locFrom, locTo));
		
		locFrom = new ArrayList<Location>();
		locTo = new ArrayList<Location>();
		locFrom.addAll(Arrays.asList(new Location(world, 44, 98, 12), new Location(world, 27, 98, 1), new Location(world, 38, 98, -5)));
		locTo.addAll(Arrays.asList(new Location(world, 42, 101, 12), new Location(world, 27, 101, -1), new Location(world, 40, 101, -5)));
		doors.add(new Door("storage", locFrom, locTo));
		
		return doors;
		
	}
	
	private static List<Manhole> initManholes() {
		
		List<Manhole> manholes = new ArrayList<Manhole>();
		
		manholes.add(new Manhole(new Location(world, 8.5, 98, -39.5), new Location(world, 8.5, 94, -39.5)));
		manholes.add(new Manhole(new Location(world, 20.5, 98, -39.5), new Location(world, 20.5, 94, -39.5)));
		manholes.add(new Manhole(new Location(world, 17.5, 98, -25.5), new Location(world, 17.5, 94, -25.5)));
		manholes.add(new Manhole(new Location(world, 45.5, 98, -24.5), new Location(world, 45.5, 94, -24.5)));
		manholes.add(new Manhole(new Location(world, 32.5, 98, -6.5), new Location(world, 32.5, 94, -6.5)));
		manholes.add(new Manhole(new Location(world, 3.5, 98, -9.5), new Location(world, 3.5, 94, -9.5)));
		manholes.add(new Manhole(new Location(world, -10.5, 98, -25.5), new Location(world, -10.5, 94, -25.5)));
		manholes.add(new Manhole(new Location(world, 12.5, 98, 23.5), new Location(world, 12.5, 94, 23.5)));
		manholes.add(new Manhole(new Location(world, 23.5, 98, 21.5), new Location(world, 23.5, 94, 21.5)));
		manholes.add(new Manhole(new Location(world, 41.5, 98, 34.5), new Location(world, 41.5, 94, 34.5)));
		manholes.add(new Manhole(new Location(world, -3.5, 98, 34.5), new Location(world, -3.5, 94, 34.5)));
		manholes.add(new Manhole(new Location(world, 23.5, 98, 28.5), new Location(world, 23.5, 94, 28.5)));
		manholes.add(new Manhole(new Location(world, 24.5, 98, 56.5), new Location(world, 24.5, 94, 56.5)));
		manholes.add(new Manhole(new Location(world, 10.5, 98, 61.5), new Location(world, 10.5, 94, 61.5)));
		
		return manholes;
		
	}
	
	private static List<Task> initTasks(Game game) {
		
		List<Task> tasks = new ArrayList<Task>();
		
		tasks.add(new Card(new Location(world, 29, 98, -15), new Location(world, 3, 50, 0), new Location(world, 1.5, 51, 2.5), new Location(world, 1.5, 51, -2.5)));
		
		tasks.add(new Meteor(new Location(world, 0, 99, -25), new Location(world, 3, 50, -10), new Location(world, -3, 55, -8), new Location(world, -3, 51, -12), new Location(world, 0, 99, -25), game.visual_task));
		
		List<Location> way = new ArrayList<Location>();
		way.addAll(Arrays.asList(new Location(world, -3, 52, 12), new Location(world, -3, 54, 10), new Location(world, -3, 52, 8), new Location(world, -3, 55, 7)));
		tasks.add(new ShipWay(new Location(world, 9, 99, -46), new Location(world, 3, 50, 10), way));

		List<Location> shields = new ArrayList<Location>();
		shields.addAll(Arrays.asList(new Location(world, -3, 55, 20), new Location(world, -3, 54, 21), new Location(world, -3, 54, 19), new Location(world, -3, 53, 22), new Location(world, -3, 53, 20), new Location(world, -3, 53, 18), new Location(world, -3, 52, 21), new Location(world, -3, 52, 19), new Location(world, -3, 51, 20)));
		tasks.add(new Shield(new Location(world, 45, 99, -19), new Location(world, 3, 50, 20), shields));
		
		tasks.add(new Scan(new Location(world, 14, 99, 16), new Location(world, -7, 50, 0), new Location(world, -10.5, 51, 0.5), new Location(world, -13, 55, 3), new Location(world, -13, 55, -3), game.visual_task));
		
		List<Location> bar = new ArrayList<Location>();
		List<Location> buttons = new ArrayList<Location>();
		bar.addAll(Arrays.asList(new Location(world, -7, 55, -13), new Location(world, -7, 55, -11), new Location(world, -7, 55, -9), new Location(world, -7, 55, -7)));
		buttons.addAll(Arrays.asList(new Location(world, -13, 55, -8), new Location(world, -13, 55, -10), new Location(world, -13, 55, -12), new Location(world, -13, 53, -8), new Location(world, -13, 53, -10), new Location(world, -13, 53, -12), new Location(world, -13, 51, -8), new Location(world, -13, 51, -10), new Location(world, -13, 51, -12)));
		tasks.add(new Reactor(new Location(world, 18, 99, 60), new Location(world, -7, 50, -10), bar, buttons));
		
		tasks.add(new CalibrateDistr(new Location(world, 23, 99, 11), new Location(world, -27, 50, 20), new Location(world, -33, 56, 23), new Location(world, -33, 50, 17)));
		
		tasks.add(new UnlockManifold(new Location(world, 8, 99, 61), new Location(world, -27, 50, 10), new Location(world, -32, 52, 12), new Location(world, -32, 51, 8)));
		
		tasks.add(new ClearOxygen(new Location(world, 7, 99, -13), new Location(world, -37, 50, -10), new Location(world, -43, 56, -8), new Location(world, -43, 50, -13)));
		
		tasks.add(new StabilizeWay(new Location(world, 14, 99, -48), new Location(world, -47, 50, 20)));
		
		return tasks;
		
	}
	
	private static List<Task> initLongTasks(Game game) {
		
		List<Task> tasks = new ArrayList<Task>();
		
		List<Location> nextStepsLocation = new ArrayList<Location>();
		List<Location> nextStepsLocTo = new ArrayList<Location>();
		nextStepsLocation.addAll(Arrays.asList(new Location(world, -11, 99, -26), new Location(world, 8, 99, -43), new Location(world, 21, 99, -9), new Location(world, 44, 99, -9), new Location(world, 22, 99, 22)));
		nextStepsLocTo.addAll(Arrays.asList(new Location(world, -7, 50, 20), new Location(world, -7, 50, 20), new Location(world, -7, 50, 20), new Location(world, -7, 50, 20), new Location(world, -7, 50, 20)));
		tasks.add(new Upload_Download(new Location(world, -6, 99, -10), new Location(world, -7, 50, 20), nextStepsLocation, nextStepsLocTo, new Location(world, -12, 51, 17), new Location(world, -12, 51, 23), new Location(world, -12, 51, 17), new Location(world, -12, 51, 23)));
		
		nextStepsLocation = new ArrayList<Location>();
		nextStepsLocTo = new ArrayList<Location>();
		nextStepsLocation.addAll(Arrays.asList(new Location(world, 11, 99, -9), new Location(world, 48, 99, -3)));
		nextStepsLocTo.addAll(Arrays.asList(new Location(world, -7, 50, 10), new Location(world, -7, 50, 10)));
		tasks.add(new Rabbish(new Location(world, -7, 99, -10), new Location(world, -7, 50, 10), nextStepsLocation, nextStepsLocTo, new Location(world, -13, 50, 8), new Location(world, -11, 54, 12), new Location(world, -11, 49, 12), new Location(world, -13, 49, 8), game.visual_task));
		
		nextStepsLocation = new ArrayList<Location>();
		nextStepsLocTo = new ArrayList<Location>();
		nextStepsLocation.add(new Location(world, 5, 99, 45));
		nextStepsLocTo.add(new Location(world, -27, 50, 0));
		tasks.add(new StabilizeDrive(new Location(world, 39, 99, 45), new Location(world, -27, 50, 0), nextStepsLocation, nextStepsLocTo, new Location(world, -33, 56, 0), new Location(world, -33, 52, 0)));
		
		nextStepsLocation = new ArrayList<Location>();
		nextStepsLocTo = new ArrayList<Location>();
		nextStepsLocation.addAll(Arrays.asList(new Location(world, 4, 99, 44), new Location(world, 41, 98, 6), new Location(world, 38, 99, 44)));
		nextStepsLocTo.addAll(Arrays.asList(new Location(world, -27, 50, -10), new Location(world, -27, 50, -10), new Location(world, -27, 50, -10)));
		tasks.add(new Refuel(new Location(world, 41, 98, 6), new Location(world, -27, 50, -10), nextStepsLocation, nextStepsLocTo, new Location(world, -33, 50, -10), new Location(world, -33, 56, -10)));
		
		nextStepsLocation = new ArrayList<Location>();
		nextStepsLocTo = new ArrayList<Location>();
		List<Location> wiresStart = new ArrayList<Location>();
		wiresStart.addAll(Arrays.asList(new Location(world, -43, 50, 22), new Location(world, -43, 56, 22), new Location(world, -43, 54, 22), new Location(world, -43, 52, 22)));
		List<Location> wiresEnd = new ArrayList<Location>();
		wiresEnd.addAll(Arrays.asList(new Location(world, -43, 50, 18), new Location(world, -43, 56, 18), new Location(world, -43, 54, 18), new Location(world, -43, 52, 18)));
		nextStepsLocation.addAll(Arrays.asList(new Location(world, 13, 99, -37), new Location(world, -8, 99, 8)));
		nextStepsLocTo.addAll(Arrays.asList(new Location(world, -37, 50, 20), new Location(world, -37, 50, 20)));
		tasks.add(new Wiring(new Location(world, 29, 99, 4), new Location(world, -37, 50, 20), nextStepsLocation, nextStepsLocTo, wiresStart, wiresEnd));
		
		nextStepsLocation = new ArrayList<Location>();
		nextStepsLocTo = new ArrayList<Location>();
		nextStepsLocation.addAll(Arrays.asList(new Location(world, 0, 99, -34), new Location(world, 10, 99, -21), new Location(world, 8, 99, -40), new Location(world, 31, 99, -32), new Location(world, 44, 99, -15), new Location(world, 29, 99, 45), new Location(world, -4, 99, 39), new Location(world, 13, 99, 29)));
		nextStepsLocTo.addAll(Arrays.asList(new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0), new Location(world, -37, 50, 0)));
		tasks.add(new Energy(new Location(world, 22, 99, 20), new Location(world, -37, 50, 10), nextStepsLocation, nextStepsLocTo, new Location(world, -43, 54, 10), new Location(world, -43, 56, 10), new Location(world, -43, 53, 0)));
		
		return tasks;
		
	}
	
}
