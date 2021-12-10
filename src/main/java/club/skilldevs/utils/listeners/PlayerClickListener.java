package club.skilldevs.utils.listeners;

import club.skilldevs.utils.PlayerCallable;
import club.skilldevs.utils.events.PlayerAttackPlayerEvent;
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
        injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unInjectPlayer(event.getPlayer());
    }

    ///////////////////////////////////////////////////////////////////////////

    private void addClick(Player p) {
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

    private void injectPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = null;

        try {
            channelDuplexHandler = new ChannelDuplexHandler() {
                @Override
                public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                    //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());
                    if (packet instanceof PacketPlayInArmAnimation) {
                        addClick(player);
                        if (callableMap.containsKey(player.getUniqueId())) callableMap.get(player.getUniqueId()).call(player);
                    }

                    super.channelRead(channelHandlerContext, packet);
                }

                @Override
                public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                    //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET WRITE: " + ChatColor.GREEN + packet.toString());
                    super.write(channelHandlerContext, packet, channelPromise);
                }
            };
        } catch (Exception e) {
            System.out.println("[GlobalBridge] Error al leer los paquetes al jugador " + player.getName());
        }

        try {
            ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (Exception e) {
            System.out.println("[GlobalBridge] Error al enviar los paquetes al jugador " + player.getName());
        }
    }

    private void unInjectPlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

}
