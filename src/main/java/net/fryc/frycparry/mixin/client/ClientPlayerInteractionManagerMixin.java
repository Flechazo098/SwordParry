package net.fryc.frycparry.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.network.payloads.StartParryingPayload;
import net.fryc.frycparry.network.payloads.StopBlockingPayload;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryInteraction;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameMode;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
abstract class ClientPlayerInteractionManagerMixin implements ParryInteraction {

    @Shadow
    private GameMode gameMode;
    @Shadow
    private @Final MinecraftClient client;

    @Shadow
    private @Final ClientPlayNetworkHandler networkHandler;

    @Shadow
    private void syncSelectedSlot() {
    }

    @Override
    public ActionResult frycparry_interactItemParry(PlayerEntity player, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        } else {
            this.syncSelectedSlot();
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
            MutableObject<ActionResult> mutableObject = new MutableObject<>();
            ItemStack itemStack = player.getStackInHand(hand);
            if (player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
                mutableObject.setValue(ActionResult.PASS);
                return mutableObject.getValue();
            } else {
                TypedActionResult<ItemStack> typedActionResult;
                if (ParryHelper.isItemParryEnabled(itemStack)) {
                    typedActionResult = ((ParryItem) itemStack.getItem()).frycparry_useParry(this.client.world, player, hand);
                    PacketByteBuf buf = PacketByteBufs.create();
                    new StartParryingPayload(true).write(buf);
                    ClientPlayNetworking.send(ModPackets.START_PARRYING_ID, buf);
                } else {
                    typedActionResult = TypedActionResult.fail(itemStack);
                }
                ItemStack itemStack2 = typedActionResult.getValue();
                if (itemStack2 != itemStack) {
                    player.setStackInHand(hand, itemStack2);
                }

                mutableObject.setValue(typedActionResult.getResult());
            }
            return mutableObject.getValue();
        }
    }

    public void frycparry_stopUsingItemParry(PlayerEntity player) {
        this.syncSelectedSlot();
        PacketByteBuf buf = PacketByteBufs.create();
        new StopBlockingPayload(true).write(buf);
        ClientPlayNetworking.send(ModPackets.STOP_BLOCKING_ID, buf);
        ((CanBlock) player).frycparry_stopUsingItemParry();
    }
}
