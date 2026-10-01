package com.slxca.betterChestlock;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChestInfoRequests {

    private static final Set<UUID> PENDING = new HashSet<>();

    private ChestInfoRequests() {
    }

    public static void add(UUID playerUuid) {
        PENDING.add(playerUuid);
    }

    public static boolean consume(UUID playerUuid) {
        return PENDING.remove(playerUuid);
    }
}