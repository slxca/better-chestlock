package com.slxca.betterChestlock.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

public class GermanLanguageProvider extends FabricLanguageProvider {

    protected GermanLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, "de_de", registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.LOCKED_CHEST, "Verschlossene Truhe");
        translationBuilder.add(ModItems.LOCK, "Schloss");
        translationBuilder.add("message.better-chestlock.cant_break", "Nur der Besitzer kann diese Truhe zerstören");
        translationBuilder.add("message.better-chestlock.cant_open", "Nur der Besitzer kann diese Truhe öffnen");
    }
}