package club.skilldevs.utils;

import club.skilldevs.utils.listeners.PlayerDamageListener;
import club.skilldevs.utils.menu.IconManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
public class sLoader {

    public static JavaPlugin INSTANCE;
    public static sLoader PLUGIN;

    public sLoader(JavaPlugin plugin) {
        INSTANCE = plugin;
        PLUGIN = this;
    }

    public void registerDamageListeners(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), plugin);
    }
}
