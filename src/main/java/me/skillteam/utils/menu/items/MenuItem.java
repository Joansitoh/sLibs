/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.skillteam.utils.menu.items;

import me.skillteam.utils.menu.actions.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItem {

    private String displayName;
    private ItemStack icon;
    private List<String> lore;

    public MenuItem(String displayName, ItemStack icon, String... lore) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.icon = icon;
        this.lore = new ArrayList<>();
        this.lore.addAll(Arrays.asList(lore));
    }

    public MenuItem(String displayName, ItemStack icon, List<String> lore) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.icon = icon;
        this.lore = new ArrayList<>();
        this.lore.addAll(lore);
    }

    public MenuItem(ItemStack icon) {
        this.icon = icon;
        if (icon.hasItemMeta()) {
            this.lore = icon.getItemMeta().getLore();
            this.displayName = icon.getItemMeta().getDisplayName();
        }
    }

    public static ItemStack setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
        if (displayName == null) return itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        ArrayList<String> loreColor = new ArrayList<>();

        meta.setLore(loreColor);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack setName(ItemStack itemStack, String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public void setIcon(ItemStack newIcon) {
        this.icon = newIcon;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemStack getFinalIcon() {
        return MenuItem.setNameAndLore(this.getIcon().clone(), this.getDisplayName(), this.getLore());
    }

    public void onItemClick(ItemClickEvent event) {
    }

}

