package com.slxca.betterChestlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LockedChestBlockEntity extends ChestBlockEntity {

    public LockedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.LOCKED_CHEST, pos, state);
    }
}