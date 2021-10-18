package me.skillteam.utils.menu.items;

import me.skillteam.utils.menu.actions.ItemClickEvent;
import me.skillteam.utils.xseries.XMaterial;
import org.bukkit.ChatColor;

public class CloseMenuItem extends StaticMenuItem {

    public CloseMenuItem() {
        super("&c&lCLOSE", XMaterial.MUSIC_DISC_BLOCKS.parseItem());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}