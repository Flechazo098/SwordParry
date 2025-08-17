package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class ResetLastAttackedTicksPayload {
    private final boolean bl;

    public ResetLastAttackedTicksPayload(boolean bl) {
        this.bl = bl;
    }

    public ResetLastAttackedTicksPayload(PacketByteBuf buf) {
        this.bl = buf.readBoolean();
    }

    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.bl);
    }

    public boolean bl() {
        return bl;
    }
}