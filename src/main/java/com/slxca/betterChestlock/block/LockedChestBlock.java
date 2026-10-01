package com.slxca.betterChestlock.block;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.slxca.betterChestlock.ChestInfoRequests;
import com.slxca.betterChestlock.LockedChestProtection;
import com.slxca.betterChestlock.ModConfig;
import com.slxca.betterChestlock.TrustData;
import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
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
        if (ModConfig.INSTANCE.allowHoppers && level instanceof Level world) {
            return new LockedChestWorldlyContainer(ChestBlock.getContainer(this, state, world, pos, false));
        }
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
        if (level.getBlockEntity(pos) instanceof LockedChestBlockEntity lockedChest) {
            if (level instanceof ServerLevel serverLevel && ChestInfoRequests.consume(player.getUUID())) {
                showInfo(player, lockedChest, serverLevel.getServer());
                return InteractionResult.SUCCESS;
            }

            if (level instanceof ServerLevel && !LockedChestProtection.canAccess(lockedChest, player)) {
                player.sendOverlayMessage(Component.translatable(CANT_OPEN_KEY));
                return InteractionResult.FAIL;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private static void showInfo(Player player, LockedChestBlockEntity chest, MinecraftServer server) {
        UUID ownerUuid = chest.getOwner();
        player.sendSystemMessage(Component.translatable("message.better-chestlock.info_title"));

        if (ownerUuid == null) {
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_owner", "?"));
            return;
        }

        String ownerName = resolveName(server, ownerUuid);
        player.sendSystemMessage(Component.translatable("message.better-chestlock.info_owner", ownerName));

        List<UUID> trusted = TrustData.get(server).getTrusted(ownerUuid);
        if (trusted.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_trusted_none"));
        } else {
            String names = trusted.stream().map(id -> resolveName(server, id)).collect(Collectors.joining(", "));
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_trusted", names));
        }
    }

    private static String resolveName(MinecraftServer server, UUID uuid) {
        ServerPlayer online = server.getPlayerList().getPlayersByUUID().get(uuid);
        if (online != null) {
            return online.getGameProfile().name();
        }
        return server.services().nameToIdCache().get(uuid).map(NameAndId::name).orElse(uuid.toString());
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

    private static class LockedChestWorldlyContainer implements WorldlyContainer {

        private final net.minecraft.world.Container chest;

        LockedChestWorldlyContainer(net.minecraft.world.Container chest) {
            this.chest = chest;
        }

        @Override
        public int getContainerSize() {
            return this.chest.getContainerSize();
        }

        @Override
        public boolean isEmpty() {
            return this.chest.isEmpty();
        }

        @Override
        public ItemStack getItem(int slot) {
            return this.chest.getItem(slot);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            return this.chest.removeItem(slot, amount);
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            return this.chest.removeItemNoUpdate(slot);
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            this.chest.setItem(slot, stack);
        }

        @Override
        public void setChanged() {
            this.chest.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return this.chest.stillValid(player);
        }

        @Override
        public void clearContent() {
            this.chest.clearContent();
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            int[] slots = new int[this.chest.getContainerSize()];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
            return slots;
        }

        @Override
        public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return this.chest.canPlaceItem(slot, stack);
        }

        @Override
        public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return true;
        }
    }
}