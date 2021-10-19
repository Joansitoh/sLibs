package club.skilldevs.utils.lunarapi.listener;

import club.skilldevs.utils.SkillManager;
import club.skilldevs.utils.lunarapi.LunarClientAPI;
import club.skilldevs.utils.lunarapi.event.LCPlayerRegisterEvent;
import club.skilldevs.utils.lunarapi.nethandler.client.LCPacketUpdateWorld;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public class LunarClientLoginListener implements Listener {

    private final LunarClientAPI lunarClientAPI;
    private final JavaPlugin INSTANCE = SkillManager.INSTANCE;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(INSTANCE, () -> {
            if (!lunarClientAPI.isRunningLunarClient(player)) {
                lunarClientAPI.failPlayerRegister(player);
            }
        }, 2 * 20L);
    }

    @EventHandler
    public void onRegister(PlayerRegisterChannelEvent event) {
        if (!event.getChannel().equalsIgnoreCase(LunarClientAPI.MESSAGE_CHANNEL)) {
            return;
        }
        Player player = event.getPlayer();

        lunarClientAPI.registerPlayer(player);

        INSTANCE.getServer().getPluginManager().callEvent(new LCPlayerRegisterEvent(event.getPlayer()));
        updateWorld(event.getPlayer());
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        if (event.getChannel().equalsIgnoreCase(LunarClientAPI.MESSAGE_CHANNEL)) {
            lunarClientAPI.unregisterPlayer(event.getPlayer(), false);
        }
    }

    @EventHandler
    public void onUnregister(PlayerQuitEvent event) {
        lunarClientAPI.unregisterPlayer(event.getPlayer(), true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        updateWorld(event.getPlayer());
    }

    private void updateWorld(Player player) {
        String worldIdentifier = lunarClientAPI.getWorldIdentifier(player.getWorld());

        lunarClientAPI.sendPacket(player, new LCPacketUpdateWorld(worldIdentifier));
    }
}
