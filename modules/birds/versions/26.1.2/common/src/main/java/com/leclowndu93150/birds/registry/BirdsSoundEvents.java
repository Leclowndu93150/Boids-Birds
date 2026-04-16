package com.leclowndu93150.birds.registry;

import com.leclowndu93150.birds.Birds;
import net.minecraft.sounds.SoundEvent;

public class BirdsSoundEvents {
    public static SoundEvent BIRD_HURT = SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.hurt"));
    public static SoundEvent BIRD_DEATH = SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.death"));
    public static SoundEvent BIRD_FLAP = SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.flap"));
}
