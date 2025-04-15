// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.datagen;

import de.macbrayne.menupause.config.GuiEntries;
import de.macbrayne.menupause.config.GuiEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class GuiEntriesDataProvider extends FabricCodecDataProvider<GuiEntries> {

    protected GuiEntriesDataProvider(PackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "", GuiEntries.ENTRIES_CODEC);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, GuiEntries> provider, HolderLookup.Provider lookup) {
        List<GuiEntry<?>> entries = new ArrayList<>();
        accept(entries);
        provider.accept(new ResourceLocation("menupause", "guientries"), new GuiEntries(entries));
    }


    public abstract void accept(List<GuiEntry<?>> guiEntries);

    @Override
    public String getName() {
        return "GuiEntries Provider";
    }
}
