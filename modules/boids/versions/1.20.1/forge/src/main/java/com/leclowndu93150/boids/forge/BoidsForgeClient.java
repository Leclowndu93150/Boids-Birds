package com.leclowndu93150.boids.forge;

import com.leclowndu93150.boids.config.BoidsConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public final class BoidsForgeClient {
    private BoidsForgeClient() {}

    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(BoidsConfigScreen::create)
        );
    }
}
