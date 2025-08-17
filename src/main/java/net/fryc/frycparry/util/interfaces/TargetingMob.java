package net.fryc.frycparry.util.interfaces;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface TargetingMob {

    void frycparry_setLastTarget(@Nullable LivingEntity target);

    @Nullable
    LivingEntity frycparry_getLastTarget();
}
