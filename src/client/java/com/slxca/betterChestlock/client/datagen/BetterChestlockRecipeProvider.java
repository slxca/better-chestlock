package com.slxca.betterChestlock.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class BetterChestlockRecipeProvider extends FabricRecipeProvider {

    protected BetterChestlockRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                addLockRecipe();
                addLockedChestRecipe();
            }

            private void addLockRecipe() {
                shaped(RecipeCategory.MISC, ModItems.LOCK)
                        .pattern(" I ")
                        .pattern("III")
                        .pattern(" I ")
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                        .save(output);
            }

            private void addLockedChestRecipe() {
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