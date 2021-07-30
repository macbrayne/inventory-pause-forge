package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;
import mekanism.generators.client.gui.*;

@RegisterCompat(modId = "mekanismgenerators", configKey = "mekanismGeneratorsCompat")
public class MekanismGeneratorsConfig {
    @RegisterClass(GuiBioGenerator.class)
    public boolean guiBioGenerator;
    @RegisterClass(GuiFissionReactor.class)
    public boolean GuiFissionReactor;
    @RegisterClass(GuiFissionReactorLogicAdapter.class)
    public boolean guiFissionReactorLogicAdapter;
    @RegisterClass(GuiFusionReactorController.class)
    public boolean guiFusionReactorController;
    @RegisterClass(GuiFusionReactorFuel.class)
    public boolean guiFusionReactorFuel;
    @RegisterClass(GuiFusionReactorHeat.class)
    public boolean guiFusionReactorHeat;
    @RegisterClass(GuiFusionReactorLogicAdapter.class)
    public boolean guiFusionReactorLogicAdapter;
    @RegisterClass(GuiFusionReactorStats.class)
    public boolean guiFusionReactorStats;
    @RegisterClass(GuiGasGenerator.class)
    public boolean guiGasGenerator;
    @RegisterClass(GuiHeatGenerator.class)
    public boolean guiHeatGenerator;
    @RegisterClass(GuiIndustrialTurbine.class)
    public boolean guiIndustrialTurbine;
    @RegisterClass(GuiTurbineStats.class)
    public boolean guiTurbineStats;
    @RegisterClass(GuiWindGenerator.class)
    public boolean guiWindGenerator;

}
