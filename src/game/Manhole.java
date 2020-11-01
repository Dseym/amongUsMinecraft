package game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Manhole {

	private Location locFrom;
	private Location location;
	
	public Manhole(Location location, Location locFrom) {
		
		this.location = location;
		this.locFrom = locFrom;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public Location getLocFrom() {
		
		return locFrom;
		
	}
	
	@SuppressWarnings("deprecation")
	public void tp(PlayerGame pg) {
		
		Player p = pg.getPlayer();
		
		p.removePotionEffect(PotionEffectType.SPEED);
		
		if(p.getLocation().distance(location) < 1.5) {
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 4));
			p.teleport(locFrom);
			for(Player _p: Bukkit.getOnlinePlayers())
				_p.hidePlayer(p);
			
		} else {
			
			p.teleport(location);
			for(Player _p: Bukkit.getOnlinePlayers())
				_p.showPlayer(p);
			
		}
		
	}
	
}
