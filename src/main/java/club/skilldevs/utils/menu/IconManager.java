package club.skilldevs.utils.menu;

import club.skilldevs.utils.menu.items.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IconManager {

    ItemStack getFinalIcon(MenuItem item);

    ItemStack getFinalIcon(MenuItem item, Player player);

}
