package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class StartParryingPayload {
    private final boolean bl;

    public StartParryingPayload(boolean bl) {
        this.bl = bl;
    }

    public StartParryingPayload(PacketByteBuf buf) {
        this.bl = buf.readBoolean();
    }

    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.bl);
    }

    public boolean bl() {
        return bl;
    }
}