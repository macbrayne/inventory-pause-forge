// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class MenuPauseDataGenerator {
    public static void onInitializeDataGenerator(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(event.includeClient(), new GuiEntriesData(output, lookupProvider));
    }
}