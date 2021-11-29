package club.skilldevs.utils.lunarapi.nethandler.client;

import club.skilldevs.utils.lunarapi.nethandler.ByteBufWrapper;
import club.skilldevs.utils.lunarapi.nethandler.LCPacket;
import club.skilldevs.utils.lunarapi.nethandler.server.LCNetHandlerServer;
import club.skilldevs.utils.lunarapi.nethandler.shared.LCNetHandler;
import lombok.Getter;

import java.io.IOException;
import java.util.UUID;

public final class LCPacketVoiceMute extends LCPacket {

    @Getter private UUID muting;

    @Getter private int volume;

    public LCPacketVoiceMute() {
    }

    public LCPacketVoiceMute(UUID muting) {
        this(muting, 0);
    }

    public LCPacketVoiceMute(UUID muting, int volume) {
        this.muting = muting;
        this.volume = volume;
    }

    @Override
    public void write(ByteBufWrapper b) throws IOException {
        b.writeUUID(muting);
        b.writeVarInt(volume);
    }

    @Override
    public void read(ByteBufWrapper b) throws IOException {
        this.muting = b.readUUID();
        this.volume = b.readVarInt();
    }

    @Override
    public void process(LCNetHandler handler) {
        ((LCNetHandlerServer) handler).handleVoiceMute(this);
    }
}
