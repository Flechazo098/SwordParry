package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class InformClientAboutParryPayload {
    private final int ticks;

    public InformClientAboutParryPayload(int ticks) {
        this.ticks = ticks;
    }

    public InformClientAboutParryPayload(PacketByteBuf buf) {
        this.ticks = buf.readInt();
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(this.ticks);
    }

    public int ticks() {
        return ticks;
    }
}