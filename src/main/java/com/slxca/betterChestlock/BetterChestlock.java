package com.slxca.betterChestlock;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.block.entity.ModBlockEntityTypes;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class BetterChestlock implements ModInitializer {

    public static final String MOD_ID = "better-chestlock";

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModBlockEntityTypes.init();
        ModCreativeTabs.register();
        ModCommands.register();
        LockedChestProtection.register();
    }
}