package club.skilldevs.utils;

import club.skilldevs.utils.listeners.PlayerDamageListener;
import club.skilldevs.utils.lunarapi.LunarClientAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
public class sLoader {

    public static JavaPlugin INSTANCE;
    public static sLoader PLUGIN;
    public static LunarClientAPI LUNAR_API;

    public sLoader(JavaPlugin plugin) {
        INSTANCE = plugin;
        PLUGIN = this;
        LUNAR_API = new LunarClientAPI(plugin);
    }

}
