package com.slxca.betterChestlock.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.slxca.betterChestlock.ModBlockEntityTypes;
import com.slxca.betterChestlock.client.LockedChestBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Mixin(BlockEntityRenderers.class)
public abstract class BlockEntityRenderersMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void betterChestlock$registerLockedChest(CallbackInfo ci) {
        invokeRegister(ModBlockEntityTypes.LOCKED_CHEST, LockedChestBlockEntityRenderer::new);
    }

    @Invoker("register")
    static <T extends BlockEntity, S extends BlockEntityRenderState> void invokeRegister(
            BlockEntityType<? extends T> type, BlockEntityRendererProvider<T, S> provider) {
        throw new AssertionError();
    }
}