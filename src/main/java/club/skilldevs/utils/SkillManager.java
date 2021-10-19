package club.skilldevs.utils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
public class SkillManager {

    public static JavaPlugin INSTANCE;

    public SkillManager(JavaPlugin plugin) {
        INSTANCE = plugin;
    }

}
