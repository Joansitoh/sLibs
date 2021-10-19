package devs.skillclub.utils.menu.items;

import devs.skillclub.utils.xseries.XMaterial;
import devs.skillclub.utils.menu.actions.ItemClickEvent;
import devs.skillclub.utils.menu.menus.ItemMenu;

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