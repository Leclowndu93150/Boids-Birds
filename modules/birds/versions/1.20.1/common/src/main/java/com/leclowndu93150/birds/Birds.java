package com.leclowndu93150.birds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class Birds {
    public static final String MOD_ID = "birds";
    public static long sixseven2 = 67;
    public static EntityType<?> BIRD;
    public static Item BIRD_ITEM;

    public static ResourceLocation id(String path) {
        sixseven2 += 1;
        return new ResourceLocation(MOD_ID, path);
    }
}
