package com.slxca.betterChestlock;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;

import java.util.UUID;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
            dispatcher.register(Commands.literal("chest")
                    .then(Commands.literal("info")
                            .executes(context -> info(context.getSource())))
                    .then(Commands.literal("trust")
                            .then(Commands.argument("player", word())
                                    .executes(context -> trust(context.getSource(), getString(context, "player")))))
                    .then(Commands.literal("untrust")
                            .then(Commands.argument("player", word())
                                    .executes(context -> untrust(context.getSource(), getString(context, "player"))))));
        });
    }

    private static int info(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ChestInfoRequests.add(player.getUUID());
        source.sendSuccess(() -> Component.translatable("message.better-chestlock.info_click"), false);
        return 1;
    }

    private static int trust(CommandSourceStack source, String playerName) throws CommandSyntaxException {
        ServerPlayer owner = source.getPlayerOrException();
        UUID target = resolvePlayerId(source.getServer(), playerName);
        if (target == null) {
            source.sendFailure(Component.translatable("message.better-chestlock.player_not_found"));
            return 0;
        }
        if (target.equals(owner.getUUID())) {
            source.sendFailure(Component.translatable("message.better-chestlock.cannot_trust_self"));
            return 0;
        }

        TrustData.get(source.getServer()).trust(owner.getUUID(), target);
        source.sendSuccess(() -> Component.translatable("message.better-chestlock.trusted", playerName), false);
        return 1;
    }

    private static int untrust(CommandSourceStack source, String playerName) throws CommandSyntaxException {
        ServerPlayer owner = source.getPlayerOrException();
        UUID target = resolvePlayerId(source.getServer(), playerName);
        if (target == null) {
            source.sendFailure(Component.translatable("message.better-chestlock.player_not_found"));
            return 0;
        }

        TrustData.get(source.getServer()).untrust(owner.getUUID(), target);
        source.sendSuccess(() -> Component.translatable("message.better-chestlock.untrusted", playerName), false);
        return 1;
    }

    private static UUID resolvePlayerId(MinecraftServer server, String name) {
        ServerPlayer online = server.getPlayerList().getPlayerByName(name);
        if (online != null) {
            return online.getUUID();
        }
        return server.services().nameToIdCache().get(name).map(NameAndId::id).orElse(null);
    }
}