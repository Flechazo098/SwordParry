package net.fryc.frycparry.network.payloads;

import net.minecraft.network.PacketByteBuf;

public class SecondConfigAnswerPayload {
    private final int dualWieldingSettings;
    private final int shieldEnchantability;

    public SecondConfigAnswerPayload(int dualWieldingSettings, int shieldEnchantability) {
        this.dualWieldingSettings = dualWieldingSettings;
        this.shieldEnchantability = shieldEnchantability;
    }

    public SecondConfigAnswerPayload(PacketByteBuf buf) {
        this.dualWieldingSettings = buf.readInt();
        this.shieldEnchantability = buf.readInt();
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(this.dualWieldingSettings);
        buf.writeInt(this.shieldEnchantability);
    }

    public int dualWieldingSettings() {
        return dualWieldingSettings;
    }

    public int shieldEnchantability() {
        return shieldEnchantability;
    }
}