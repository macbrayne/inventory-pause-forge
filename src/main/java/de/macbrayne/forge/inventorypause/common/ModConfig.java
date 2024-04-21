// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        public static class AdditionalGUIs {
            public boolean pauseAnvil = false;
            public boolean pauseBeacon = false;
            public boolean pauseDispenser = false;
            public boolean pauseBrewingStand = false;
            public boolean pauseHopper = false;
            public boolean pauseCartographyTable = false;
            public boolean pauseStonecutter = false;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                AdditionalGUIs that = (AdditionalGUIs) o;
                return pauseAnvil == that.pauseAnvil && pauseBeacon == that.pauseBeacon && pauseDispenser == that.pauseDispenser && pauseBrewingStand == that.pauseBrewingStand && pauseHopper == that.pauseHopper && pauseCartographyTable == that.pauseCartographyTable && pauseStonecutter == that.pauseStonecutter;
            }

            @Override
            public int hashCode() {
                return Objects.hash(pauseAnvil, pauseBeacon, pauseDispenser, pauseBrewingStand, pauseHopper, pauseCartographyTable, pauseStonecutter);
            }
        }

        public static class WorldGUIs {
            public boolean pauseHorse = false;
            public boolean pauseMerchant = false;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                WorldGUIs worldGUIs = (WorldGUIs) o;
                return pauseHorse == worldGUIs.pauseHorse && pauseMerchant == worldGUIs.pauseMerchant;
            }

            @Override
            public int hashCode() {
                return Objects.hash(pauseHorse, pauseMerchant);
            }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Abilities abilities = (Abilities) o;
            return pauseInventory == abilities.pauseInventory && pauseCreativeInventory == abilities.pauseCreativeInventory && pauseDeath == abilities.pauseDeath && pauseGameModeSwitcher == abilities.pauseGameModeSwitcher && pauseCraftingTable == abilities.pauseCraftingTable && pauseFurnace == abilities.pauseFurnace && pauseShulkerBox == abilities.pauseShulkerBox && pauseChest == abilities.pauseChest && Objects.equals(worldGUIs, abilities.worldGUIs) && Objects.equals(additionalGUIs, abilities.additionalGUIs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pauseInventory, pauseCreativeInventory, pauseDeath, pauseGameModeSwitcher, pauseCraftingTable, pauseFurnace, pauseShulkerBox, pauseChest, worldGUIs, additionalGUIs);
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
        public final boolean hideDebugButton = false;
        public final boolean hideModCompatButton = false;
        public final boolean registerKeybinds = true;

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
