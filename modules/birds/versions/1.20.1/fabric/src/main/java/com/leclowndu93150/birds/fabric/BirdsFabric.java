package com.leclowndu93150.birds.fabric;

import com.leclowndu93150.birds.Birds;
import com.leclowndu93150.birds.config.BirdsConfig;
import com.leclowndu93150.birds.entities.bird.Bird;
import com.leclowndu93150.birds.registry.BirdsSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class BirdsFabric implements ModInitializer {
    public static final TagKey<Biome> SPAWNS_BIRDS = TagKey.create(Registries.BIOME, Birds.id("spawns_birds"));

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> BirdsConfig.HANDLER.load());

        EntityType<Bird> birdType = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Birds.id("bird"),
            FabricEntityTypeBuilder.create(MobCategory.AMBIENT, Bird::new).dimensions(EntityDimensions.fixed(1.5f, 0.6f)).trackRangeChunks(16).build()
        );
        Birds.BIRD = birdType;

        Item birdItem = new SpawnEggItem(birdType, 0xFF4D3927, 0xFF7D706C, new Item.Properties());
        Registry.register(BuiltInRegistries.ITEM, Birds.id("bird_spawn_egg"), birdItem);
        Birds.BIRD_ITEM = birdItem;

        FabricDefaultAttributeRegistry.register(birdType, Bird.createMobAttributes());

        BiomeModifications.addSpawn(BiomeSelectors.tag(SPAWNS_BIRDS), MobCategory.AMBIENT, birdType, 50, 7, 10);
        SpawnPlacements.register(birdType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Bird::checkAnimalSpawnRules);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> {
            content.accept(birdItem);
        });

        CreativeModeTab birdsTab = FabricItemGroup.builder()
            .icon(() -> new ItemStack(birdItem))
            .title(Component.translatable("itemGroup.birds.birds"))
            .displayItems((context, entries) -> {
                entries.accept(birdItem);
            })
            .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Birds.id("birds"), birdsTab);

        registerSoundEvent(BirdsSoundEvents.BIRD_HURT);
        registerSoundEvent(BirdsSoundEvents.BIRD_DEATH);
        registerSoundEvent(BirdsSoundEvents.BIRD_FLAP);
    }

    private static void registerSoundEvent(SoundEvent soundEvent) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.getLocation(), soundEvent);
    }
}
