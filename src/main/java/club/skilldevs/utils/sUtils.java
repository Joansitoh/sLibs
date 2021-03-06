package club.skilldevs.utils;

import club.skilldevs.utils.listeners.PlayerClickListener;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class sUtils {

    public static void cleanUp(Player player) {
        // Inventory clean
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        // Attributes clean
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(30);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        // Experience clean
        player.setLevel(0);
        player.setExp(0);

        // Mode clean
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static int getPlayerClicks(Player player) {
        return PlayerClickListener.getClicks(player);
    }

    public static void setPlayerClickListener(Player player, PlayerCallable callable) {
        PlayerClickListener.getCallableMap().put(player.getUniqueId(), callable);
    }

    ////////////////////////////////////////////////////////////////////////////

    public static boolean consumeItem(Player player, int count, XMaterial mat) {
        return consumeItem(player, count, mat, null);
    }

    public static boolean consumeItem(Player player, int count, XMaterial mat, String s) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat.parseMaterial());

        int found = 0;
        for (ItemStack stack : ammo.values()) found += stack.getAmount();
        if (count > found) return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);
            if (s != null) {
                if (!stack.hasItemMeta() || !stack.getItemMeta().hasDisplayName() || !stack.getItemMeta().getDisplayName().equalsIgnoreCase(s))
                    return false;
            }

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed) player.getInventory().setItem(index, null);
            else stack.setAmount(stack.getAmount() - removed);

            if (count <= 0) break;
        }

        player.updateInventory();
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////

    public static Map<UUID, Location> getLocationsMap(ConfigurationSection section, boolean center) {
        Map<UUID, Location> locations = new HashMap<>();
        for (String s : section.getKeys(false)) {
            locations.put(UUID.fromString(s), getLocation(section.getConfigurationSection(s), center));
        }
        return locations;
    }

    public static List<Location> getLocations(ConfigurationSection section, boolean center) {
        List<Location> locations = new ArrayList<>();
        for (String s : section.getKeys(false)) locations.add(getLocation(section.getConfigurationSection(s), center));
        return locations;
    }

    ////////////////////////////////////////////////////////////////////////////

    public static Location getLocation(ConfigurationSection section) {
        return getLocation(section, false);
    }

    public static Location getLocation(ConfigurationSection section, boolean center) {
        String world = section.getString("WORLD");
        double x = section.getDouble("X"), y = section.getDouble("Y"), z = section.getDouble("Z");
        double yaw = section.getDouble("YAW"), pitch = section.getDouble("PITCH");

        if (world == null || Bukkit.getWorld(world) == null) return null;
        Location loc = new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
        if (center) {
            loc = new Location(Bukkit.getWorld(world), (int) x, (int) y, (int) z, (float) yaw, (float) pitch);
            loc.add(0.5, 0, 0.5);
        }

        return loc;
    }

    ////////////////////////////////////////////////////////////////////////////

    public static void saveLocation(FileConfig config, Location location) {
        saveLocation(config, UUID.randomUUID().toString(), location);
    }

    public static void saveLocation(FileConfig config, String path, Location location) {
        config.getConfiguration().set(path + ".WORLD", location.getWorld().getName());

        config.getConfiguration().set(path + ".X", location.getBlockX());
        config.getConfiguration().set(path + ".Y", location.getBlockY());
        config.getConfiguration().set(path + ".Z", location.getBlockZ());

        config.getConfiguration().set(path + ".YAW", location.getYaw());
        config.getConfiguration().set(path + ".PITCH", location.getPitch());
    }

}
