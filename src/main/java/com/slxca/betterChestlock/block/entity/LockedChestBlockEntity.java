package com.slxca.betterChestlock.block.entity;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class LockedChestBlockEntity extends ChestBlockEntity {

    private static final String OWNER_KEY = "owner";

    @Nullable
    private UUID owner;

    public LockedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.LOCKED_CHEST, pos, state);
    }

    @Nullable
    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        this.setChanged();
    }

    public boolean isOwner(Player player) {
        return this.owner != null && this.owner.equals(player.getUUID());
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.owner != null) {
            output.putString(OWNER_KEY, this.owner.toString());
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        Optional<String> owner = input.getString(OWNER_KEY);
        this.owner = owner.map(UUID::fromString).orElse(null);
    }
}