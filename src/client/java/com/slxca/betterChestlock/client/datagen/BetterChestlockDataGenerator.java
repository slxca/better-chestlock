package com.slxca.betterChestlock.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetterChestlockDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BetterChestlockBlockLootProvider::new);
        pack.addProvider(BetterChestlockRecipeProvider::new);
        pack.addProvider(EnglishLanguageProvider::new);
        pack.addProvider(GermanLanguageProvider::new);
    }
}