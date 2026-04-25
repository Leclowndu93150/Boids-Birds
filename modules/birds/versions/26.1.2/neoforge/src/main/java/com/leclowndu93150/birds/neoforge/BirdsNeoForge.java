package com.leclowndu93150.birds.neoforge;

import com.leclowndu93150.birds.Birds;
import com.leclowndu93150.birds.config.BirdsConfig;
import com.leclowndu93150.birds.entities.bird.Bird;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(Birds.MOD_ID)
public class BirdsNeoForge {
    public static final TagKey<Biome> SPAWNS_BIRDS = TagKey.create(Registries.BIOME, Birds.id("spawns_birds"));

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Birds.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Birds.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Birds.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Birds.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<Bird>> BIRD = ENTITY_TYPES.register("bird",
        () -> EntityType.Builder.of(Bird::new, MobCategory.AMBIENT).sized(1.5f, 0.6f).clientTrackingRange(16).build(ResourceKey.create(Registries.ENTITY_TYPE, Birds.id("bird"))));

    public static final DeferredHolder<Item, Item> BIRD_SPAWN_EGG = ITEMS.register("bird_spawn_egg",
        id -> new SpawnEggItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).spawnEgg(BIRD.get())));

    public static final DeferredHolder<SoundEvent, SoundEvent> BIRD_HURT_SOUND = SOUND_EVENTS.register("entity.bird.hurt",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.hurt")));
    public static final DeferredHolder<SoundEvent, SoundEvent> BIRD_DEATH_SOUND = SOUND_EVENTS.register("entity.bird.death",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.death")));
    public static final DeferredHolder<SoundEvent, SoundEvent> BIRD_FLAP_SOUND = SOUND_EVENTS.register("entity.bird.flap",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.flap")));

    public static final Supplier<CreativeModeTab> BIRDS_TAB = CREATIVE_MODE_TABS.register("birds",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(BIRD_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup.birds.birds"))
            .displayItems((context, entries) -> entries.accept(BIRD_SPAWN_EGG.get()))
            .build());

    public BirdsNeoForge(IEventBus modBus, ModContainer container) {
        ENTITY_TYPES.register(modBus);
        ITEMS.register(modBus);
        SOUND_EVENTS.register(modBus);
        CREATIVE_MODE_TABS.register(modBus);

        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

        modBus.addListener(this::onEntityAttributeCreation);
        modBus.addListener(this::onSpawnPlacementRegister);
        modBus.addListener(this::onBuildCreativeTabContents);

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            BirdsNeoForgeClient.init(modBus, container);
        }
    }

    private void onServerStarting(ServerStartingEvent event) {
        BirdsConfig.HANDLER.load();
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BIRD.get(), Bird.createMobAttributes().build());
        Birds.BIRD = BIRD.get();
        Birds.BIRD_ITEM = BIRD_SPAWN_EGG.get();
    }

    private void onSpawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
        event.register(BIRD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Bird::checkBirdSpawnRule, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BIRD_SPAWN_EGG.get());
        }
    }
}
