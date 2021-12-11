package club.skilldevs.utils.nms.ver;

import club.skilldevs.utils.listeners.PlayerClickListener;
import club.skilldevs.utils.nms.sNMSHandler;
import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Joansiitoh (DragonsTeam  SkillTeam)
 * Date: 11/12/2021 - 17:41.
 */
public class v1_8_R3 implements sNMSHandler {

    @Override
    public void injectPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = null;

        try {
            channelDuplexHandler = new ChannelDuplexHandler() {
                @Override
                public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                    //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());
                    if (packet instanceof PacketPlayInArmAnimation) {
                        PlayerClickListener.addClick(player);
                        if (PlayerClickListener.getCallableMap().containsKey(player.getUniqueId())) PlayerClickListener.getCallableMap().get(player.getUniqueId()).call(player);
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

    @Override
    public void unInjectPlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }
}
