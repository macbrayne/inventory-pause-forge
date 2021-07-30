package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;
import me.desht.pneumaticcraft.client.gui.*;
import me.desht.pneumaticcraft.client.gui.semiblock.GuiLogisticsProvider;
import me.desht.pneumaticcraft.client.gui.semiblock.GuiLogisticsRequester;
import me.desht.pneumaticcraft.client.gui.semiblock.GuiLogisticsStorage;

@RegisterCompat(modId = "pneumaticcraft", configKey = "pneumaticcraftCompat")
public class PneumaticCraftConfig {
    // me.desht.pneumaticcraft.client.gui.semiblock.*
    @RegisterClass(GuiLogisticsProvider.class)
    boolean guiLogisticsProvider = true;
    @RegisterClass(GuiLogisticsRequester.class)
    boolean guiLogisticsRequester = true;
    @RegisterClass(GuiLogisticsStorage.class)
    boolean guiLogisticsStorage = true;


    // me.desht.pneumaticcraft.client.gui.*
    @RegisterClass(GuiAdvancedAirCompressor.class)
    boolean guiAdvancedAirCompressor = true;
    @RegisterClass(GuiAdvancedLiquidCompressor.class)
    boolean guiAdvancedLiquidCompressor = true;
    @RegisterClass(GuiAerialInterface.class)
    boolean guiAerialInterface = true;
    @RegisterClass(GuiAirCannon.class)
    boolean guiAirCannon = true;
    @RegisterClass(GuiAirCompressor.class)
    boolean guiAirCompressor = true;
    @RegisterClass(GuiAmadron.class)
    boolean guiAmadron = true;
    @RegisterClass(GuiAmadronAddTrade.class)
    boolean guiAmadronAddTrade = true;
    @RegisterClass(GuiAssemblyController.class)
    boolean guiAssemblyController = true;
    @RegisterClass(GuiCreativeCompressedIronBlock.class)
    boolean guiCreativeCompressedIronBlock = true;
    @RegisterClass(GuiCreativeCompressor.class)
    boolean guiCreativeCompressor = true;
    @RegisterClass(GuiDrone.class)
    boolean guiDrone = true;
    @RegisterClass(GuiElectrostaticCompressor.class)
    boolean guiElectrostaticCompressor = true;
    @RegisterClass(GuiElevator.class)
    boolean guiElevator = true;
    @RegisterClass(GuiEtchingTank.class)
    boolean guiEtchingTank = true;
    @RegisterClass(GuiFluidMixer.class)
    boolean guiFluidMixer = true;
    @RegisterClass(GuiFluidTank.class)
    boolean guiFluidTank = true;
    @RegisterClass(GuiFluxCompressor.class)
    boolean guiFluxCompressor = true;
    @RegisterClass(GuiGasLift.class)
    boolean guiGasLift = true;
    @RegisterClass(GuiInventorySearcher.class)
    boolean guiInventorySearcher = true;
    @RegisterClass(GuiItemSearcher.class)
    boolean guiItemSearcher = true;
    @RegisterClass(GuiJackhammer.class)
    boolean guiJackhammer = true;
    @RegisterClass(GuiJackHammerSetup.class)
    boolean guiJackHammerSetup = true;
    @RegisterClass(GuiKeroseneLamp.class)
    boolean guiKeroseneLamp = true;
    @RegisterClass(GuiLiquidCompressor.class)
    boolean guiLiquidCompressor = true;
    @RegisterClass(GuiLiquidHopper.class)
    boolean guiLiquidHopper = true;
    @RegisterClass(GuiMinigun.class)
    boolean guiMinigun = true;
    @RegisterClass(GuiMinigunMagazine.class)
    boolean guiMinigunMagazine = true;
    @RegisterClass(GuiOmnidirectionalHopper.class)
    boolean guiOmnidirectionalHopper = true;
    @RegisterClass(GuiPneumaticArmor.class)
    boolean guiPneumaticArmor = true;
    @RegisterClass(GuiPneumaticDoorBase.class)
    boolean guiPneumaticDoorBase = true;
    @RegisterClass(GuiPneumaticDynamo.class)
    boolean guiPneumaticDynamo = true;
    @RegisterClass(GuiPressureChamber.class)
    boolean guiPressureChamber = true;
    @RegisterClass(GuiPressureChamberInterface.class)
    boolean guiPressureChamberInterface = true;
    @RegisterClass(GuiPressurizedSpawner.class)
    boolean guiPressurizedSpawner = true;
    @RegisterClass(GuiProgrammableController.class)
    boolean guiProgrammableController = true;
    @RegisterClass(GuiProgrammer.class)
    boolean guiProgrammer = true;
    @RegisterClass(GuiRefineryController.class)
    boolean guiRefineryController = true;
    @RegisterClass(GuiReinforcedChest.class)
    boolean guiReinforcedChest = true;
    @RegisterClass(GuiRemote.class)
    boolean guiRemote = true;
    @RegisterClass(GuiRemoteEditor.class)
    boolean guiRemoteEditor = true;
    @RegisterClass(GuiSecurityStationHacking.class)
    boolean guiSecurityStationHacking = true;
    @RegisterClass(GuiSecurityStationInventory.class)
    boolean guiSecurityStationInventory = true;
    @RegisterClass(GuiSentryTurret.class)
    boolean guiSentryTurret = true;
    @RegisterClass(GuiSmartChest.class)
    boolean guiSmartChest = true;
    @RegisterClass(GuiSpawnerExtractor.class)
    boolean guiSpawnerExtractor = true;
    @RegisterClass(GuiTagWorkbench.class)
    boolean guiTagWorkbench = true;
    @RegisterClass(GuiThermalCompressor.class)
    boolean guiThermalCompressor = true;
    @RegisterClass(GuiThermopneumaticProcessingPlant.class)
    boolean guiThermopneumaticProcessingPlant = true;
    @RegisterClass(GuiUniversalSensor.class)
    boolean guiUniversalSensor = true;
    @RegisterClass(GuiUVLightBox.class)
    boolean guiUVLightBox = true;
    @RegisterClass(GuiVacuumPump.class)
    boolean guiVacuumPump = true;
    @RegisterClass(GuiVacuumTrap.class)
    boolean guiVacuumTrap = true;
}
