package club.skilldevs.utils;

import club.skilldevs.utils.listeners.PlayerDamageListener;
import club.skilldevs.utils.lunarapi.LunarClientAPI;
import club.skilldevs.utils.nms.sNMSHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 18/10/2021 - 22:14.
 */
public class sLoader {

    public static JavaPlugin INSTANCE;
    public static sLoader PLUGIN;
    public static LunarClientAPI LUNAR_API;
    public static sNMSHandler NMS_HANDLER;

    public sLoader(JavaPlugin plugin) {
        if (INSTANCE == null) INSTANCE = plugin;
        if (PLUGIN == null) PLUGIN = this;
        if (LUNAR_API == null) LUNAR_API = new LunarClientAPI(plugin);

        if (NMS_HANDLER == null) {
            String packageName = plugin.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);

            try {
                final Class<?> clazz = Class.forName("club.skilldevs.utils.nms.vers." + version);
                if (sNMSHandler.class.isAssignableFrom(clazz))
                    sLoader.NMS_HANDLER = ((sNMSHandler) clazz.getConstructor().newInstance());
                System.out.println("[sLibs] Version " + version + " of NMS loaded!");
            } catch (final Exception e) {
                System.out.println("[sLibs] Could not find support for version " + version + ".");
            }
        }
    }

}
