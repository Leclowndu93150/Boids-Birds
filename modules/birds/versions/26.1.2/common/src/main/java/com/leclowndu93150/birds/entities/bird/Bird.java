package com.leclowndu93150.birds.entities.bird;

import com.leclowndu93150.birds.config.BirdsConfig;
import com.leclowndu93150.birds.registry.BirdsSoundEvents;
import com.leclowndu93150.birds.goals.DynamicHeightBoundsGoal;
import com.leclowndu93150.boids.BoidsSimulation;
import com.leclowndu93150.boids.goals.BoidGoal;
import com.leclowndu93150.boids.goals.LimitSpeedAndLookInVelocityDirectionGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

public class Bird extends Mob {
    public final AnimationState flyAnimationState = new AnimationState();
    float flapCooldownTick = 0;
    final int flapOffset;

    public Bird(EntityType<Bird> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        flapOffset = getRandom().nextInt(0, 10);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0);
    }

    protected void registerGoals() {
        var config = BirdsConfig.HANDLER.instance();
        this.goalSelector.addGoal(1, new DynamicHeightBoundsGoal(this, config.heightOffset, config.heightRange));

        var simulation = new BoidsSimulation(
            config.separationInfluence,
            config.separationRange,
            Float.NEGATIVE_INFINITY,
            config.alignmentInfluence,
            Float.NEGATIVE_INFINITY,
            config.cohesionInfluence,
            Float.NEGATIVE_INFINITY,
            0.04f
        );

        this.goalSelector.addGoal(2, new BoidGoal(this, simulation));
        this.goalSelector.addGoal(3, new LimitSpeedAndLookInVelocityDirectionGoal(this, config.minSpeed, config.maxSpeed));
    }

    public static boolean checkBirdSpawnRule(EntityType<?> entityType, LevelAccessor levelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        boolean bl = EntitySpawnReason.ignoresLightRequirements(entitySpawnReason) || isBrightEnoughToSpawn(levelAccessor, blockPos);
        return levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && bl;
    }

    protected static boolean isBrightEnoughToSpawn(LevelAccessor levelAccessor, BlockPos blockPos) {
        return levelAccessor.getRawBrightness(blockPos, 0) > 8;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 65536;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return distance > 65536;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(256);
    }

    @Override
    public void tick() {
        super.tick();
        setNoGravity(true);

        var animationTime = (tickCount + flapOffset) % (20 * 0.5F);
        if (animationTime < 4 && animationTime > 2 && --flapCooldownTick < 0) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), BirdsSoundEvents.BIRD_FLAP, this.getSoundSource(), 0.1f + this.random.nextFloat() * 0.05F, 1.95F + this.random.nextFloat() * 0.05F, false);
            flapCooldownTick = 0;
        }
        flyAnimationState.startIfStopped(tickCount + flapOffset);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return BirdsSoundEvents.BIRD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return BirdsSoundEvents.BIRD_DEATH;
    }
}
