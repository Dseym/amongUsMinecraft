package game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import amongUs.Inv;
import amongUs.Messages;

public class Kits {

	public static void imposter(Player p) {
		
		PlayerInventory inv = p.getInventory();
		
		inv.clear();
		
		inv.setItem(1, Inv.genItem(Material.IRON_SWORD, Messages.knife));
		inv.setItem(4, Inv.genItem(Material.REDSTONE_BLOCK, Messages.mapSabotage));
		inv.setItem(7, Inv.genItem(Material.CLAY_BRICK, Messages.report));
		
	}
	
	public static void crewmate(Player p) {
		
		PlayerInventory inv = p.getInventory();
		
		inv.clear();
		
		inv.setItem(4, Inv.genItem(Material.CLAY_BRICK, Messages.report));
		
	}
	
	public static void vote(Player p) {
		
		PlayerInventory inv = p.getInventory();
		
		inv.clear();
		
		for(int i = 0; i < 9; i++)
			inv.setItem(i, Inv.genItem(Material.BARRIER, "§c"));
		
		inv.setItem(4, Inv.genItem(Material.BOOK, Messages.vote));
		
	}
	
	public static void colorArmor(Player p, Color color) {
		
		PlayerInventory inv = p.getInventory();
		
		ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
		meta.setColor(color.getBukkitColor());
		item.setItemMeta(meta);
		
		inv.setBoots(item);
		
		item = new ItemStack(Material.LEATHER_LEGGINGS);
		meta = (LeatherArmorMeta)item.getItemMeta();
		meta.setColor(color.getBukkitColor());
		item.setItemMeta(meta);
		
		inv.setLeggings(item);
		
		item = new ItemStack(Material.LEATHER_CHESTPLATE);
		meta = (LeatherArmorMeta)item.getItemMeta();
		meta.setColor(color.getBukkitColor());
		item.setItemMeta(meta);
		
		inv.setChestplate(item);
		
	}
	
}
