package club.skilldevs.utils.lunarapi.nethandler.client;

import club.skilldevs.utils.lunarapi.nethandler.ByteBufWrapper;
import club.skilldevs.utils.lunarapi.nethandler.LCPacket;
import club.skilldevs.utils.lunarapi.nethandler.server.LCNetHandlerServer;
import club.skilldevs.utils.lunarapi.nethandler.shared.LCNetHandler;
import lombok.Getter;

import java.io.IOException;
import java.util.UUID;

public final class LCPacketVoiceChannelSwitch extends LCPacket {

    @Getter private UUID switchingTo;

    public LCPacketVoiceChannelSwitch() {
    }

    public LCPacketVoiceChannelSwitch(UUID switchingTo) {
        this.switchingTo = switchingTo;
    }

    @Override
    public void write(ByteBufWrapper b) throws IOException {
        b.writeUUID(switchingTo);
    }

    @Override
    public void read(ByteBufWrapper b) throws IOException {
        this.switchingTo = b.readUUID();
    }

    @Override
    public void process(LCNetHandler handler) {
        ((LCNetHandlerServer) handler).handleVoiceChannelSwitch(this);
    }
}
