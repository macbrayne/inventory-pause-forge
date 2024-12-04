// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.config;

import com.mojang.serialization.Codec;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.CreativeInventoryListener;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiStates {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static final Codec<Map<String, PauseMode>> STATES_CODEC = Codec.unboundedMap(Codec.STRING, PauseMode.CODEC);
    public static final Codec<GuiStates> CODEC = STATES_CODEC.xmap(GuiStates::new, GuiStates::convertBack);
    public PauseMode pauseDeath, pauseInventory, pauseCreativeInventory, pauseGameModeSwitcher;

    public final Map<GuiEntry, PauseMode> states;

    public GuiStates(Map<String, PauseMode> states) {
        states = new HashMap<>(states);
        pauseInventory = states.remove("pauseInventory");
        pauseCreativeInventory = states.remove("pauseCreativeInventory");
        pauseGameModeSwitcher = states.remove("pauseGameModeSwitcher");
        pauseDeath = states.remove("pauseDeath");
        this.states = convertStates(states);
    }

    public PauseMode get(GuiEntry entry) {
        return states.get(entry);
    }

    public PauseMode put(GuiEntry entry, PauseMode mode) {
        return states.put(entry, mode);
    }

    private static Map<GuiEntry, PauseMode> convertStates(Map<String, PauseMode> map) {
        if(map.isEmpty()) {
            return new HashMap<>();
        }
        Map<GuiEntry, PauseMode> states = new HashMap<>();
        Map<String, GuiEntry> lookupMap = new HashMap<>();
        InventoryPause.GUI_ENTRIES.entries().forEach(entry -> lookupMap.put(entry.configEntry(), entry));
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
        states.put("pauseInventory", gui.pauseInventory == null ? PauseMode.ON : gui.pauseInventory);
        states.put("pauseCreativeInventory", gui.pauseCreativeInventory == null ? PauseMode.ON : gui.pauseCreativeInventory);
        states.put("pauseGameModeSwitcher", gui.pauseGameModeSwitcher == null ? PauseMode.OFF : gui.pauseGameModeSwitcher);
        states.put("pauseDeath", gui.pauseDeath == null ? PauseMode.OFF : gui.pauseDeath);
        return states;
    }

    public void registerScreens() {
        ScreenDictionary dict = InventoryPause.getScreenDictionary();
        dict.register(InventoryScreen.class, () -> pauseInventory);
        dict.register(CreativeModeInventoryScreen.class, () -> pauseCreativeInventory);
        dict.register(GameModeSwitcherScreen.class, () -> pauseGameModeSwitcher);
        dict.register(DeathScreen.class, () -> pauseDeath);
        InventoryPause.GUI_ENTRIES.entries().forEach(entry -> {
            dict.register(entry.target(), () -> get(entry));
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiStates guiStates = (GuiStates) o;
        return pauseDeath == guiStates.pauseDeath && pauseInventory == guiStates.pauseInventory && pauseCreativeInventory == guiStates.pauseCreativeInventory && pauseGameModeSwitcher == guiStates.pauseGameModeSwitcher && Objects.equals(states, guiStates.states);
    }
}
