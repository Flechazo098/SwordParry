package net.fryc.frycparry.damage;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSources extends DamageSource {

    public ModDamageSources(String typeName) {
        super(typeName);
    }

    public static final DamageSource COUNTERATTACK = new ModDamageSources("counterattack");
}
