package net.fryc.frycparry.tag;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityTypeTags {

    public static final TagKey<EntityType<?>> DISARM_RESISTANT_MOBS = ModEntityTypeTags.of("disarm_resistant_mobs");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(Registry.ENTITY_TYPE.getKey(), new Identifier(FrycParry.MOD_ID, id));
    }

    private ModEntityTypeTags() {
    }

}
