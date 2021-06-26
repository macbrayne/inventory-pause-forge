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
        static class AdditionalGUIs {
            boolean pauseAnvil = false;
            boolean pauseBeacon = false;
            boolean pauseDispenser = false;
            boolean pauseBrewingStand = false;
            boolean pauseCartographyTable = false;
            boolean pauseStonecutter = false;
        }

        static class WorldGUIs {
            boolean pauseHorse = false;
            boolean pauseMerchant = false;
        }

        boolean pauseInventory = true;
        boolean pauseCreativeInventory = true;
        boolean pauseCraftingTable = false;
        boolean pauseFurnace = false;
        boolean pauseShulkerBox = false;
        boolean pauseChest = false;
        @ConfigEntry.Gui.CollapsibleObject
        WorldGUIs worldGUIs = new WorldGUIs();
        @ConfigEntry.Gui.CollapsibleObject
        AdditionalGUIs additionalGUIs = new AdditionalGUIs();
    }


    @ConfigEntry.Category("modCompat")
    public boolean waystonesCompat = true;
    @ConfigEntry.Category("modCompat")
    List<String> customScreens = new ArrayList<>();
}
