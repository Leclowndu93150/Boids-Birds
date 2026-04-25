package com.leclowndu93150.boids.forge;

import com.leclowndu93150.boids.Boids;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static net.minecraft.commands.Commands.literal;

@Mod(Boids.MOD_ID)
public class BoidsForge {
    public BoidsForge() {
        MinecraftForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            BoidsForgeClient.init();
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        Boids.loadConfig();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
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
