package com.leclowndu93150.boids;

import com.leclowndu93150.boids.config.BoidsConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

    private static final List<String> FISH_PATTERNS = List.of(
        "*cod",
        "*salmon",
        "*trout",
        "*bass",
        "*carp",
        "*catfish",
        "*perch",
        "*pike",
        "*walleye",
        "*bluegill",
        "*crappie",
        "*sturgeon",
        "*gar",
        "*minnow",
        "*herring",
        "*anchovy",
        "*sardine",
        "*mackerel",
        "*tuna",
        "*swordfish",
        "*marlin",
        "*halibut",
        "*flounder",
        "*sole",
        "*plaice",
        "*pollock",
        "*haddock",
        "*grouper",
        "*snapper",
        "*barracuda",
        "*tilapia",
        "*piranha",
        "*arapaima",
        "*coelacanth",
        "*anglerfish",
        "*pufferfish",
        "*blowfish",
        "*clownfish",
        "*fish",
        "*muskellunge",
        "*tambaqui",
        "*boulti",
        "*bayad",
        "*capitaine",
        "*synodontis",
        "*shrooma"
    );

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

        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            ResourceLocation rl = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            String path = rl.getPath();
            for (String pattern : FISH_PATTERNS) {
                if (matchesWildcard(pattern, path)) {
                    entities.add(type);
                    break;
                }
            }
        }

        for (String id : config.includedEntities) {
            var rl = ResourceLocation.tryParse(id);
            if (rl != null) {
                BuiltInRegistries.ENTITY_TYPE.getOptional(rl).ifPresent(entities::add);
            }
        }

        for (String id : config.excludeEntities) {
            var rl = ResourceLocation.tryParse(id);
            if (rl != null) {
                BuiltInRegistries.ENTITY_TYPE.getOptional(rl).ifPresent(entities::remove);
            }
        }

        return Set.copyOf(entities);
    }

    public static boolean isAffected(Entity entity) {
        return AFFECTED_ENTITIES.contains(entity.getType());
    }

    private static boolean matchesWildcard(String pattern, String text) {
        if (pattern.startsWith("*") && pattern.endsWith("*")) {
            return text.contains(pattern.substring(1, pattern.length() - 1));
        } else if (pattern.startsWith("*")) {
            return text.endsWith(pattern.substring(1));
        } else if (pattern.endsWith("*")) {
            return text.startsWith(pattern.substring(0, pattern.length() - 1));
        }
        return text.equals(pattern);
    }
}
