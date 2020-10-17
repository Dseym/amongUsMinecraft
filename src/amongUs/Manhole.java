package amongUs;

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
	
	public void tp(PlayerGame player) {
		
		Player _player = player.getPlayer();
		
		_player.removePotionEffect(PotionEffectType.SPEED);
		_player.removePotionEffect(PotionEffectType.INVISIBILITY);
		
		if(_player.getLocation().distance(location) < 1.5) {
			
			_player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 4));
			_player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
			_player.teleport(locFrom);
			
		} else {
			
			_player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2*20, 0));
			_player.teleport(location);
			
		}
		
	}
	
}
