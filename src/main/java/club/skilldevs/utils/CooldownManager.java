package club.skilldevs.utils;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private static final HashMap<UUID, HashMap<String, Long>> cooldowns = new HashMap<>();

    public static void addCooldown(UUID uuid, String key, int seconds) {
        cooldowns.putIfAbsent(uuid, new HashMap<>());
        cooldowns.get(uuid).put(key, System.currentTimeMillis() + (seconds * 1000L));
    }

    public static void removeCooldown(UUID uuid, String key) {
        cooldowns.get(uuid).remove(key);
    }

    public static boolean hasCooldown(UUID uuid, String key) {
        if (cooldowns.containsKey(uuid)) {
            HashMap<String, Long> cooldown = cooldowns.get(uuid);
            if (cooldown.containsKey(key)) {
                if (cooldown.get(key) > System.currentTimeMillis()) return true;
                cooldown.remove(key);
                return false;
            }
        }
        return false;
    }

    public static int getReaminingCooldown(UUID uuid, String key) {
        if (cooldowns.containsKey(uuid)) {
            HashMap<String, Long> cooldown = cooldowns.get(uuid);
            if (cooldown.containsKey(key)) {
                return (int) ((cooldown.get(key) - System.currentTimeMillis()) / 1000L);
            }
        }
        return 0;
    }

}
