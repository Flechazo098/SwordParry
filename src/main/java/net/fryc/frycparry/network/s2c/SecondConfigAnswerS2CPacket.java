package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.network.payloads.SecondConfigAnswerPayload;
import net.fryc.frycparry.util.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SecondConfigAnswerS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        SecondConfigAnswerPayload payload = new SecondConfigAnswerPayload(buf);

        client.execute(() -> {
            ConfigHelper.shieldEnchantability = payload.shieldEnchantability();
            ConfigHelper.dualWieldingSettings = payload.dualWieldingSettings();
        });
    }
}