package club.skilldevs.utils.launcher;

import club.skilldevs.utils.lunarapi.LunarClientAPI;
import club.skilldevs.utils.nms.sNMSHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
public class sLoaderAPI {

    public static JavaPlugin INSTANCE;
    public static LunarClientAPI LUNAR_API;
    public static sNMSHandler NMS_HANDLER;

    public sLoaderAPI(JavaPlugin plugin) {
        if (INSTANCE == null) INSTANCE = plugin;
        if (LUNAR_API == null) LUNAR_API = new LunarClientAPI(plugin);
        if (NMS_HANDLER == null) check(plugin);
    }

    public static void check(JavaPlugin plugin) {
        if (NMS_HANDLER == null) {
            String packageName = plugin.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);

            plugin.getServer().getConsoleSender().sendMessage("§a[sLoaderAPI] §fLoading NMS Handler for version §e" + version);
            try {
                final Class<?> clazz = Class.forName("club.skilldevs.utils.nms.ver." + version);
                if (sNMSHandler.class.isAssignableFrom(clazz))
                    sLoaderAPI.NMS_HANDLER = ((sNMSHandler) clazz.getConstructor().newInstance());
                plugin.getServer().getConsoleSender().sendMessage("§a[sLoaderAPI] §fNMS Handler loaded for version §e" + version);
            } catch (final Exception e) {
                plugin.getServer().getConsoleSender().sendMessage("§a[sLoaderAPI] §fFailed to load NMS Handler for version §e" + version);
            }
        }
    }

}
