package com.slxca.betterChestlock.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

public class EnglishLanguageProvider extends FabricLanguageProvider {

    protected EnglishLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.LOCKED_CHEST, "Locked Chest");
        translationBuilder.add(ModItems.LOCK, "Lock");
        translationBuilder.add("message.better-chestlock.cant_break", "Only the owner can break this chest");
        translationBuilder.add("message.better-chestlock.cant_open", "Only the owner can open this chest");
    }
}