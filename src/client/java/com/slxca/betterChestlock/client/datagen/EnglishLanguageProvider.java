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
        translationBuilder.add("message.better-chestlock.info_click", "Right-click a locked chest to view its info.");
        translationBuilder.add("message.better-chestlock.info_title", "Locked Chest");
        translationBuilder.add("message.better-chestlock.info_owner", "Owner: %s");
        translationBuilder.add("message.better-chestlock.info_trusted", "Trusted: %s");
        translationBuilder.add("message.better-chestlock.info_trusted_none", "Trusted: None");
        translationBuilder.add("message.better-chestlock.trusted", "You trusted %s on all your locked chests.");
        translationBuilder.add("message.better-chestlock.untrusted", "You removed %s from your locked chest trust list.");
        translationBuilder.add("message.better-chestlock.player_not_found", "Player not found.");
        translationBuilder.add("message.better-chestlock.cannot_trust_self", "You cannot trust yourself.");
    }
}