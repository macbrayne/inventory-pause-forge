// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.macbrayne.forge.inventorypause.InventoryPause;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.*;

public class GuiEntries {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static final Codec<GuiEntries> ENTRIES_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GuiEntry.CODEC.listOf().fieldOf("icon_buttons").forGetter(GuiEntries::entries)
    ).apply(instance, GuiEntries::new));
    public final List<GuiEntry> entries = new ArrayList<>();

    public GuiEntries(List<GuiEntry> entries) {
        this.entries.addAll(entries);
    }

    public GuiEntries() {
    }

    public List<GuiEntry> entries() {
        return entries;
    }

    public static GuiEntries loadEntries(ModContainer container) {
        return attemptLoadEntries(FMLPaths.CONFIGDIR.get().resolve("inventorypause/guientries.json")).orElseGet(
                () -> attemptLoadEntries(container.getModInfo().getOwningFile().getFile().findResource("data/inventorypause/guientries.json")).orElseGet(GuiEntries::new)
        );
    }

    private static Optional<GuiEntries> attemptLoadEntries(Path path) {
        return ConfigHelper.attemptLoad(path, ENTRIES_CODEC);
    }
}
