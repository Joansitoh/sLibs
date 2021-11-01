
package club.skilldevs.utils.menu.items;

import club.skilldevs.utils.ChatUtils;
import club.skilldevs.utils.ItemUtils;
import club.skilldevs.utils.menu.actions.ItemClickEvent;
import club.skilldevs.utils.sLibs;
import club.skilldevs.utils.sLoader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class MenuItem {

    private String displayName;
    private ItemStack icon;
    private List<String> lore;

    public MenuItem(String displayName, ItemStack icon, List<String> lore) {
        this.displayName = ItemUtils.getItemName(icon);
        this.lore = ItemUtils.getItemLore(icon);
        this.icon = icon;

        if (displayName != null) this.displayName = ChatUtils.translate(displayName);
        if (lore != null) this.lore = ChatUtils.translate(lore);
    }

    public MenuItem(String displayName, ItemStack icon, String... lore) {
        this(displayName, icon, Arrays.asList(lore));
    }

    public MenuItem(ItemStack icon) {
        this(null, icon);
    }

    public ItemStack getFinalIcon() {
        if (sLibs.iconManager != null) return sLibs.iconManager.getFinalIcon(this);

        if (displayName == null) return icon;
        ItemStack item = icon.clone();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void onItemClick(ItemClickEvent event) {
    }

}

