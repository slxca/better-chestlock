package com.slxca.betterChestlock.block;

import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LockedChestBlock extends ChestBlock {

    public LockedChestBlock(Properties properties) {
        super(() -> ModBlockEntityTypes.LOCKED_CHEST, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LockedChestBlockEntity(pos, state);
    }
}