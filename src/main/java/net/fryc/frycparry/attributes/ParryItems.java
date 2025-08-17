package net.fryc.frycparry.attributes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public record ParryItems(
        String parryAttributes,
        List<Item> items
) {
    public static final Codec<ParryItems> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("parryAttributes").forGetter(ParryItems::parryAttributes),
            Identifier.CODEC.xmap(
                    Registry.ITEM::get,
                    Registry.ITEM::getId
            ).listOf().fieldOf("items").forGetter(ParryItems::items)
    ).apply(instance, ParryItems::new));
}