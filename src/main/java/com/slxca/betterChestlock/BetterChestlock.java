package com.slxca.betterChestlock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BetterChestlock implements ModInitializer {

    public static final String MOD_ID = "better-chestlock";

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModBlockEntityTypes.init();

        CreativeModeTabEvents.modifyOutputEvent(
                ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("functional_blocks")))
                .register(output -> output.accept(new ItemStack(ModBlocks.LOCKED_CHEST)));
    }
}