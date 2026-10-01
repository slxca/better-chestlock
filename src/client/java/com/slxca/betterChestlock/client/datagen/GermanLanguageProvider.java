package com.slxca.betterChestlock.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.slxca.betterChestlock.block.ModBlocks;
import com.slxca.betterChestlock.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

public class GermanLanguageProvider extends FabricLanguageProvider {

    protected GermanLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, "de_de", registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.LOCKED_CHEST, "Verschlossene Truhe");
        translationBuilder.add(ModItems.LOCK, "Schloss");
        translationBuilder.add("message.better-chestlock.cant_break", "Nur der Besitzer kann diese Truhe zerstören");
        translationBuilder.add("message.better-chestlock.cant_open", "Nur der Besitzer kann diese Truhe öffnen");
        translationBuilder.add("message.better-chestlock.info_click", "Klicke eine verschlossene Truhe rechts an, um ihre Informationen zu sehen.");
        translationBuilder.add("message.better-chestlock.info_title", "Verschlossene Truhe");
        translationBuilder.add("message.better-chestlock.info_owner", "Besitzer: %s");
        translationBuilder.add("message.better-chestlock.info_trusted", "Vertraut: %s");
        translationBuilder.add("message.better-chestlock.info_trusted_none", "Vertraut: Keiner");
        translationBuilder.add("message.better-chestlock.trusted", "Du hast %s Zugriff auf alle deine verschlossenen Truhen gegeben.");
        translationBuilder.add("message.better-chestlock.untrusted", "Du hast %s von deiner Trust-Liste entfernt.");
        translationBuilder.add("message.better-chestlock.player_not_found", "Spieler nicht gefunden.");
        translationBuilder.add("message.better-chestlock.cannot_trust_self", "Du kannst dir nicht selbst vertrauen.");
    }
}