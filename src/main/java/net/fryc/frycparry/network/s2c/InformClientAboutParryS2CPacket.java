package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.network.payloads.InformClientAboutParryPayload;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class InformClientAboutParryS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        InformClientAboutParryPayload payload = new InformClientAboutParryPayload(buf);

        client.execute(() -> {
            ClientPlayerEntity player = client.player;
            if (player != null) {
                ((CanBlock) player).frycparry_setParryTimer(player.getWorld(), payload.ticks());
            }
        });
    }
}