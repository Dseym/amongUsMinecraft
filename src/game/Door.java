package game;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import amongUs.Main;

public class Door {

	private String nameRoom;
	private List<Location> locTo;
	private List<Location> locFrom;
	private boolean isOpen = true;
	
	public Door(String nameRoom, List<Location> locFrom, List<Location> locTo) {
		
		this.nameRoom = nameRoom;
		this.locTo = locTo;
		this.locFrom = locFrom;
		
	}
	
	public void closeDoor() {
		
		for(int i = 0; i < locFrom.size(); i++)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill " + locFrom.get(i).getBlockX() + " " + locFrom.get(i).getBlockY() + " " + locFrom.get(i).getBlockZ() + " " + locTo.get(i).getBlockX() + " " + locTo.get(i).getBlockY() + " " + locTo.get(i).getBlockZ() + " minecraft:iron_block");
		
		Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {@Override public void run() {openDoor();}}, 7*20);
		
		isOpen = false;
		
	}
	
	public void openDoor() {
		
		for(int i = 0; i < locFrom.size(); i++)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill " + locFrom.get(i).getBlockX() + " " + locFrom.get(i).getBlockY() + " " + locFrom.get(i).getBlockZ() + " " + locTo.get(i).getBlockX() + " " + locTo.get(i).getBlockY() + " " + locTo.get(i).getBlockZ() + " minecraft:air");
		
		isOpen = true;
		
	}
	
	public String getName() {
		
		return nameRoom;
		
	}
	
	public boolean isOpen() {
		
		return isOpen;
		
	}
	
}
