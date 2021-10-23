package club.skilldevs.utils.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
@Setter
public final class PlayerAuthenticateEvent extends Event {

    private static final HandlerList list = new HandlerList();

    private final UUID uniqueId;
    private final Player player;

    public PlayerAuthenticateEvent(Player player) {
        this.player = player;
        this.uniqueId = player.getUniqueId();
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }

}
