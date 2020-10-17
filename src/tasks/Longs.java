package tasks;

import java.util.List;

import org.bukkit.Location;

import amongUs.Messages;

public abstract class Longs extends Task {
	
	private List<Location> nextStepsLocation;
	private List<Location> nextStepsLocTo;
	protected int step = 1;

	public Longs(Location loc, Location locTo, List<Location> nextStepsLocation, List<Location> nextStepsLocTo) {
		
		super(loc, locTo);
		
		this.nextStepsLocation = nextStepsLocation;
		this.nextStepsLocTo = nextStepsLocTo;
		
	}
	
	public void nextStep() {
		
		if(nextStepsLocation.size() == 0) {
			
			super.complete(true);
			return;
			
		}
		
		player.sendMessage("§b§o" + Messages.stepComplete);
		
		super.complete(false);
		
		step++;
		
		super.location = nextStepsLocation.get(0);
		super.locTo = nextStepsLocTo.get(0);
		
		nextStepsLocation.remove(0);
		nextStepsLocTo.remove(0);
		
	}

	public abstract void start();

}
