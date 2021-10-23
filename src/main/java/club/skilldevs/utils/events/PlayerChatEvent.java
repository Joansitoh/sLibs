package club.skilldevs.utils.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

@Getter
@Setter
public final class PlayerChatEvent extends Event implements Cancellable {

    private static final HandlerList list = new HandlerList();

    private final ArrayList<Player> receptors;

    private final String message;
    private final Player player;
    private final Object attribute;

    private boolean cancelled;

    public PlayerChatEvent(Player player, String message, Object attribute, ArrayList<Player> receptors) {
        this.player = player;
        this.message = message;
        this.attribute = attribute;
        this.receptors = receptors;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }

}