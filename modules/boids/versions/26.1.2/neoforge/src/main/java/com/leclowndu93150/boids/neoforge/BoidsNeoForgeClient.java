package com.leclowndu93150.boids.neoforge;

import com.leclowndu93150.boids.config.BoidsConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class BoidsNeoForgeClient {
    private BoidsNeoForgeClient() {}

    public static void init(ModContainer container) {
        container.registerExtensionPoint(
            IConfigScreenFactory.class,
            (c, parent) -> BoidsConfigScreen.create(parent)
        );
    }
}
