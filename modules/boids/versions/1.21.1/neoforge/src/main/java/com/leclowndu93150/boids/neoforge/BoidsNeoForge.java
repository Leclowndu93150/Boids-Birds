package com.leclowndu93150.boids.neoforge;

import com.leclowndu93150.boids.Boids;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.minecraft.commands.Commands.literal;

@Mod(Boids.MOD_ID)
public class BoidsNeoForge {
    public BoidsNeoForge(ModContainer container) {
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            BoidsNeoForgeClient.init(container);
        }
    }

    private void onServerStarting(ServerStartingEvent event) {
        Boids.loadConfig();
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            literal("boids")
                .requires(source -> source.hasPermission(2))
                .then(literal("config").then(literal("reload").executes(ctx -> {
                    Boids.loadConfig();
                    ctx.getSource().sendSuccess(() -> Component.translatable("commands.boids.config.reload"), true);
                    return 1;
                })))
        );
    }
}
