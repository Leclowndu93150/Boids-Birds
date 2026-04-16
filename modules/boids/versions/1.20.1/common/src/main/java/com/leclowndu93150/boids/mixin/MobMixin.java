package com.leclowndu93150.boids.mixin;

import com.leclowndu93150.boids.Boids;
import com.leclowndu93150.boids.NearbyMobTracker;
import com.leclowndu93150.boids.duck.MobDuck;
import com.leclowndu93150.boids.goals.LimitSpeedAndLookInVelocityDirectionGoal;
import com.leclowndu93150.boids.goals.StayInWaterGoal;
import com.leclowndu93150.boids.config.BoidsConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements MobDuck {
    @Unique
    NearbyMobTracker boids$nearbyMobs;
    @Unique
    Goal boids$stayInWaterGoal;
    @Unique
    Goal boids$limitSpeedGoal;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        if (!Boids.isAffected(this)) {
            return;
        }

        boids$enable();
    }

    @Inject(method = "serverAiStep", at = @At("HEAD"), cancellable = true)
    void serverAiStep(CallbackInfo ci) {
        if (boids$nearbyMobs == null) {
            return;
        }

        ci.cancel();

        addDeltaMovement(Boids.SETTINGS.apply((Mob) (Object) this, boids$nearbyMobs.tick()));

        if (boids$stayInWaterGoal != null) {
            boids$stayInWaterGoal.tick();
        }

        boids$limitSpeedGoal.tick();
    }

    @Unique
    private static boolean boids$isWaterMob(EntityType<?> type) {
        MobCategory category = type.getCategory();
        return category == MobCategory.WATER_CREATURE
            || category == MobCategory.WATER_AMBIENT
            || category == MobCategory.UNDERGROUND_WATER_CREATURE
            || category == MobCategory.AXOLOTLS;
    }

    @Override
    public void boids$enable() {
        if (boids$isWaterMob(getType())) {
            boids$stayInWaterGoal = new StayInWaterGoal((Mob) (Object) this);
        }

        var config = BoidsConfig.HANDLER.instance();
        boids$limitSpeedGoal = new LimitSpeedAndLookInVelocityDirectionGoal((Mob) (Object) this, config.minSpeed, config.maxSpeed);
        boids$nearbyMobs = new NearbyMobTracker((Mob) (Object) this);
    }

    @Override
    public void boids$disable() {
        boids$stayInWaterGoal = null;
        boids$limitSpeedGoal = null;
        boids$nearbyMobs = null;
    }
}
