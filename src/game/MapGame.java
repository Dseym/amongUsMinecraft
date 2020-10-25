package game;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;

import tasks.Sabotage;
import tasks.Task;

public class MapGame {

	private Location emergencyMetting;
	private List<Location> spawns;
	private List<Task> tasks;
	private List<Sabotage> sabotages;
	private List<Manhole> manholes;
	private List<Door> doors;
	private Map<String, String> sabotageTypes;
	private Cameras camera;
	private World world;
	
	public MapGame(Location emergencyMetting, List<Location> spawns, List<Task> tasks, List<Manhole> manholes, List<Door> doors, Map<String, String> sabotageTypes, List<Sabotage> sabotages, Cameras camera) {
		
		world = emergencyMetting.getWorld();
		this.emergencyMetting = emergencyMetting;
		this.spawns = spawns;
		this.tasks = tasks;
		this.manholes = manholes;
		this.doors = doors;
		this.sabotageTypes = sabotageTypes;
		this.sabotages = sabotages;
		this.camera = camera;
		
	}
	
	public World getWorld() {
		
		return world;
		
	}
	
	public List<Task> getTasks() {
		
		return tasks;
		
	}
	
	public Cameras getCamera(Location loc) {
		
		if(camera.getLocation().distance(loc) < 1)
			return camera;
		
		return null;
		
	}
	
	public Cameras getCameras() {
		
		return camera;
		
	}
	
	public List<Sabotage> getSabotageTasks() {
		
		return sabotages;
		
	}
	
	public Location getMetting() {
		
		return emergencyMetting;
		
	}
	
	public List<Location> getSpawns() {
		
		return spawns;
		
	}
	
	public Manhole getManhole(Location loc) {
		
		for(Manhole manhole: manholes)
			if(manhole.getLocation().distance(loc) < 1 || manhole.getLocFrom().distance(loc) < 1)
				return manhole;
		
		return null;
		
	}
	
	public Door getDoor(String name) {
		
		for(Door door: doors)
			if(door.getName().equalsIgnoreCase(name))
				return door;
		
		return null;
		
	}
	
	public List<Door> getDoors() {
		
		return doors;
		
	}
	
	public Map<String, String> getSabotageTypes() {
		
		return sabotageTypes;
		
	}
	
}
