package net.fryc.frycparry.attributes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public record ParryEffect(
        StatusEffect statusEffect,
        int duration,
        int amplifier,
        float chance,
        float enchantmentMultiplier
) {
    public static final Codec<ParryEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.xmap(
                    Registry.STATUS_EFFECT::get,
                    Registry.STATUS_EFFECT::getId
            ).fieldOf("statusEffect").forGetter(ParryEffect::statusEffect),
            Codec.INT.fieldOf("duration").forGetter(ParryEffect::duration),
            Codec.INT.fieldOf("amplifier").forGetter(ParryEffect::amplifier),
            Codec.FLOAT.fieldOf("chance").forGetter(ParryEffect::chance),
            Codec.FLOAT.fieldOf("enchantmentMultiplier").forGetter(ParryEffect::enchantmentMultiplier)
    ).apply(instance, ParryEffect::new));
}