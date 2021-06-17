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

    @ConfigEntry.Category("abilities")
    @ConfigEntry.Gui.TransitiveObject
    public Abilities abilities = new Abilities();

    static class Abilities {
        boolean pauseInventory = true;
        boolean pauseCreativeInventory = true;
        boolean pauseFurnace = false;
        boolean pauseCraftingTable = false;
        boolean pauseShulkerBox = false;
    }

    @ConfigEntry.Category("modCompat")
    List<String> customScreens = new ArrayList<>();
}
