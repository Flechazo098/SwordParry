package net.fryc.frycparry.mite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class MiteLivingEntityMixin {
    @Shadow
    protected ItemStack activeItemStack;


    @SuppressWarnings({"InvalidInjectorMethodSignature", "MixinAnnotationTarget"})
    @WrapOperation(method = "damage", at = {
//            //For user side obfuscated environment
//            @At(value = "INVOKE", target = "Lnet/minecraft/class_1297;canKnockBackTarget(Lnet/minecraft/class_1309;)Z", remap = false),
            //For dev side environment
            @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;canKnockBackTarget(Lnet/minecraft/entity/LivingEntity;)Z", remap = false)}, expect = 1, require = 1)
    private boolean redirectFixSwordBlockDamage(Entity instance, LivingEntity livingEntity, Operation<Boolean> original) {
        if (!(this.activeItemStack.getItem() instanceof ShieldItem)) {
            return false;
        }
        return original.call(instance, livingEntity);
    }
}
