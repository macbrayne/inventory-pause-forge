// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the mod config. All fields not configurable in-game are set to final
 */
public class ModConfig {
    public boolean enabled = true;
    public boolean disableSaving = false;
    public boolean pauseSounds = false;
    public boolean debug = false;
    public final DebugText debugText = new DebugText();
    public final Config settingsForModpacks = new Config();

    public static class DebugText {
        public final float x = 4f;
        public final float y = 4f;
        public final int maxDepth = 3;
    }

    public final Abilities abilities = new Abilities();

    public final ModCompat modCompat = new ModCompat();

    public static class Abilities {
        public static class AdditionalGUIs {
            public boolean pauseAnvil = false;
            public boolean pauseBeacon = false;
            public boolean pauseDispenser = false;
            public boolean pauseBrewingStand = false;
            public boolean pauseHopper = false;
            public boolean pauseCartographyTable = false;
            public boolean pauseStonecutter = false;
        }

        public static class WorldGUIs {
            public boolean pauseHorse = false;
            public boolean pauseMerchant = false;
        }

        public boolean pauseInventory = true;
        public boolean pauseCreativeInventory = true;
        public boolean pauseDeath = false;
        public boolean pauseGameModeSwitcher = false;
        public boolean pauseCraftingTable = false;
        public boolean pauseFurnace = false;
        public boolean pauseShulkerBox = false;
        public boolean pauseChest = false;
        public final WorldGUIs worldGUIs = new WorldGUIs();
        public final AdditionalGUIs additionalGUIs = new AdditionalGUIs();
    }

    public static class ModCompat {
        public final List<String> customScreens = new ArrayList<>();
        public int timeBetweenCompatTicks = 20;
        public final List<String> compatScreens = new ArrayList<>();
    }

    public static class Config {
        public final boolean hideDebugButton = false;
        public final boolean hideModCompatButton = false;
        public final boolean registerKeybinds = true;
    }
}
