package devs.skillclub.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 31/08/2021 - 19:37.
 */
public class PlayerUtils {

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

}
