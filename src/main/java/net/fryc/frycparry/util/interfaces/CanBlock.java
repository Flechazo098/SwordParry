package net.fryc.frycparry.util.interfaces;


import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface CanBlock {

    //for LivingEntity
    void frycparry_setBlockingDataToTrue();

    void frycparry_setBlockingDataToFalse();

    boolean frycparry_getBlockingDataValue();

    void frycparry_setParryDataToTrue();

    void frycparry_setParryDataToFalse();

    boolean frycparry_getParryDataValue();

    void frycparry_stopUsingItemParry();

    void frycparry_setCurrentHandParry(Hand hand);

    boolean frycparry_hasParriedRecently();

    void frycparry_setParryTimer(World world, int ticks);

}
