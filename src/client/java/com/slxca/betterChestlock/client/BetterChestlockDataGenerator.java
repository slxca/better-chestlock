package com.slxca.betterChestlock.client;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.ModBlocks;
import com.slxca.betterChestlock.ModItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class BetterChestlockDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BetterChestlockLootProvider::new);
        pack.addProvider(BetterChestlockRecipeProvider::new);
        pack.addProvider(BetterChestlockLanguageProviderEn::new);
        pack.addProvider(BetterChestlockLanguageProviderDe::new);
    }

    private static class BetterChestlockRecipeProvider extends FabricRecipeProvider {
        protected BetterChestlockRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
            return new RecipeProvider(recipes, advancements) {
                @Override
                public void buildRecipes() {
                    shaped(RecipeCategory.MISC, ModItems.LOCK)
                            .pattern(" I ")
                            .pattern("III")
                            .pattern(" I ")
                            .define('I', Items.IRON_INGOT)
                            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                            .save(output);

                    shaped(RecipeCategory.DECORATIONS, ModBlocks.LOCKED_CHEST)
                            .pattern("PPP")
                            .pattern("PLP")
                            .pattern("PPP")
                            .define('P', ItemTags.PLANKS)
                            .define('L', ModItems.LOCK)
                            .unlockedBy("has_lock", has(ModItems.LOCK))
                            .save(output);
                }
            };
        }
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
            translationBuilder.add(ModItems.LOCK, "Lock");
        }
    }

    private static class BetterChestlockLanguageProviderDe extends FabricLanguageProvider {
        protected BetterChestlockLanguageProviderDe(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, "de_de", registriesFuture);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(ModBlocks.LOCKED_CHEST, "Verschlossene Truhe");
            translationBuilder.add(ModItems.LOCK, "Schloss");
        }
    }
}