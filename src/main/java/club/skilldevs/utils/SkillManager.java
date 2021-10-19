package club.skilldevs.utils;

import club.skilldevs.utils.lunarapi.LunarClientAPI;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
@Getter
public class SkillManager {

    public static JavaPlugin INSTANCE;

    private final LunarClientAPI lunarClientAPI;

    public SkillManager(JavaPlugin plugin) {
        INSTANCE = plugin;
        lunarClientAPI = new LunarClientAPI(plugin);
    }

}
