package com.slxca.betterChestlock;

import java.util.Set;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {

    public static final BlockEntityType<LockedChestBlockEntity> LOCKED_CHEST = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ModBlocks.LOCKED_CHEST_ID,
            new BlockEntityType<>(LockedChestBlockEntity::new, Set.of(ModBlocks.LOCKED_CHEST)));

    public static void init() {
    }
}