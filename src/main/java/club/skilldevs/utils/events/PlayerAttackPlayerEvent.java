package club.skilldevs.utils.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public final class PlayerAttackPlayerEvent extends Event implements Cancellable {

    private static final HandlerList list = new HandlerList();

    private final Player player, enemy;
    private double damage;
    private boolean cancelled;

    private boolean usingBow;

    public PlayerAttackPlayerEvent(Player player, Player enemy, double damage) {
        this.player = player;
        this.enemy = enemy;
        this.damage = damage;

        this.cancelled = false;
        this.usingBow = false;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }

}
