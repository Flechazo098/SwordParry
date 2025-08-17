package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class FirstConfigAnswerPayload {
    private final boolean enSwordBlocking;
    private final boolean enAxeBlocking;
    private final boolean enPickaxeBlocking;
    private final boolean enShovelBlocking;
    private final boolean enHoeBlocking;
    private final boolean enOtherBlocking;

    public FirstConfigAnswerPayload(boolean enSwordBlocking, boolean enAxeBlocking, boolean enPickaxeBlocking,
                                    boolean enShovelBlocking, boolean enHoeBlocking, boolean enOtherBlocking) {
        this.enSwordBlocking = enSwordBlocking;
        this.enAxeBlocking = enAxeBlocking;
        this.enPickaxeBlocking = enPickaxeBlocking;
        this.enShovelBlocking = enShovelBlocking;
        this.enHoeBlocking = enHoeBlocking;
        this.enOtherBlocking = enOtherBlocking;
    }

    public FirstConfigAnswerPayload(PacketByteBuf buf) {
        this.enSwordBlocking = buf.readBoolean();
        this.enAxeBlocking = buf.readBoolean();
        this.enPickaxeBlocking = buf.readBoolean();
        this.enShovelBlocking = buf.readBoolean();
        this.enHoeBlocking = buf.readBoolean();
        this.enOtherBlocking = buf.readBoolean();
    }

    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.enSwordBlocking);
        buf.writeBoolean(this.enAxeBlocking);
        buf.writeBoolean(this.enPickaxeBlocking);
        buf.writeBoolean(this.enShovelBlocking);
        buf.writeBoolean(this.enHoeBlocking);
        buf.writeBoolean(this.enOtherBlocking);
    }

    public boolean enSwordBlocking() {
        return enSwordBlocking;
    }

    public boolean enAxeBlocking() {
        return enAxeBlocking;
    }

    public boolean enPickaxeBlocking() {
        return enPickaxeBlocking;
    }

    public boolean enShovelBlocking() {
        return enShovelBlocking;
    }

    public boolean enHoeBlocking() {
        return enHoeBlocking;
    }

    public boolean enOtherBlocking() {
        return enOtherBlocking;
    }
}