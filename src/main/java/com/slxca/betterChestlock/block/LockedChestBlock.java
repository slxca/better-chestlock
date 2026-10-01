package com.slxca.betterChestlock.block;

import com.slxca.betterChestlock.LockedChestProtection;
import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
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
    private static final WorldlyContainer EMPTY_CONTAINER = new EmptyWorldlyContainer();

    public LockedChestBlock(Properties properties) {
        super(() -> ModBlockEntityTypes.LOCKED_CHEST, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LockedChestBlockEntity(pos, state);
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
        return EMPTY_CONTAINER;
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
                && !LockedChestProtection.canAccess(lockedChest, player)) {
            player.sendOverlayMessage(Component.translatable(CANT_OPEN_KEY));
            return InteractionResult.FAIL;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private static class EmptyWorldlyContainer extends SimpleContainer implements WorldlyContainer {

        EmptyWorldlyContainer() {
            super(0);
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return false;
        }
    }
}