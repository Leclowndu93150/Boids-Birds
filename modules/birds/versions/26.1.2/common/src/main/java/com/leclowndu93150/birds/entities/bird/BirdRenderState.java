package com.leclowndu93150.birds.entities.bird;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class BirdRenderState extends LivingEntityRenderState {
    public final AnimationState flyAnimationState = new AnimationState();
}
