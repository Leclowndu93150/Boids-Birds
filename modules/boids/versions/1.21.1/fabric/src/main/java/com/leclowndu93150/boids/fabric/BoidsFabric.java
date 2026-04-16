package com.leclowndu93150.boids.fabric;

import com.leclowndu93150.boids.Boids;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class BoidsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> Boids.loadConfig());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                literal("boids")
                    .requires(source -> source.hasPermission(2))
                    .then(literal("config").then(literal("reload").executes(ctx -> {
                        Boids.loadConfig();
                        ctx.getSource().sendSuccess(() -> Component.translatable("commands.boids.config.reload"), true);
                        return 1;
                    })))
            )
        );
    }
}
