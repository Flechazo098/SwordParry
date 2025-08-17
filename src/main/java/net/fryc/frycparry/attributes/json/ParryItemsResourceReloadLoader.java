package net.fryc.frycparry.attributes.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.attributes.ParryItems;
import net.fryc.frycparry.util.ConfigHelper;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.InputStream;
import java.util.Collection;

public class ParryItemsResourceReloadLoader implements SimpleSynchronousResourceReloadListener {
    private static final String PARRY_ITEMS_PATH = "parry_items";

    @Override
    public Identifier getFabricId() {
        return new Identifier(FrycParry.MOD_ID, PARRY_ITEMS_PATH);
    }

    @Override
    public void reload(ResourceManager manager) {
        // setting default parry attributes for all items
        ConfigHelper.reloadDefaultParryEffects();
        for (Item item : Registry.ITEM) {
            if (item instanceof ParryItem parryItem) {
                    parryItem.frycparry_setParryAttributes(ParryAttributes.getDefaultParryAttributes(item));
            }
        }


        // setting parry attributes from datapacks
        Collection<Identifier> resources = manager.findResources(PARRY_ITEMS_PATH, path -> path.endsWith(".json"));
        for (Identifier id : resources) {
            try (InputStream stream = manager.getResource(id).getInputStream()) {
                JsonElement jsonElement = JsonParser.parseString(new String(stream.readAllBytes()));

                ParryItems.CODEC.parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(error -> FrycParry.LOGGER.error("Error parsing parry items from {}: {}", id, error))
                        .ifPresent(parryItems -> {
                            for (Item item : parryItems.items()) {
                                if (item instanceof ParryItem parryItem) {
                                    parryItem.frycparry_setParryAttributes(parryItems.parryAttributes());
                                }
                            }
                            FrycParry.LOGGER.info("Loaded parry items configuration from: {}", id);
                        });

            } catch (Exception e) {
                FrycParry.LOGGER.error("Error occurred while loading resource json {}", id.toString(), e);
            }
        }
    }
}