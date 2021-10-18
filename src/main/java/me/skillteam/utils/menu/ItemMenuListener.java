package me.skillteam.utils.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemMenuListener implements Listener {

    private static final ItemMenuListener INSTANCE = new ItemMenuListener();
    private JavaPlugin plugin;

    public static ItemMenuListener getInstance() {
        return INSTANCE;
    }

    public static void closeOpenMenus() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory() == null || !(player.getOpenInventory().getTopInventory().getHolder() instanceof ItemMenuHolder))
                continue;
            player.closeInventory();
        }
    }

    public void register(JavaPlugin plugin) {
        this.plugin = plugin;
        if (this.isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }

    public boolean isRegistered(JavaPlugin plugin) {
        if (plugin.equals(this.plugin)) {
            for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (!listener.getListener().equals(INSTANCE)) continue;
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof ItemMenuHolder)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (!event.getInventory().getType().equals(InventoryType.CHEST)) return;

        ((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof ItemMenuHolder)) return;
        if (!event.getInventory().getType().equals(InventoryType.CHEST)) return;

        ((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClose(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCreativeEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof ItemMenuHolder)) return;
        if (!event.getInventory().getType().equals(InventoryType.CHEST)) return;

        ((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryCreativeClick(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            ItemMenuListener.closeOpenMenus();
            this.plugin = null;
        }
    }

}

