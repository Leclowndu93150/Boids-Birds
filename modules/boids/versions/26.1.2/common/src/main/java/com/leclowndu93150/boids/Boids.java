package com.leclowndu93150.boids;

import com.leclowndu93150.boids.config.BoidsConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Boids {
    public static final String MOD_ID = "boids";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static BoidsSimulation SETTINGS;
    public static Set<EntityType<?>> AFFECTED_ENTITIES = Collections.emptySet();

    public static void loadConfig() {
        BoidsConfig.HANDLER.load();
        var config = BoidsConfig.HANDLER.instance();

        SETTINGS = new BoidsSimulation(
            config.separationInfluence,
            config.separationRange,
            BoidsSimulation.degrees(config.separationAngle),
            config.alignmentInfluence,
            BoidsSimulation.degrees(config.alignmentAngle),
            config.cohesionInfluence,
            BoidsSimulation.degrees(config.cohesionAngle),
            config.randomness
        );

        AFFECTED_ENTITIES = resolveEntities(config);
    }

    private static Set<EntityType<?>> resolveEntities(BoidsConfig config) {
        var entities = new ArrayList<EntityType<?>>();

        if (config.includeDefaultFish) {
            entities.add(EntityType.SALMON);
            entities.add(EntityType.COD);
            entities.add(EntityType.TROPICAL_FISH);
        }

        for (String id : config.includedEntities) {
            var rl = Identifier.tryParse(id);
            if (rl != null) {
                BuiltInRegistries.ENTITY_TYPE.get(rl).map(Holder.Reference::value).ifPresent(entities::add);
            }
        }

        for (String id : config.excludeEntities) {
            var rl = Identifier.tryParse(id);
            if (rl != null) {
                BuiltInRegistries.ENTITY_TYPE.get(rl).map(Holder.Reference::value).ifPresent(entities::remove);
            }
        }

        return Set.copyOf(entities);
    }

    public static boolean isAffected(Entity entity) {
        return AFFECTED_ENTITIES.contains(entity.getType());
    }
}
