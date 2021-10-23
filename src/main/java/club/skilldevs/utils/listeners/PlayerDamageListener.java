package club.skilldevs.utils.listeners;

import club.skilldevs.utils.events.PlayerAttackPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Joansiitoh (DragonsTeam &amp;&amp; SkillTeam)
 * Date: 28/09/2021 - 13:40.
 */
public class PlayerDamageListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerReceiveDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity(), enemy;
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                enemy = Bukkit.getPlayer(((Player) arrow.getShooter()).getUniqueId());

                PlayerAttackPlayerEvent e = new PlayerAttackPlayerEvent(player, enemy, event.getFinalDamage());
                e.setUsingBow(true);

                Bukkit.getPluginManager().callEvent(e);
                if (e.isCancelled()) event.setCancelled(true);
                return;
            }
        }

        if (event.getDamager() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getDamager();
            if (wolf.getOwner() == null || (!(wolf.getOwner() instanceof Player))) return;
            enemy = Bukkit.getPlayer(((Player) wolf.getOwner()).getUniqueId());

            PlayerAttackPlayerEvent e = new PlayerAttackPlayerEvent(player, enemy, event.getFinalDamage());
            Bukkit.getPluginManager().callEvent(e);

            if (e.isCancelled()) event.setCancelled(true);
            return;
        }

        if (event.getDamager() instanceof Player) {
            enemy = Bukkit.getPlayer(event.getDamager().getUniqueId());

            PlayerAttackPlayerEvent e = new PlayerAttackPlayerEvent(player, enemy, event.getFinalDamage());
            Bukkit.getPluginManager().callEvent(e);

            if (e.isCancelled()) event.setCancelled(true);
        }
    }

}
