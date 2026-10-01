package com.slxca.betterChestlock.protection;

import java.util.UUID;

import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.config.ModConfig;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;

public class LockedChestProtection {

    private static final String CANT_BREAK_KEY = "message.better-chestlock.cant_break";

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!(blockEntity instanceof LockedChestBlockEntity lockedChest)) {
                return true;
            }

            if (canAccess(lockedChest, player)) {
                return true;
            }

            player.sendOverlayMessage(Component.translatable(CANT_BREAK_KEY));
            return false;
        });
    }

    public static boolean canAccess(LockedChestBlockEntity lockedChest, Player player) {
        return isOperator(player) || lockedChest.isOwner(player) || isTrusted(lockedChest, player);
    }

    private static boolean isTrusted(LockedChestBlockEntity lockedChest, Player player) {
        if (!(lockedChest.getLevel() instanceof ServerLevel serverLevel)) {
            return false;
        }

        UUID owner = lockedChest.getOwner();
        if (owner == null) {
            return false;
        }

        return TrustData.get(serverLevel.getServer()).isTrusted(owner, player.getUUID());
    }

    private static boolean isOperator(Player player) {
        return ModConfig.INSTANCE.opBypass
                && player instanceof ServerPlayer serverPlayer
                && serverPlayer.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER);
    }
}