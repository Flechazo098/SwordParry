package net.fryc.frycparry.attributes.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Collection;

public class ParryAttributesResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private static final String PARRY_ATTRIBUTES_PATH = "parry_attributes";

    @Override
    public Identifier getFabricId() {
        return new Identifier(FrycParry.MOD_ID, PARRY_ATTRIBUTES_PATH);
    }

    @Override
    public void reload(ResourceManager manager) {
        Collection<Identifier> resources = manager.findResources(PARRY_ATTRIBUTES_PATH, path -> path.endsWith(".json"));
        for (Identifier id : resources) {
            try (InputStream stream = manager.getResource(id).getInputStream()) {
                JsonElement jsonElement = JsonParser.parseString(new String(stream.readAllBytes()));
                String fileName = id.getPath().substring(17, id.getPath().length() - 5);

                ParryAttributes.CODEC.parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(error -> FrycParry.LOGGER.error("Error parsing parry attributes from {}: {}", fileName, error))
                        .ifPresent(parryAttributes -> {
                            parryAttributes.addToMap(fileName);
                            FrycParry.LOGGER.info("Loaded parry attributes: {}", fileName);
                        });

            } catch (Exception e) {
                FrycParry.LOGGER.error("Error occurred while loading resource json {}", id.toString(), e);
            }
        }
    }

}