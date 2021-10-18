package me.skillteam.utils.menu.items;

import me.skillteam.utils.menu.actions.ItemClickEvent;
import me.skillteam.utils.menu.actions.ItemClickHandler;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ActionMenuItem extends MenuItem {

    private final MenuItem instance;
    private ItemClickHandler handler;

    public ActionMenuItem(String displayName, ItemClickHandler handler, ItemStack icon, String... lore) {
        super(displayName, icon, lore);
        this.handler = handler;
        this.instance = this;
    }

    public ActionMenuItem(String displayName, ItemClickHandler handler, ItemStack icon, List<String> lore) {
        super(displayName, icon, arrayFromList(lore));
        this.handler = handler;
        this.instance = this;
    }

    public ActionMenuItem(String displayName, ItemClickHandler handler, ItemStack icon) {
        this(displayName, handler, icon, new String[0]);
    }

    private static String[] arrayFromList(List<String> list) {
        String[] tor = new String[list.size()];
        for (int x = 0; x < tor.length; x++)
            tor[x] = list.get(x);
        return tor;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        this.handler.onItemClick(event);
    }

    public ItemClickHandler getHandler() {
        return this.handler;
    }

    public void setHandler(ItemClickHandler handler) {
        this.handler = handler;
    }
}

