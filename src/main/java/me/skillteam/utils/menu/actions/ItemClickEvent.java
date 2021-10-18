/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.inventory.ItemStack
 */
package me.skillteam.utils.menu.actions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemClickEvent {

    private final Player player;
    private final ClickType clickType;

    private final ItemStack stack;
    private final Inventory inventory;

    @Setter
    private boolean goBack = false, close = false, update = false, canClick = false;

    public ItemClickEvent(Player player, ItemStack stack, ClickType type, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        this.stack = stack;
        this.clickType = type;
    }

    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            this.close = false;
            this.update = false;
        }
    }

    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            this.goBack = false;
            this.update = false;
        }
    }

    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            this.goBack = false;
            this.close = false;
        }
    }

}

