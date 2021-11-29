package club.skilldevs.utils.lunarapi.nethandler.server;

import club.skilldevs.utils.lunarapi.nethandler.client.LCPacketClientVoice;
import club.skilldevs.utils.lunarapi.nethandler.client.LCPacketVoiceChannelSwitch;
import club.skilldevs.utils.lunarapi.nethandler.client.LCPacketVoiceMute;
import club.skilldevs.utils.lunarapi.nethandler.shared.LCNetHandler;

public interface LCNetHandlerServer extends LCNetHandler {

    void handleStaffModStatus(LCPacketStaffModStatus packet);
    void handleVoice(LCPacketClientVoice packet);
    void handleVoiceMute(LCPacketVoiceMute packet);
    void handleVoiceChannelSwitch(LCPacketVoiceChannelSwitch packet);
}