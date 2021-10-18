package me.skillteam.utils.menu.items;

import me.skillteam.utils.menu.actions.ItemClickEvent;
import me.skillteam.utils.menu.menus.ItemMenu;
import me.skillteam.utils.xseries.XMaterial;

public class BackMenuItem extends StaticMenuItem {

    private final ItemMenu old;

    public BackMenuItem(ItemMenu parent) {
        super("&c&lGO BACK", XMaterial.REDSTONE.parseItem());
        this.old = parent;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        if (old != null) old.open(event.getPlayer());
        else event.getPlayer().closeInventory();
    }

}