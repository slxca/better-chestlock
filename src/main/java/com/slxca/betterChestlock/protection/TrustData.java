package com.slxca.betterChestlock.protection;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.slxca.betterChestlock.BetterChestlock;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class TrustData extends SavedData {

    private static final Codec<TrustData> CODEC = Codec.unboundedMap(UUIDUtil.CODEC, UUIDUtil.CODEC_SET)
            .xmap(TrustData::fromMap, data -> data.trusted);

    public static final SavedDataType<TrustData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(BetterChestlock.MOD_ID, "trust"),
            TrustData::new,
            CODEC,
            DataFixTypes.LEVEL);

    private final Map<UUID, Set<UUID>> trusted = new LinkedHashMap<>();

    public static TrustData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(TYPE);
    }

    private static TrustData fromMap(Map<UUID, Set<UUID>> map) {
        TrustData data = new TrustData();
        map.forEach((owner, trustedPlayers) -> data.trusted.put(owner, new LinkedHashSet<>(trustedPlayers)));
        return data;
    }

    public boolean isTrusted(UUID owner, UUID player) {
        Set<UUID> trustedPlayers = this.trusted.get(owner);
        return trustedPlayers != null && trustedPlayers.contains(player);
    }

    public void trust(UUID owner, UUID player) {
        this.trusted.computeIfAbsent(owner, id -> new LinkedHashSet<>()).add(player);
        this.setDirty();
    }

    public void untrust(UUID owner, UUID player) {
        Set<UUID> trustedPlayers = this.trusted.get(owner);
        if (trustedPlayers != null && trustedPlayers.remove(player)) {
            if (trustedPlayers.isEmpty()) {
                this.trusted.remove(owner);
            }
            this.setDirty();
        }
    }

    public List<UUID> getTrusted(UUID owner) {
        Set<UUID> trustedPlayers = this.trusted.get(owner);
        return trustedPlayers == null ? List.of() : List.copyOf(trustedPlayers);
    }
}