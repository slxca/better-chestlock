package com.slxca.betterChestlock;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    private static final ResourceKey<CreativeModeTab> FUNCTIONAL_BLOCKS = tab("functional_blocks");
    private static final ResourceKey<CreativeModeTab> INGREDIENTS = tab("ingredients");

    public static void register() {
        CreativeModeTabEvents.modifyOutputEvent(FUNCTIONAL_BLOCKS)
                .register(output -> output.accept(new ItemStack(ModBlocks.LOCKED_CHEST)));

        CreativeModeTabEvents.modifyOutputEvent(INGREDIENTS)
                .register(output -> output.accept(new ItemStack(ModItems.LOCK)));
    }

    private static ResourceKey<CreativeModeTab> tab(String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace(path));
    }
}