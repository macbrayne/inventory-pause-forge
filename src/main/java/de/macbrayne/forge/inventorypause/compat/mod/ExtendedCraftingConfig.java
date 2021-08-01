package de.macbrayne.forge.inventorypause.compat.mod;

import com.blakebr0.extendedcrafting.client.screen.*;
import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;

@RegisterCompat(modId = "extendedcrafting", configKey = "extendedCraftingCompat")
public class ExtendedCraftingConfig {
    @RegisterClass(AdvancedAutoTableScreen.class)
    boolean advancedAutoTableScreen= true;
    @RegisterClass(AdvancedTableScreen.class)
    boolean advancedTableScreen= true;
    @RegisterClass(BasicAutoTableScreen.class)
    boolean basicAutoTableScreen= true;
    @RegisterClass(BasicTableScreen.class)
    boolean basicTableScreen= true;
    @RegisterClass(CompressorScreen.class)
    boolean compressorScreen= true;
    @RegisterClass(CraftingCoreScreen.class)
    boolean craftingCoreScreen= true;
    @RegisterClass(EliteAutoTableScreen.class)
    boolean eliteAutoTableScreen= true;
    @RegisterClass(EliteTableScreen.class)
    boolean eliteTableScreen= true;
    @RegisterClass(EnderCrafterScreen.class)
    boolean enderCrafterScreen= true;
    @RegisterClass(UltimateAutoTableScreen.class)
    boolean ultimateAutoTableScreen= true;
    @RegisterClass(UltimateTableScreen.class)
    boolean ultimateTableScreen= true;
}
