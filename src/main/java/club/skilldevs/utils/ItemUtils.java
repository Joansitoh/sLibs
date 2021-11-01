package club.skilldevs.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

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
