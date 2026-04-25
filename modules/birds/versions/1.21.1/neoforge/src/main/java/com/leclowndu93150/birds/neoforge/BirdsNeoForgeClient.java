package com.leclowndu93150.birds.neoforge;

import com.leclowndu93150.birds.config.BirdsConfigScreen;
import com.leclowndu93150.birds.entities.bird.BirdModel;
import com.leclowndu93150.birds.entities.bird.BirdRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class BirdsNeoForgeClient {
    private BirdsNeoForgeClient() {}

    public static void init(IEventBus modBus, ModContainer container) {
        container.registerExtensionPoint(
            IConfigScreenFactory.class,
            (c, parent) -> BirdsConfigScreen.create(parent)
        );

        modBus.addListener(BirdsNeoForgeClient::onRegisterRenderers);
        modBus.addListener(BirdsNeoForgeClient::onRegisterLayerDefinitions);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BirdsNeoForge.BIRD.get(), BirdRenderer::new);
    }

    private static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
    }
}
