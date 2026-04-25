package com.leclowndu93150.birds.forge;

import com.leclowndu93150.birds.Birds;
import com.leclowndu93150.birds.config.BirdsConfig;
import com.leclowndu93150.birds.entities.bird.Bird;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Birds.MOD_ID)
public class BirdsForge {
    public static final TagKey<Biome> SPAWNS_BIRDS = TagKey.create(Registries.BIOME, Birds.id("spawns_birds"));

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Birds.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Birds.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Birds.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Birds.MOD_ID);

    public static final RegistryObject<EntityType<Bird>> BIRD = ENTITY_TYPES.register("bird",
        () -> EntityType.Builder.<Bird>of(Bird::new, MobCategory.AMBIENT).sized(1.5f, 0.6f).clientTrackingRange(16).build("bird"));

    public static final RegistryObject<Item> BIRD_SPAWN_EGG = ITEMS.register("bird_spawn_egg",
        () -> new ForgeSpawnEggItem(BIRD, 0xFF4D3927, 0xFF7D706C, new Item.Properties()));

    public static final RegistryObject<SoundEvent> BIRD_HURT_SOUND = SOUND_EVENTS.register("entity.bird.hurt",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.hurt")));
    public static final RegistryObject<SoundEvent> BIRD_DEATH_SOUND = SOUND_EVENTS.register("entity.bird.death",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.death")));
    public static final RegistryObject<SoundEvent> BIRD_FLAP_SOUND = SOUND_EVENTS.register("entity.bird.flap",
        () -> SoundEvent.createVariableRangeEvent(Birds.id("entity.bird.flap")));

    public static final RegistryObject<CreativeModeTab> BIRDS_TAB = CREATIVE_MODE_TABS.register("birds",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(BIRD_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup.birds.birds"))
            .displayItems((context, entries) -> entries.accept(BIRD_SPAWN_EGG.get()))
            .build());

    public BirdsForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITY_TYPES.register(modBus);
        ITEMS.register(modBus);
        SOUND_EVENTS.register(modBus);
        CREATIVE_MODE_TABS.register(modBus);

        MinecraftForge.EVENT_BUS.register(this);

        modBus.addListener(this::onEntityAttributeCreation);
        modBus.addListener(this::onSpawnPlacementRegister);
        modBus.addListener(this::onBuildCreativeTabContents);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            BirdsForgeClient.init(modBus);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        BirdsConfig.HANDLER.load();
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BIRD.get(), Bird.createMobAttributes().build());
        Birds.BIRD = BIRD.get();
        Birds.BIRD_ITEM = BIRD_SPAWN_EGG.get();
    }

    private void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(BIRD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Bird::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BIRD_SPAWN_EGG.get());
        }
    }
}
