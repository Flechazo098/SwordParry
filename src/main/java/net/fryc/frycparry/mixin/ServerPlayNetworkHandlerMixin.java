package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.interfaces.OnParryInteraction;
import net.fryc.frycparry.util.interfaces.ServerParryInteraction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayNetworkHandler.class)
abstract class ServerPlayNetworkHandlerMixin implements ServerPlayPacketListener, OnParryInteraction {
    @Override
    public void frycparry_onPlayerInteractItemParry(ServerPlayerEntity player, ServerWorld world, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.updateLastActionTime();
        if (!itemStack.isEmpty()) {
            ((ServerParryInteraction) player.interactionManager).frycparry_interactItemParry(player, world, itemStack, hand);
        }
    }
}