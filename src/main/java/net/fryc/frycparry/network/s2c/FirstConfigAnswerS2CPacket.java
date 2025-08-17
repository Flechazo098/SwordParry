package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.network.payloads.FirstConfigAnswerPayload;
import net.fryc.frycparry.util.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class FirstConfigAnswerS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        FirstConfigAnswerPayload payload = new FirstConfigAnswerPayload(buf);

        client.execute(() -> {
            ConfigHelper.enableBlockingWithSword = payload.enSwordBlocking();
            ConfigHelper.enableBlockingWithAxe = payload.enAxeBlocking();
            ConfigHelper.enableBlockingWithPickaxe = payload.enPickaxeBlocking();
            ConfigHelper.enableBlockingWithShovel = payload.enShovelBlocking();
            ConfigHelper.enableBlockingWithHoe = payload.enHoeBlocking();
            ConfigHelper.enableBlockingWithOtherTools = payload.enOtherBlocking();
        });
    }
}