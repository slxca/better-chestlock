package com.slxca.betterChestlock.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.slxca.betterChestlock.block.entity.LockedChestBlockEntity;
import com.slxca.betterChestlock.protection.TrustData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;

public final class ChestInfo {

    private static final Set<UUID> PENDING = new HashSet<>();

    private ChestInfo() {
    }

    public static void addPending(UUID playerUuid) {
        PENDING.add(playerUuid);
    }

    public static boolean consumePending(UUID playerUuid) {
        return PENDING.remove(playerUuid);
    }

    public static void show(Player player, LockedChestBlockEntity chest, MinecraftServer server) {
        UUID ownerUuid = chest.getOwner();
        player.sendSystemMessage(Component.translatable("message.better-chestlock.info_title"));

        if (ownerUuid == null) {
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_owner", "?"));
            return;
        }

        player.sendSystemMessage(Component.translatable("message.better-chestlock.info_owner", resolveName(server, ownerUuid)));

        List<UUID> trusted = TrustData.get(server).getTrusted(ownerUuid);
        if (trusted.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_trusted_none"));
        } else {
            String names = trusted.stream().map(id -> resolveName(server, id)).collect(Collectors.joining(", "));
            player.sendSystemMessage(Component.translatable("message.better-chestlock.info_trusted", names));
        }
    }

    private static String resolveName(MinecraftServer server, UUID uuid) {
        ServerPlayer online = server.getPlayerList().getPlayersByUUID().get(uuid);
        if (online != null) {
            return online.getGameProfile().name();
        }
        return server.services().nameToIdCache().get(uuid).map(NameAndId::name).orElse(uuid.toString());
    }
}