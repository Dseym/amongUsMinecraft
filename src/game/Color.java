package game;

import org.bukkit.ChatColor;

public enum Color {

	DARK_BLUE("0000AA", "Dark Blue", ChatColor.DARK_BLUE),
	DARK_RED("AA0000", "Dark Red", ChatColor.DARK_RED),
	DARK_AQUE("00AAAA", "Dark Aque", ChatColor.DARK_AQUA),
	DARK_GREEN("00AA00", "Dark Green", ChatColor.DARK_GREEN),
	DARK_PURPLE("AA00AA", "Dark Purple", ChatColor.DARK_PURPLE),
	DARK_GRAY("555555", "Dark Gray", ChatColor.DARK_GRAY),
	BLUE("5555FF", "Blue", ChatColor.BLUE),
	GREEN("55FF55", "Green", ChatColor.GREEN),
	RED("FF5555", "Red", ChatColor.RED),
	YELLOW("FFFF55", "Yellow", ChatColor.YELLOW);
	
	private String hex;
	private String name;
	private ChatColor chatColor;

	Color(String hex, String name, ChatColor chatColor) {
		
		this.hex = hex;
		this.name = name;
		this.chatColor = chatColor;
		
	}
	
	public org.bukkit.Color getBukkitColor() {

		return org.bukkit.Color.fromRGB(Integer.valueOf(hex, 16));
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public ChatColor getChatColor() {
		
		return chatColor;
		
	}
	
}
