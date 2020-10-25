package amongUs;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inv {
	
	public static void init() {
		
		sabotageMap();
		
	}

	public static ItemStack genItem(Material mat, String name, String... lores) {
		
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(lores != null)
			meta.setLore(Arrays.asList(lores));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	public static ItemStack genItem(Material mat, String name, int dataByte, String... lores) {
		
		ItemStack item = new ItemStack(mat, 1, (byte)dataByte);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(lores != null)
			meta.setLore(Arrays.asList(lores));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	private static Inventory sabotageMap;
	private static void sabotageMap() {
		
		sabotageMap = Bukkit.createInventory(null, 54, Messages.mapSabotage);
		
		for(int i = 0; i < sabotageMap.getSize(); i++)
			sabotageMap.setItem(i, genItem(Material.STAINED_GLASS_PANE, "§7"));

		sabotageMap.setItem(1, genItem(Material.IRON_DOOR, "Sabotage", "§eUp Drive", Messages.sabType_doorUpDrive, "ID: doorUpDrive"));
		sabotageMap.setItem(37, genItem(Material.IRON_DOOR, "Sabotage", "§eDown Drive", Messages.sabType_doorDownDrive, "ID: doorDownDrive"));
		sabotageMap.setItem(20, genItem(Material.IRON_DOOR, "Sabotage", "§eSecurity", Messages.sabType_doorSecurity, "ID: doorSecurity"));
		sabotageMap.setItem(12, genItem(Material.IRON_DOOR, "Sabotage", "§eMedbay", Messages.sabType_doorMedbay, "ID: doorMedbay"));
		sabotageMap.setItem(29, genItem(Material.IRON_DOOR, "Sabotage", "§eElectrical", Messages.sabType_doorElectrical, "ID: doorElectrical"));
		sabotageMap.setItem(5, genItem(Material.IRON_DOOR, "Sabotage", "§eCafeteria", Messages.sabType_doorCafeteria, "ID: doorCafeteria"));
		sabotageMap.setItem(41, genItem(Material.IRON_DOOR, "Sabotage", "§eStorage", Messages.sabType_doorStorage, "ID: doorStorage"));
		
		sabotageMap.setItem(18, genItem(Material.SEA_LANTERN, "Sabotage", "§eReactor", Messages.sabType_reactor, "ID: reactor"));
		sabotageMap.setItem(51, genItem(Material.REDSTONE_ORE, "Sabotage", "§eCommunication", Messages.sabType_communicate, "ID: communicate"));
		sabotageMap.setItem(15, genItem(Material.SAPLING, "Sabotage", "§eOxygen", Messages.sabType_oxygen, "ID: oxygen"));
		sabotageMap.setItem(30, genItem(Material.REDSTONE_COMPARATOR, "Sabotage", "§eElectrical", Messages.sabType_electrical, "ID: electrical"));
		
	}
	
	public static void showSabotageMap(Player p) {
		
		p.openInventory(sabotageMap);
		
	}
	
}
