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
        // Mod compats which cause problematic behaviour should be disabled by default

        public boolean waystonesCompat = true;
        public boolean ironchestCompat = true;
        public boolean appliedEnergistics2Compat = true;
        public boolean botaniaCompat = true;
        public boolean curiosCompat = true;
        public boolean theTwilightForestCompat = true;
        public boolean mekanismCompat = true;
        public boolean mekanismGeneratorsCompat = true;
        public boolean refinedStorageCompat = false;
        public boolean titaniumCompat = true;
        public boolean industrialForegoingCompat = true;
        public boolean projectECompat = false;
        public boolean aquaculture2Compat = true;
        public boolean pneumaticcraftCompat = false;
        public boolean extendedCraftingCompat = true;

        @ConfigEntry.Gui.Excluded
        public FineTuning fineTuning = new FineTuning();

        List<String> customScreens = new ArrayList<>();

        public static class FineTuning {
            public WaystonesConfig waystonesConfig = new WaystonesConfig();
            public AppliedEnergistics2Config appliedEnergistics2Config = new AppliedEnergistics2Config();
            public MekanismConfig mekanismConfig = new MekanismConfig();
            public MekanismGeneratorsConfig mekanismGeneratorsConfig = new MekanismGeneratorsConfig();
            public RefinedStorageConfig refinedStorageConfig = new RefinedStorageConfig();
            public IndustrialForegoingConfig industrialForegoingConfig = new IndustrialForegoingConfig();
            public ProjectEConfig projectEConfig = new ProjectEConfig();
            public PneumaticCraftConfig pneumaticCraftConfig = new PneumaticCraftConfig();
            public ExtendedCraftingConfig extendedCraftingConfig = new ExtendedCraftingConfig();
        }
    }
}
