package club.skilldevs.utils.lunarapi.nethandler.shared;

public interface LCNetHandler {

    void handleAddWaypoint(LCPacketWaypointAdd packet);
    void handleRemoveWaypoint(LCPacketWaypointRemove packet);
    void handleEmote(LCPacketEmoteBroadcast packet);

}