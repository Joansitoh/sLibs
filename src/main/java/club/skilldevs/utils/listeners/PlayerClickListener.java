package club.skilldevs.utils.listeners;

import club.skilldevs.utils.PlayerCallable;
import club.skilldevs.utils.events.PlayerAttackPlayerEvent;
import club.skilldevs.utils.sLoader;
import io.netty.channel.*;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 28/09/2021 - 13:40.
 */
public class PlayerClickListener implements Listener {

    private static final Map<UUID, ArrayList<Long>> clicks = new HashMap<>();
    public static final HashMap<UUID, Long> blockInteractMap = new HashMap<>();

    @Getter public static HashMap<UUID, PlayerCallable> callableMap = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) blockInteractMap.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (sLoader.NMS_HANDLER != null) sLoader.NMS_HANDLER.injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (sLoader.NMS_HANDLER != null) sLoader.NMS_HANDLER.unInjectPlayer(event.getPlayer());
    }

    ///////////////////////////////////////////////////////////////////////////

    public static void addClick(Player p) {
        try {
            clicks.putIfAbsent(p.getUniqueId(), new ArrayList<>());
            if (blockInteractMap.containsKey(p.getUniqueId())) {
                if (blockInteractMap.get(p.getUniqueId()) + 500 > System.currentTimeMillis())
                    return;
            }

            clicks.get(p.getUniqueId()).add(System.currentTimeMillis());
        } catch (Exception ignore) {}
    }

    public static int getClicks(Player p) {
        clicks.putIfAbsent(p.getUniqueId(), new ArrayList<>());
        try {
            ArrayList<Long> cps = clicks.getOrDefault(p.getUniqueId(), new ArrayList<>());
            cps.removeIf(aLong -> aLong < System.currentTimeMillis() - 1000L);
            clicks.put(p.getUniqueId(), cps);
            return clicks.get(p.getUniqueId()).size();
        } catch (Exception ignore) {
            return 0;
        }
    }

    ///////////////////////////////////////////////////////////////////////////

}
