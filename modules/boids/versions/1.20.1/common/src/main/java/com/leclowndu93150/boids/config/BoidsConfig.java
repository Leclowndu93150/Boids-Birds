package com.leclowndu93150.boids.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BoidsConfig {
    public static final ConfigClassHandler<BoidsConfig> HANDLER = ConfigClassHandler.createBuilder(BoidsConfig.class)
        .id(new ResourceLocation("boids", "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(getConfigPath())
            .setJson5(true)
            .build())
        .build();

    @SerialEntry public float separationInfluence = 0.6f;
    @SerialEntry public float separationRange = 2.5f;
    @SerialEntry public float separationAngle = 70f;

    @SerialEntry public float alignmentInfluence = 0.4f;
    @SerialEntry public float alignmentAngle = 100f;

    @SerialEntry public float cohesionInfluence = 0.4f;
    @SerialEntry public float cohesionAngle = 70f;

    @SerialEntry public float minSpeed = 0.2f;
    @SerialEntry public float maxSpeed = 0.3f;

    @SerialEntry public float randomness = 0.005f;

    @SerialEntry public boolean includeDefaultFish = true;
    @SerialEntry public List<String> includedEntities = new ArrayList<>();
    @SerialEntry public List<String> excludeEntities = new ArrayList<>();

    private static Path getConfigPath() {
        return Path.of("config", "boids.json5");
    }
}
