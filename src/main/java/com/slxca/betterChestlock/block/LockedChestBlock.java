package com.slxca.betterChestlock.block;

import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.block.entity.ModBlockEntityTypes;
import com.slxca.betterChestlock.command.ChestInfo;
import com.slxca.betterChestlock.config.ModConfig;
import com.slxca.betterChestlock.protection.LockedChestProtection;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;

public class LockedChestBlock extends ChestBlock implements WorldlyContainerHolder {

    private static final String CANT_OPEN_KEY = "message.better-chestlock.cant_open";

    public LockedChestBlock(Properties properties) {
        super(() -> ModBlockEntityTypes.LOCKED_CHEST, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LockedChestBlockEntity(pos, state);
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
        if (ModConfig.INSTANCE.allowHoppers && level instanceof Level world) {
            return ChestContainerAdapters.wrap(ChestBlock.getContainer(this, state, world, pos, false));
        }
        return ChestContainerAdapters.EMPTY;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (placer instanceof Player player
                && level.getBlockEntity(pos) instanceof LockedChestBlockEntity lockedChest) {
            lockedChest.setOwner(player.getUUID());

            if (state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
                BlockPos partnerPos = getConnectedBlockPos(pos, state);
                if (!(level.getBlockEntity(partnerPos) instanceof LockedChestBlockEntity partner)
                        || !player.getUUID().equals(partner.getOwner())) {
                    level.setBlock(pos, state.setValue(ChestBlock.TYPE, ChestType.SINGLE), 3);
                    level.setBlock(partnerPos, level.getBlockState(partnerPos).setValue(ChestBlock.TYPE, ChestType.SINGLE), 3);
                }
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof LockedChestBlockEntity lockedChest
                && level instanceof ServerLevel serverLevel) {
            if (ChestInfo.consumePending(player.getUUID())) {
                ChestInfo.show(player, lockedChest, serverLevel.getServer());
                return InteractionResult.SUCCESS;
            }

            if (!LockedChestProtection.canAccess(lockedChest, player)) {
                player.sendOverlayMessage(Component.translatable(CANT_OPEN_KEY));
                return InteractionResult.FAIL;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}