package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "inventorypause")
public class ModConfig implements ConfigData {
    boolean pauseInventory = true;
    boolean pauseFurnace = false;
    boolean pauseCraftingTable = false;
    boolean pauseShulkerBox = false;
    List<String> customScreens = new ArrayList<>();

    public boolean debug = false;
}
