package com.slxca.betterChestlock.client;

import com.mojang.blaze3d.vertex.PoseStack;

import com.slxca.betterChestlock.LockedChestBlockEntity;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.renderer.MultiblockChestResources;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;

public class LockedChestBlockEntityRenderer extends ChestRenderer<LockedChestBlockEntity> {

    private static final MultiblockChestResources<SpriteId> SPRITES = new MultiblockChestResources<>(
            sprite("locked_normal"),
            sprite("locked_left"),
            sprite("locked_right"));

    private final SpriteGetter sprites;
    private final MultiblockChestResources<ChestModel> models;

    public LockedChestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.sprites = context.sprites();
        this.models = ChestRenderer.LAYERS.map(context::bakeLayer).map(ChestModel::new);
    }

    private static SpriteId sprite(String path) {
        return Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath("better-chestlock", path));
    }

    @Override
    public void submit(ChestRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(ChestRenderer.modelTransformation(state.facing));
        float openness = 1.0F - state.open;
        openness = 1.0F - openness * openness * openness;
        SpriteId spriteId = SPRITES.select(state.type);
        ChestModel model = this.models.select(state.type);
        collector.submitModel(model, openness, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, spriteId, this.sprites, 0);
        if (state.breakProgress != null) {
            OrderedSubmitNodeCollector ordered = collector.order(1);
            RenderType renderType = spriteId.renderType(model.renderType());
            ordered.submitCrumblingOverlay(model, openness, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
        }
        poseStack.popPose();
    }
}