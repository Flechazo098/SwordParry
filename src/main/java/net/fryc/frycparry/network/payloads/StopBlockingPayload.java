package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class StopBlockingPayload {
    private final boolean bl;

    public StopBlockingPayload(boolean bl) {
        this.bl = bl;
    }

    public StopBlockingPayload(PacketByteBuf buf) {
        this.bl = buf.readBoolean();
    }

    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.bl);
    }

    public boolean bl() {
        return bl;
    }
}