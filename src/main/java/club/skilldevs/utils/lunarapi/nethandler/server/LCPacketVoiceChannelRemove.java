package club.skilldevs.utils.lunarapi.nethandler.server;

import club.skilldevs.utils.lunarapi.nethandler.ByteBufWrapper;
import club.skilldevs.utils.lunarapi.nethandler.LCPacket;
import club.skilldevs.utils.lunarapi.nethandler.client.LCNetHandlerClient;
import club.skilldevs.utils.lunarapi.nethandler.shared.LCNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public final class LCPacketVoiceChannelRemove extends LCPacket {

    @Getter private UUID uuid;

    @Override
    public void write(ByteBufWrapper b) {
        b.writeUUID(uuid);
    }

    @Override
    public void read(ByteBufWrapper b) {
        this.uuid = b.readUUID();
    }

    @Override
    public void process(LCNetHandler handler) {
        ((LCNetHandlerClient) handler).handleVoiceChannelDelete(this);
    }
}
