package net.fryc.frycparry.util.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface ParryInteraction {

    //for ClientPlayerInteractionManager
    ActionResult frycparry_interactItemParry(PlayerEntity player, Hand hand);

    void frycparry_stopUsingItemParry(PlayerEntity player);

}
