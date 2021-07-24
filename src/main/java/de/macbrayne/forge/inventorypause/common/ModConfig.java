package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.compat.mod.*;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CanBeFinal")
@Config(name = "inventorypause")
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    public boolean disableSaving = false;
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

    @ConfigEntry.Category("modCompat")
    @ConfigEntry.Gui.TransitiveObject
    public ModCompat modCompat = new ModCompat();

    public static class Abilities {
        public static class AdditionalGUIs {
            public boolean pauseAnvil = false;
            public boolean pauseBeacon = false;
            public boolean pauseDispenser = false;
            public boolean pauseBrewingStand = false;
            public boolean pauseCartographyTable = false;
            public boolean pauseStonecutter = false;
        }

        public static class WorldGUIs {
            public boolean pauseHorse = false;
            public boolean pauseMerchant = false;
        }

        public boolean pauseInventory = true;
        public boolean pauseCreativeInventory = true;
        public boolean pauseCraftingTable = false;
        public boolean pauseFurnace = false;
        public boolean pauseShulkerBox = false;
        public boolean pauseChest = false;
        @ConfigEntry.Gui.CollapsibleObject
        public
        WorldGUIs worldGUIs = new WorldGUIs();
        @ConfigEntry.Gui.CollapsibleObject
        public
        AdditionalGUIs additionalGUIs = new AdditionalGUIs();
    }

    public static class ModCompat {
        // Mod compats which have problematic behaviour should be disabled by default

        public boolean waystonesCompat = true;
        public boolean ironchestCompat = true;
        public boolean botaniaCompat = true;
        public boolean curiosCompat = true;
        public boolean theTwilightForestCompat = true;
        public boolean mekanismCompat = true;
        public boolean refinedStorageCompat = false;
        public boolean titaniumCompat = true;
        public boolean industrialForegoingCompat = true;
        public boolean projectECompat = false;

        @ConfigEntry.Gui.Excluded
        public FineTuning fineTuning = new FineTuning();

        List<String> customScreens = new ArrayList<>();

        public static class FineTuning {
            public WaystonesCompat.WaystonesConfig waystonesConfig = new WaystonesCompat.WaystonesConfig();
            public AppliedEnergistics2Compat.AppliedEnergistics2Config appliedEnergistics2Config = new AppliedEnergistics2Compat.AppliedEnergistics2Config();
            public MekanismCompat.MekanismConfig mekanismConfig = new MekanismCompat.MekanismConfig();
            public RefinedStorageCompat.RefinedStorageConfig refinedStorageConfig = new RefinedStorageCompat.RefinedStorageConfig();
            public IndustrialForegoingCompat.IndustrialForegoingConfig industrialForegoingConfig = new IndustrialForegoingCompat.IndustrialForegoingConfig();
            public ProjectECompat.ProjectEConfig projectEConfig = new ProjectECompat.ProjectEConfig();
        }
    }
}
