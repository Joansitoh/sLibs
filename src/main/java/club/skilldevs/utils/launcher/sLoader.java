package club.skilldevs.utils.launcher;

import club.skilldevs.utils.listeners.PlayerClickListener;
import club.skilldevs.utils.listeners.PlayerDamageListener;
import club.skilldevs.utils.lunarapi.LunarClientAPI;
import club.skilldevs.utils.nms.sNMSHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Joansiitoh (DragonsTeam SkillTeam)
 * Date: 23/12/2021 - 19:41.
 */
public class sLoader extends JavaPlugin {

    public static sLoader INSTANCE;

    public static LunarClientAPI LUNAR_API;
    public static sNMSHandler NMS_HANDLER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LUNAR_API = new LunarClientAPI(this);

        if (NMS_HANDLER == null) {
            String packageName = getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);

            getServer().getConsoleSender().sendMessage("§a[sLoader] §fLoading NMS Handler for version §e" + version);
            try {
                final Class<?> clazz = Class.forName("club.skilldevs.utils.nms.ver." + version);
                if (sNMSHandler.class.isAssignableFrom(clazz))
                    NMS_HANDLER = ((sNMSHandler) clazz.getConstructor().newInstance());
                getServer().getConsoleSender().sendMessage("§a[sLoader] §fNMS Handler loaded for version §e" + version);
            } catch (final Exception e) {
                getServer().getConsoleSender().sendMessage("§a[sLoader] §fFailed to load NMS Handler for version §e" + version);
            }
        }

        sLoaderAPI.LUNAR_API = LUNAR_API;
        sLoaderAPI.NMS_HANDLER = NMS_HANDLER;
        sLoaderAPI.INSTANCE = INSTANCE;

        getServer().getConsoleSender().sendMessage("§a[sLoader] §fPlugin has been enabled!");
        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
    }


}
