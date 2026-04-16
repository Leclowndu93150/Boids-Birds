package com.leclowndu93150.birds.fabric;

import com.leclowndu93150.birds.Birds;
import com.leclowndu93150.birds.entities.bird.Bird;
import com.leclowndu93150.birds.entities.bird.BirdModel;
import com.leclowndu93150.birds.entities.bird.BirdRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.entity.EntityType;

public class BirdsFabricClient implements ClientModInitializer {
    @Override
    @SuppressWarnings("unchecked")
    public void onInitializeClient() {
        EntityRendererRegistry.register((EntityType<Bird>) Birds.BIRD, BirdRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
    }
}
