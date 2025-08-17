package net.fryc.frycparry.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.network.payloads.FirstConfigAnswerPayload;
import net.fryc.frycparry.network.payloads.SecondConfigAnswerPayload;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin {

    //informs client about server's config to avoid visual bugs
    @Inject(method = "onPlayerConnected(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void sendConfigToClient(ServerPlayerEntity player, CallbackInfo info) {
        PacketByteBuf buf1 = PacketByteBufs.create();
        new FirstConfigAnswerPayload(
                FrycParry.config.sword.enableBlockingWithSword,
                FrycParry.config.axe.enableBlockingWithAxe,
                FrycParry.config.pickaxe.enableBlockingWithPickaxe,
                FrycParry.config.shovel.enableBlockingWithShovel,
                FrycParry.config.hoe.enableBlockingWithHoe,
                FrycParry.config.server.enableBlockingWithOtherTools
        ).write(buf1);
        ServerPlayNetworking.send(player, ModPackets.FIRST_ANSWER_CONFIG_ID, buf1);

        PacketByteBuf buf2 = PacketByteBufs.create();
        new SecondConfigAnswerPayload(
                FrycParry.config.server.enableBlockingWhenDualWielding,
                FrycParry.config.enchantments.shieldEnchantability
        ).write(buf2);
        ServerPlayNetworking.send(player, ModPackets.SECOND_ANSWER_CONFIG_ID, buf2);
    }
}
