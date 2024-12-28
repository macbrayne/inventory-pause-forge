// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.config.old;

import de.macbrayne.inventorypause.common.KeyBehaviour;
import de.macbrayne.inventorypause.common.PauseMode;
import de.macbrayne.inventorypause.config.GuiStates;
import de.macbrayne.inventorypause.config.ModConfig;

import java.util.*;

/**
 * Contains the mod config. All fields not configurable in-game are set to final
 */
public class ModConfigTOML {
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
        public PauseMode pauseGrindstone = PauseMode.OFF;
        public PauseMode pauseCrafter = PauseMode.OFF;
        public PauseMode pauseSignEdit = PauseMode.OFF;
        public PauseMode pauseSmithing = PauseMode.OFF;
        public PauseMode pauseLectern = PauseMode.OFF;
        public PauseMode pauseLoom = PauseMode.OFF;
        public PauseMode pauseEnchantingTable = PauseMode.OFF;

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
        ModConfigTOML modConfig = (ModConfigTOML) o;
        return enabled == modConfig.enabled && disableSaving == modConfig.disableSaving && pauseSounds == modConfig.pauseSounds && debug == modConfig.debug && Objects.equals(debugText, modConfig.debugText) && Objects.equals(settingsForModpacks, modConfig.settingsForModpacks) && Objects.equals(abilities, modConfig.abilities) && Objects.equals(modCompat, modConfig.modCompat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, disableSaving, pauseSounds, debug, debugText, settingsForModpacks, abilities, modCompat);
    }

    public static ModConfig toV3(ModConfigTOML convert) {
        ModConfig.ModCompat modCompat = new ModConfig.ModCompat(convert.modCompat.compatScreens, convert.modCompat.customScreens, (int) Math.ceil(20f / convert.modCompat.timeBetweenCompatTicks));
        ModConfig.SettingsForModpacks settingsForModpacks = new ModConfig.SettingsForModpacks(convert.settingsForModpacks.hideDebugButton, convert.settingsForModpacks.hideModCompatButton, convert.settingsForModpacks.registerKeybinds);
        ModConfig.DebugText debugText = new ModConfig.DebugText(convert.debug, convert.debugText.x, convert.debugText.y, convert.debugText.maxDepth);
        Map<String, PauseMode> states = new HashMap<>();
        states.put("pauseInventory", convert.abilities.pauseInventory);
        states.put("pauseCreativeInventory", convert.abilities.pauseCreativeInventory);
        states.put("pauseDeath", convert.abilities.pauseDeath);
        states.put("pauseGameModeSwitcher", convert.abilities.pauseGameModeSwitcher);
        states.put("pauseCraftingTable", convert.abilities.pauseCraftingTable);
        states.put("pauseFurnace", convert.abilities.pauseFurnace);
        states.put("pauseShulkerBox", convert.abilities.pauseShulkerBox);
        states.put("pauseChest", convert.abilities.pauseChest);
        states.put("pauseAnvil", convert.abilities.pauseAnvil);
        states.put("pauseBeacon", convert.abilities.pauseBeacon);
        states.put("pauseDispenser", convert.abilities.pauseDispenser);
        states.put("pauseBrewingStand", convert.abilities.pauseBrewingStand);
        states.put("pauseHopper", convert.abilities.pauseHopper);
        states.put("pauseCartographyTable", convert.abilities.pauseCartographyTable);
        states.put("pauseStonecutter", convert.abilities.pauseStonecutter);
        states.put("pauseHorse", convert.abilities.pauseHorse);
        states.put("pauseMerchant", convert.abilities.pauseMerchant);
        states.put("pauseGrindstone", convert.abilities.pauseGrindstone);
        states.put("pauseCrafter", convert.abilities.pauseCrafter);
        states.put("pauseSignEdit", convert.abilities.pauseSignEdit);
        states.put("pauseSmithing", convert.abilities.pauseSmithing);
        states.put("pauseLectern", convert.abilities.pauseLectern);
        states.put("pauseLoom", convert.abilities.pauseLoom);
        states.put("pauseEnchantingTable", convert.abilities.pauseEnchantingTable);

        return new ModConfig(3, convert.disableSaving, convert.pauseSounds, KeyBehaviour.ForceUnpause.UNPAUSE, convert.debug, debugText, settingsForModpacks, modCompat, new GuiStates(states));
    }
}