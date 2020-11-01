package game;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum PlaySound {

	DEATH(Sound.BLOCK_SLIME_FALL, 5F),
	SABOTAGE(Sound.BLOCK_ANVIL_FALL, 1F),
	TASK_COMPLETE(Sound.ENTITY_PLAYER_LEVELUP, 1F),
	VOTE(Sound.BLOCK_NOTE_BASS, 1F),
	MANHOLE(Sound.ENTITY_ENDERMEN_TELEPORT, 10F);
	
	Sound sound;
	float pitch;
	
	PlaySound(Sound sound, float pitch) {
		
		this.sound=sound;
		this.pitch=pitch;
		
	}
	
	public void play(Player p) {
		
		p.playSound(p.getLocation(), sound, 1, pitch);
		
	}
}