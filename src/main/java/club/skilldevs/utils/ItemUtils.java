package club.skilldevs.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static ItemStack setItemName(ItemStack item, String name) {
        return setItemName(item, name, false);
    }

    public static ItemStack setItemName(ItemStack item, String name, boolean color) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color ? ChatUtils.translate(name) : name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setItemLore(ItemStack item, List<String> lore) {
        return setItemLore(item, lore, false);
    }

    public static ItemStack setItemLore(ItemStack item, List<String> lore, boolean color) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(color ? ChatUtils.translate(lore) : lore);
        item.setItemMeta(meta);
        return item;
    }



    // Check if item has valid name.
    public static boolean hasValidName(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().hasDisplayName();
    }

    // Check if item has valid lore.
    public static boolean hasValidLore(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) return false;
        return item.getItemMeta().hasLore();
    }

    public static String getItemName(ItemStack item) {
        String name = item == null ? "" : item.getType().name();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName())
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
