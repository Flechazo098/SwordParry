package net.fryc.frycparry.util.interfaces;

import net.fryc.frycparry.attributes.ParryAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public interface ParryItem {

    void frycparry_setParryAttributes(String parryAttributesId);

    void frycparry_setParryAttributes(ParryAttributes parryAttributes);

    ParryAttributes frycparry_getParryAttributes();

    UseAction frycparry_getUseParryAction(ItemStack stack);


    TypedActionResult<ItemStack> frycparry_useParry(World world, PlayerEntity user, Hand hand);

    void frycparry_onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks);

    ItemStack frycparry_finishUsingParry(ItemStack stack, World world, LivingEntity user);

}
