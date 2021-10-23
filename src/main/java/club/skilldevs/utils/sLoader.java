package club.skilldevs.utils;

import club.skilldevs.utils.listeners.PlayerDamageListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
@Getter
public class sLoader {

    public static JavaPlugin INSTANCE;

    public sLoader(JavaPlugin plugin) {
        INSTANCE = plugin;

        plugin.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), plugin);
    }

}
