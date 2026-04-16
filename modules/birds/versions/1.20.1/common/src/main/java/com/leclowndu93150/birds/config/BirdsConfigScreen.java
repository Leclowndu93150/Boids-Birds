package com.leclowndu93150.birds.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BirdsConfigScreen {
    public static Screen create(Screen parent) {
        var defaults = new BirdsConfig();
        var config = BirdsConfig.HANDLER.instance();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("birds.config.title"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("birds.config.category.flight"))
                .option(floatSlider("height_offset", 0f, 64f, 1f,
                    defaults.heightOffset, () -> config.heightOffset, v -> config.heightOffset = v))
                .option(floatSlider("height_range", 0f, 256f, 1f,
                    defaults.heightRange, () -> config.heightRange, v -> config.heightRange = v))
                .option(floatSlider("min_speed", 0f, 2f, 0.05f,
                    defaults.minSpeed, () -> config.minSpeed, v -> config.minSpeed = v))
                .option(floatSlider("max_speed", 0f, 2f, 0.05f,
                    defaults.maxSpeed, () -> config.maxSpeed, v -> config.maxSpeed = v))
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("birds.config.category.flocking"))
                .option(floatSlider("separation_influence", 0f, 2f, 0.01f,
                    defaults.separationInfluence, () -> config.separationInfluence, v -> config.separationInfluence = v))
                .option(floatSlider("separation_range", 0f, 10f, 0.1f,
                    defaults.separationRange, () -> config.separationRange, v -> config.separationRange = v))
                .option(floatSlider("alignment_influence", 0f, 2f, 0.01f,
                    defaults.alignmentInfluence, () -> config.alignmentInfluence, v -> config.alignmentInfluence = v))
                .option(floatSlider("cohesion_influence", 0f, 2f, 0.01f,
                    defaults.cohesionInfluence, () -> config.cohesionInfluence, v -> config.cohesionInfluence = v))
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("birds.config.category.spawning"))
                .option(intSlider("spawn_weight", 0, 200, 1,
                    defaults.spawnWeight, () -> config.spawnWeight, v -> config.spawnWeight = v))
                .option(intSlider("spawn_min_group", 1, 30, 1,
                    defaults.spawnMinGroup, () -> config.spawnMinGroup, v -> config.spawnMinGroup = v))
                .option(intSlider("spawn_max_group", 1, 30, 1,
                    defaults.spawnMaxGroup, () -> config.spawnMaxGroup, v -> config.spawnMaxGroup = v))
                .build())
            .save(() -> BirdsConfig.HANDLER.save())
            .build()
            .generateScreen(parent);
    }

    private static Option<Float> floatSlider(String key, float min, float max, float step,
                                             float defaultVal, Supplier<Float> getter,
                                             Consumer<Float> setter) {
        return Option.<Float>createBuilder()
            .name(Component.translatable("birds.config." + key))
            .description(OptionDescription.of(Component.translatable("birds.config." + key + ".desc")))
            .controller(opt -> FloatSliderControllerBuilder.create(opt).range(min, max).step(step))
            .binding(defaultVal, getter, setter)
            .build();
    }

    private static Option<Integer> intSlider(String key, int min, int max, int step,
                                             int defaultVal, Supplier<Integer> getter,
                                             Consumer<Integer> setter) {
        return Option.<Integer>createBuilder()
            .name(Component.translatable("birds.config." + key))
            .description(OptionDescription.of(Component.translatable("birds.config." + key + ".desc")))
            .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(min, max).step(step))
            .binding(defaultVal, getter, setter)
            .build();
    }
}
