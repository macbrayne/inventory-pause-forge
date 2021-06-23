package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "inventorypause")
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    public boolean debug = false;

    @ConfigEntry.Gui.CollapsibleObject
    public DebugText debugText = new DebugText();

    public static class DebugText {
        public float x = 4f;
        public float y = 4f;
        public int maxDepth = 3;
    }

    @ConfigEntry.Category("abilities")
    @ConfigEntry.Gui.TransitiveObject
    public Abilities abilities = new Abilities();

    static class Abilities {
        boolean pauseInventory = true;
        boolean pauseCreativeInventory = true;
        boolean pauseFurnace = false;
        boolean pauseCraftingTable = false;
        boolean pauseShulkerBox = false;
        boolean pauseAnvil = false;
        boolean pauseBeacon = false;
        boolean pauseHorse = false;
        boolean pauseDispenser = false;
        boolean pauseBrewingStand = false;
        boolean pauseCartographyTable = false;
        boolean pauseChest = false;
        boolean pauseMerchant = false;
        boolean pauseStonecutter = false;
    }

    @ConfigEntry.Category("modCompat")
    List<String> customScreens = new ArrayList<>();
}
