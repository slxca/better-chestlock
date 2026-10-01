package com.slxca.betterChestlock.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

public class BetterChestlockBlockLootProvider extends FabricBlockLootSubProvider {

    protected BetterChestlockBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.LOCKED_CHEST);
    }
}