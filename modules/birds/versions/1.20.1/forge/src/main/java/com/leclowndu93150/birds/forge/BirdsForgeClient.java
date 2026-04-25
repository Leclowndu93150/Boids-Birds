package com.leclowndu93150.birds.forge;

import com.leclowndu93150.birds.config.BirdsConfigScreen;
import com.leclowndu93150.birds.entities.bird.BirdModel;
import com.leclowndu93150.birds.entities.bird.BirdRenderer;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

public final class BirdsForgeClient {
    private BirdsForgeClient() {}

    public static void init(IEventBus modBus) {
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(BirdsConfigScreen::create)
        );

        modBus.addListener(BirdsForgeClient::onRegisterRenderers);
        modBus.addListener(BirdsForgeClient::onRegisterLayerDefinitions);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BirdsForge.BIRD.get(), BirdRenderer::new);
    }

    private static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
    }
}
