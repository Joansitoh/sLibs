package club.skilldevs.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static void setItemName(ItemStack item, String name) {
        item.getItemMeta().setDisplayName(name);
    }

    public static void setItemLore(ItemStack item, List<String> lore) {
        item.getItemMeta().setLore(lore);
    }

    // Check if item has valid name.
    public static boolean hasValidName(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().hasDisplayName();
    }

    // Check if item has valid lore.
    public static boolean hasValidLore(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().hasLore();
    }

    // Check if item has name.
    public static boolean hasName(ItemStack item, String name) {
        return item.getItemMeta().getDisplayName().equals(name);
    }

    // Check if item has lore.
    public static boolean hasLore(ItemStack item, String lore) {
        return item.getItemMeta().getLore().contains(lore);
    }

    public static String getItemName(ItemStack item) {
        String name = "";
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName())
            name = item.getItemMeta().getDisplayName();
        return name;
    }

    public static List<String> getItemLore(ItemStack item) {
        List<String> lore = new ArrayList<>();
        if (item.hasItemMeta() && item.getItemMeta().hasLore())
            lore = item.getItemMeta().getLore();
        return lore;
    }

}
