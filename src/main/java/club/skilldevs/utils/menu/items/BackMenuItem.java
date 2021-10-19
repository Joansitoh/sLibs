package club.skilldevs.utils.menu.items;

import club.skilldevs.utils.xseries.XMaterial;
import club.skilldevs.utils.menu.actions.ItemClickEvent;
import club.skilldevs.utils.menu.menus.ItemMenu;

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