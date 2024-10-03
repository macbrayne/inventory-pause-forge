// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains the mod config. All fields not configurable in-game are set to final
 */
public class ModConfig {
    public static final int VERSION = 2;
    public static boolean tempDisabled = false; // This doesn't get saved!
    public int CONFIG_VERSION_DO_NOT_TOUCH = VERSION;
    private final boolean enabled = true;
    public boolean disableSaving = false;
    public boolean pauseSounds = false;
    public boolean debug = false;
    public final DebugText debugText = new DebugText();
    public final Config settingsForModpacks = new Config();

    public boolean isEnabled() {
        return enabled && !tempDisabled;
    }

    public void setEnabled(boolean enabled) {
        tempDisabled = !enabled;
    }

    public static class DebugText {
        public float x = 4f;
        public float y = 4f;
        public int maxDepth = 3;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DebugText debugText = (DebugText) o;
            return Float.compare(x, debugText.x) == 0 && Float.compare(y, debugText.y) == 0 && maxDepth == debugText.maxDepth;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, maxDepth);
        }
    }

    public final Abilities abilities = new Abilities();

    public final ModCompat modCompat = new ModCompat();

    public static class Abilities {
        public PauseMode pauseInventory = PauseMode.ON;
        public PauseMode pauseCreativeInventory = PauseMode.ON;
        public PauseMode pauseDeath = PauseMode.OFF;
        public PauseMode pauseGameModeSwitcher = PauseMode.OFF;
        public PauseMode pauseCraftingTable = PauseMode.OFF;
        public PauseMode pauseFurnace = PauseMode.OFF;
        public PauseMode pauseShulkerBox = PauseMode.OFF;
        public PauseMode pauseChest = PauseMode.OFF;
        public PauseMode pauseAnvil = PauseMode.OFF;
        public PauseMode pauseBeacon = PauseMode.OFF;
        public PauseMode pauseDispenser = PauseMode.OFF;
        public PauseMode pauseBrewingStand = PauseMode.OFF;
        public PauseMode pauseHopper = PauseMode.OFF;
        public PauseMode pauseCartographyTable = PauseMode.OFF;
        public PauseMode pauseStonecutter = PauseMode.OFF;
        public PauseMode pauseHorse = PauseMode.OFF;
        public PauseMode pauseMerchant = PauseMode.OFF;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Abilities abilities = (Abilities) o;
            return pauseAnvil == abilities.pauseAnvil && pauseBeacon == abilities.pauseBeacon && pauseDispenser == abilities.pauseDispenser && pauseBrewingStand == abilities.pauseBrewingStand && pauseHopper == abilities.pauseHopper && pauseCartographyTable == abilities.pauseCartographyTable && pauseStonecutter == abilities.pauseStonecutter && pauseHorse == abilities.pauseHorse && pauseMerchant == abilities.pauseMerchant && pauseInventory == abilities.pauseInventory && pauseCreativeInventory == abilities.pauseCreativeInventory && pauseDeath == abilities.pauseDeath && pauseGameModeSwitcher == abilities.pauseGameModeSwitcher && pauseCraftingTable == abilities.pauseCraftingTable && pauseFurnace == abilities.pauseFurnace && pauseShulkerBox == abilities.pauseShulkerBox && pauseChest == abilities.pauseChest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pauseAnvil, pauseBeacon, pauseDispenser, pauseBrewingStand, pauseHopper, pauseCartographyTable, pauseStonecutter, pauseHorse, pauseMerchant, pauseInventory, pauseCreativeInventory, pauseDeath, pauseGameModeSwitcher, pauseCraftingTable, pauseFurnace, pauseShulkerBox, pauseChest);
        }
    }

    public static class ModCompat {
        public final List<String> customScreens = new ArrayList<>();
        public int timeBetweenCompatTicks = 20;
        public final List<String> compatScreens = new ArrayList<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModCompat modCompat = (ModCompat) o;
            return timeBetweenCompatTicks == modCompat.timeBetweenCompatTicks && Objects.equals(customScreens, modCompat.customScreens) && Objects.equals(compatScreens, modCompat.compatScreens);
        }

        @Override
        public int hashCode() {
            return Objects.hash(customScreens, timeBetweenCompatTicks, compatScreens);
        }
    }

    public static class Config {
        public boolean hideDebugButton = false;
        public boolean hideModCompatButton = false;
        public boolean registerKeybinds = true;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Config config = (Config) o;
            return hideDebugButton == config.hideDebugButton && hideModCompatButton == config.hideModCompatButton && registerKeybinds == config.registerKeybinds;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hideDebugButton, hideModCompatButton, registerKeybinds);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModConfig modConfig = (ModConfig) o;
        return enabled == modConfig.enabled && disableSaving == modConfig.disableSaving && pauseSounds == modConfig.pauseSounds && debug == modConfig.debug && Objects.equals(debugText, modConfig.debugText) && Objects.equals(settingsForModpacks, modConfig.settingsForModpacks) && Objects.equals(abilities, modConfig.abilities) && Objects.equals(modCompat, modConfig.modCompat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, disableSaving, pauseSounds, debug, debugText, settingsForModpacks, abilities, modCompat);
    }
}