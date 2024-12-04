// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.world.item.ItemStack;
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
    public static final Codec<Map<String, PauseMode>> STATES_CODEC = Codec.unboundedMap(Codec.STRING, PauseMode.CODEC);
    public final List<GuiEntry> entries = new ArrayList<>();
    public final Map<GuiEntry, PauseMode> states = new HashMap<>();

    public GuiEntries(List<GuiEntry> entries) {
        this.entries.addAll(entries);
    }

    public GuiEntries() {
    }

    public List<GuiEntry> entries() {
        return entries;
    }

    public void loadStates(ModContainer container) {
        Map<String, GuiEntry> lookupMap = new HashMap<>();
        this.entries.forEach(entry -> lookupMap.put(entry.configEntry(), entry));

        Map<String, PauseMode> stringPauseModeMap = attemptLoadStates(FMLPaths.CONFIGDIR.get().resolve("inventorypause/states.json")).orElseGet(HashMap::new);

        stringPauseModeMap.forEach((key, value) -> {
            if (lookupMap.get(key) != null) {
                if (states.put(lookupMap.get(key), value) != null) {
                    LOGGER.error("Duplicate entry for {}", key);
                }
            }
        });
    }

    public void saveStates() {
        Map<String, PauseMode> stringified = new HashMap<>();
        this.states.forEach((key, value) -> stringified.put(key.configEntry(), value));
        ConfigHelper.save(FMLPaths.CONFIGDIR.get().resolve("inventorypause/states.json"), stringified, STATES_CODEC);
    }

    private static Optional<Map<String, PauseMode>> attemptLoadStates(Path path) {
        return ConfigHelper.attemptLoad(path, STATES_CODEC);
    }

    public static GuiEntries loadEntries(ModContainer container) {
        return attemptLoadEntries(FMLPaths.CONFIGDIR.get().resolve("inventorypause/guientries.json")).orElseGet(
                () -> attemptLoadEntries(container.getModInfo().getOwningFile().getFile().findResource("data/inventorypause/guientries.json")).orElseGet(GuiEntries::new)
        );
    }

    private static Optional<GuiEntries> attemptLoadEntries(Path path) {
        return ConfigHelper.attemptLoad(path, ENTRIES_CODEC);
    }

    public record GuiEntry(String configEntry, ItemStack icon, Class<?> target) {
        private static final Codec<Class<?>> CLASS_CODEC = Codec.STRING.comapFlatMap(className -> {
            try {
                return DataResult.success(Class.forName(className, false, GuiEntries.class.getClassLoader()));
            } catch (ClassNotFoundException e) {
                return DataResult.error(() -> "Could not find class " + className);
            }
        }, Class::getName);
        public static final Codec<GuiEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("config_entry").forGetter(GuiEntry::configEntry),
                ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("icon").forGetter(GuiEntry::icon),
                CLASS_CODEC.fieldOf("target").forGetter(GuiEntry::target)
        ).apply(instance, GuiEntry::new));

        public GuiEntry {
            Objects.requireNonNull(configEntry, "configEntry");
            Objects.requireNonNull(icon, "icon");
            Objects.requireNonNull(target, "target");
        }
    }
}
