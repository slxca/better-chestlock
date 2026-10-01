package com.slxca.betterChestlock.client;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

public class BetterChestlockDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BetterChestlockLootProvider::new);
        pack.addProvider(BetterChestlockLanguageProviderEn::new);
        pack.addProvider(BetterChestlockLanguageProviderDe::new);
    }

    private static class BetterChestlockLootProvider extends FabricBlockLootSubProvider {
        protected BetterChestlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate() {
            dropSelf(ModBlocks.LOCKED_CHEST);
        }
    }

    private static class BetterChestlockLanguageProviderEn extends FabricLanguageProvider {
        protected BetterChestlockLanguageProviderEn(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(ModBlocks.LOCKED_CHEST, "Locked Chest");
        }
    }

    private static class BetterChestlockLanguageProviderDe extends FabricLanguageProvider {
        protected BetterChestlockLanguageProviderDe(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, "de_de", registriesFuture);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(ModBlocks.LOCKED_CHEST, "Verschlossene Truhe");
        }
    }
}