package com.slxca.betterChestlock.item;

import com.slxca.betterChestlock.BetterChestlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Identifier LOCK_ID = Identifier.fromNamespaceAndPath(BetterChestlock.MOD_ID, "lock");

    public static final Item LOCK = Registry.register(
            BuiltInRegistries.ITEM,
            LOCK_ID,
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, LOCK_ID))));

    public static void init() {
    }
}