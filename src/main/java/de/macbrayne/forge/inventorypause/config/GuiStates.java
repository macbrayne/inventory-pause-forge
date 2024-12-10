// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.config;

import com.mojang.serialization.Codec;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiStates {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static final Codec<Map<String, PauseMode>> STATES_CODEC = Codec.unboundedMap(Codec.STRING, PauseMode.CODEC);
    public static final Codec<GuiStates> CODEC = STATES_CODEC.xmap(GuiStates::new, GuiStates::convertBack);;

    public final Map<String, GuiEntry<?>> lookupMap;
    public final Map<GuiEntry<?>, PauseMode> states;

    public GuiStates(Map<String, PauseMode> states) {
        states = new HashMap<>(states);
        this.lookupMap = new HashMap<>();
        InventoryPause.GUI_ENTRIES.entries().forEach(entry -> this.lookupMap.put(entry.configEntry(), entry));
        this.states = convertStates(states, this.lookupMap);
    }

    public PauseMode get(GuiEntry<?> entry) {
        return states.get(entry);
    }

    public PauseMode get(String entryString) {
        return states.get(lookupMap.get(entryString));
    }

    public PauseMode put(GuiEntry<?> entry, PauseMode mode) {
        return states.put(entry, mode);
    }

    private static Map<GuiEntry<?>, PauseMode> convertStates(Map<String, PauseMode> map, Map<String, GuiEntry<?>> lookupMap) {
        if(map.isEmpty()) {
            return new HashMap<>();
        }
        Map<GuiEntry<?>, PauseMode> states = new HashMap<>();
        map.forEach((key, value) -> {
            if (lookupMap.get(key) != null) {
                if (states.put(lookupMap.get(key), value) != null) {
                    LOGGER.error("Duplicate entry for {} encountered while attempting to load config", key);
                }
            }
        });
        return states;
    }

    private static Map<String, PauseMode> convertBack(GuiStates gui) {
        Map<String, PauseMode> states = new HashMap<>();
        gui.states.forEach((key, value) -> states.put(key.configEntry(), value));
        return states;
    }

    public void registerScreens() {
        ScreenDictionary dict = InventoryPause.getScreenDictionary();
        InventoryPause.GUI_ENTRIES.entries().forEach(entry -> {
            dict.register(entry.target(), () -> get(entry));
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiStates guiStates = (GuiStates) o;
        return Objects.equals(states, guiStates.states);
    }
}
