package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.payloads.ResetLastAttackedTicksPayload;
import net.fryc.frycparry.network.payloads.StartParryingPayload;
import net.fryc.frycparry.network.payloads.StopBlockingPayload;
import net.fryc.frycparry.network.s2c.FirstConfigAnswerS2CPacket;
import net.fryc.frycparry.network.s2c.InformClientAboutParryS2CPacket;
import net.fryc.frycparry.network.s2c.SecondConfigAnswerS2CPacket;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.OnParryInteraction;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier STOP_BLOCKING_ID = new Identifier(FrycParry.MOD_ID, "stop_blocking_id");
    public static final Identifier START_PARRYING_ID = new Identifier(FrycParry.MOD_ID, "start_parrying_id");
    public static final Identifier FIRST_ANSWER_CONFIG_ID = new Identifier(FrycParry.MOD_ID, "first_answer_config_id");
    public static final Identifier SECOND_ANSWER_CONFIG_ID = new Identifier(FrycParry.MOD_ID, "second_answer_config_id");
    public static final Identifier RESET_LAST_ATTACKED_TICKS_ID = new Identifier(FrycParry.MOD_ID, "reset_last_attacked_ticks_id");
    public static final Identifier INFORM_CLIENT_ABOUT_PARRY_ID = new Identifier(FrycParry.MOD_ID, "inform_client_about_parry_id");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(
                STOP_BLOCKING_ID,
                (server, player, handler, buf, responseSender) -> {
                    StopBlockingPayload payload = new StopBlockingPayload(buf);
                    server.execute(() -> {
                        player.updateLastActionTime();
                        ((CanBlock) player).frycparry_stopUsingItemParry();
                    });
                });

        ServerPlayNetworking.registerGlobalReceiver(
                START_PARRYING_ID,
                (server, player, handler, buf, responseSender) -> {
                    StartParryingPayload payload = new StartParryingPayload(buf);
                    server.execute(() -> {
                        ((OnParryInteraction) player.networkHandler).frycparry_onPlayerInteractItemParry(player, player.getWorld(), Hand.MAIN_HAND);
                    });
                });

        ServerPlayNetworking.registerGlobalReceiver(
                RESET_LAST_ATTACKED_TICKS_ID,
                (server, player, handler, buf, responseSender) -> {
                    ResetLastAttackedTicksPayload payload = new ResetLastAttackedTicksPayload(buf);
                    server.execute(player::resetLastAttackedTicks);
                });
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(
                FIRST_ANSWER_CONFIG_ID,
                (client, handler, buf, responseSender) -> {
                    FirstConfigAnswerS2CPacket.receive(client, null, buf, null);
                });

        ClientPlayNetworking.registerGlobalReceiver(
                SECOND_ANSWER_CONFIG_ID,
                (client, handler, buf, responseSender) -> {
                    SecondConfigAnswerS2CPacket.receive(client, null, buf, null);
                });

        ClientPlayNetworking.registerGlobalReceiver(
                INFORM_CLIENT_ABOUT_PARRY_ID,
                (client, handler, buf, responseSender) -> {
                    InformClientAboutParryS2CPacket.receive(client, null, buf, null);
                });
    }

    public static void sendToServer(Identifier id, PacketByteBuf buf) {
        ClientPlayNetworking.send(id, buf);
    }

    public static void sendToClient(ServerPlayerEntity player, Identifier id, PacketByteBuf buf) {
        ServerPlayNetworking.send(player, id, buf);
    }
}