package com.leclowndu93150.boids.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import com.leclowndu93150.boids.Boids;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BoidsConfigScreen {
    public static Screen create(Screen parent) {
        var defaults = new BoidsConfig();
        var config = BoidsConfig.HANDLER.instance();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("boids.config.title"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("boids.config.category.simulation"))
                .option(floatSlider("separation_influence", 0f, 2f, 0.05f,
                    defaults.separationInfluence, () -> config.separationInfluence, v -> config.separationInfluence = v))
                .option(floatSlider("separation_range", 0f, 10f, 0.1f,
                    defaults.separationRange, () -> config.separationRange, v -> config.separationRange = v))
                .option(floatSlider("separation_angle", 0f, 180f, 1f,
                    defaults.separationAngle, () -> config.separationAngle, v -> config.separationAngle = v))
                .option(floatSlider("alignment_influence", 0f, 2f, 0.05f,
                    defaults.alignmentInfluence, () -> config.alignmentInfluence, v -> config.alignmentInfluence = v))
                .option(floatSlider("alignment_angle", 0f, 180f, 1f,
                    defaults.alignmentAngle, () -> config.alignmentAngle, v -> config.alignmentAngle = v))
                .option(floatSlider("cohesion_influence", 0f, 2f, 0.05f,
                    defaults.cohesionInfluence, () -> config.cohesionInfluence, v -> config.cohesionInfluence = v))
                .option(floatSlider("cohesion_angle", 0f, 180f, 1f,
                    defaults.cohesionAngle, () -> config.cohesionAngle, v -> config.cohesionAngle = v))
                .option(floatSlider("randomness", 0f, 0.1f, 0.001f,
                    defaults.randomness, () -> config.randomness, v -> config.randomness = v))
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("boids.config.category.speed"))
                .option(floatSlider("min_speed", 0f, 2f, 0.05f,
                    defaults.minSpeed, () -> config.minSpeed, v -> config.minSpeed = v))
                .option(floatSlider("max_speed", 0f, 2f, 0.05f,
                    defaults.maxSpeed, () -> config.maxSpeed, v -> config.maxSpeed = v))
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("boids.config.category.entities"))
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("boids.config.include_default_fish"))
                    .description(OptionDescription.of(Component.translatable("boids.config.include_default_fish.desc")))
                    .controller(TickBoxControllerBuilder::create)
                    .binding(defaults.includeDefaultFish, () -> config.includeDefaultFish, v -> config.includeDefaultFish = v)
                    .build())
                .build())
            .save(() -> {
                BoidsConfig.HANDLER.save();
                Boids.loadConfig();
            })
            .build()
            .generateScreen(parent);
    }

    private static Option<Float> floatSlider(String key, float min, float max, float step,
                                             float defaultVal, Supplier<Float> getter,
                                             Consumer<Float> setter) {
        return Option.<Float>createBuilder()
            .name(Component.translatable("boids.config." + key))
            .description(OptionDescription.of(Component.translatable("boids.config." + key + ".desc")))
            .controller(opt -> FloatSliderControllerBuilder.create(opt).range(min, max).step(step))
            .binding(defaultVal, getter, setter)
            .build();
    }
}
