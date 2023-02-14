// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public boolean enabled = true;
    public boolean disableSaving = false;
    public boolean debug = false;
    public DebugText debugText = new DebugText();
    public Config configMenu = new Config();

    public static class DebugText {
        public float x = 4f;
        public float y = 4f;
        public int maxDepth = 3;
    }

    public Abilities abilities = new Abilities();

    public ModCompat modCompat = new ModCompat();

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
        public WorldGUIs worldGUIs = new WorldGUIs();
        public AdditionalGUIs additionalGUIs = new AdditionalGUIs();
    }

    public static class ModCompat {
        public List<String> customScreens = new ArrayList<>();
        public int timeBetweenCompatTicks = 20;
        public List<String> compatScreens = new ArrayList<>();
    }

    public static class Config {
        public boolean hideDebugButton = false;
        public boolean hideModCompatButton = false;

        public boolean registerKeybinds = true;
    }
}
