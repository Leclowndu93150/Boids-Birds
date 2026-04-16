package com.leclowndu93150.birds.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;

public class BirdsConfig {
    public static final ConfigClassHandler<BirdsConfig> HANDLER = ConfigClassHandler.createBuilder(BirdsConfig.class)
        .id(ResourceLocation.fromNamespaceAndPath("birds", "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(getConfigPath())
            .setJson5(true)
            .build())
        .build();

    @SerialEntry public float heightOffset = 15f;
    @SerialEntry public float heightRange = 120f;

    @SerialEntry public float separationInfluence = 0.1f;
    @SerialEntry public float separationRange = 2f;
    @SerialEntry public float alignmentInfluence = 0.4f;
    @SerialEntry public float cohesionInfluence = 0.04f;

    @SerialEntry public float minSpeed = 0.3f;
    @SerialEntry public float maxSpeed = 0.7f;

    @SerialEntry public int spawnWeight = 50;
    @SerialEntry public int spawnMinGroup = 7;
    @SerialEntry public int spawnMaxGroup = 10;

    private static Path getConfigPath() {
        return Path.of("config", "birds.json5");
    }
}
