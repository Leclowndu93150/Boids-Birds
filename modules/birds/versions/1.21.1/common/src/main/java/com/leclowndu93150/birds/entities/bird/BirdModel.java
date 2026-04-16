package com.leclowndu93150.birds.entities.bird;

import com.leclowndu93150.birds.Birds;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

public class BirdModel<T extends Bird> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Birds.id("birdentitymodel"), "main");
    private final ModelPart root;

    public BirdModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 1.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(2, 10).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 21).addBox(-0.5F, 1.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -6.0F, -1.5708F, 0.0F, 0.0F));

        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(39, 11).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition right_wing = root.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -1.0F, -4.0F, 0.0F, 0.0F, -0.2618F));

        right_wing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.5F, -3.0F, 19.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, 3.1416F, 0.0F, -2.618F));

        right_wing.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(34, 26).mirror().addBox(-5.0F, -2.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(29, 21).mirror().addBox(-5.0F, -2.5F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, 0.0F, -1.5708F, 0.5236F));

        PartDefinition left_wing = root.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -1.0F, -4.0F, 0.0F, 0.0F, 0.2618F));

        left_wing.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -1.5F, -3.0F, 19.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, 3.1416F, 0.0F, 2.618F));

        left_wing.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(29, 21).addBox(3.0F, -2.5F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(34, 26).addBox(4.0F, -2.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, 0.0F, 1.5708F, -0.5236F));

        PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 5.0F, -0.2182F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(-1, 31).mirror().addBox(-0.5F, -3.0F, -12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.7053F, 0.0F, 3.1416F));

        tail.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(0.0F, -3.0F, -11.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.696F, 0.1974F, 3.0482F));

        tail.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(0.0F, -3.0F, -11.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.681F, 0.3152F, 2.989F));

        tail.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(-1, 31).mirror().addBox(0.0F, -3.0F, -12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.7042F, 0.0791F, 3.1231F));

        tail.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -3.0F, -11.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.681F, -0.3152F, -2.989F));

        tail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -3.0F, -11.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.696F, -0.1974F, -3.0482F));

        tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(-1, 31).addBox(-0.5F, -3.0F, -12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.7053F, 0.0F, -3.1416F));

        tail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(-1, 31).addBox(-1.0F, -3.0F, -12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.948F, -3.7867F, 2.7042F, -0.0791F, -3.1231F));

        PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 5.0F));

        legs.addOrReplaceChild("LegRight", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(1.0F, 0.0F, -8.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 7.0F));

        legs.addOrReplaceChild("LegLeft", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -8.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        animate(entity.flyAnimationState, BirdAnimation.FLAP, ageInTicks, 1);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}
