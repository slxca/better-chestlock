package com.slxca.betterChestlock.block;

import com.slxca.betterChestlock.BetterChestlock;
import com.slxca.betterChestlock.config.ModConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    public static final Identifier LOCKED_CHEST_ID = Identifier.fromNamespaceAndPath(BetterChestlock.MOD_ID, "locked_chest");

    public static final LockedChestBlock LOCKED_CHEST = Registry.register(
            BuiltInRegistries.BLOCK,
            LOCKED_CHEST_ID,
            new LockedChestBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, LOCKED_CHEST_ID))
                    .mapColor(MapColor.WOOD)
                    .strength(2.5f)
                    .explosionResistance(ModConfig.INSTANCE.allowExplosions ? 2.5f : 3600000f)
                    .sound(SoundType.WOOD)));

    public static final Item LOCKED_CHEST_ITEM = Registry.register(
            BuiltInRegistries.ITEM,
            LOCKED_CHEST_ID,
            new BlockItem(LOCKED_CHEST, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, LOCKED_CHEST_ID))
                    .useBlockDescriptionPrefix()));

    public static void init() {
    }
}