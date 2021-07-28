package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;
import mekanism.client.gui.*;
import mekanism.client.gui.item.GuiDictionary;
import mekanism.client.gui.item.GuiPersonalChestItem;
import mekanism.client.gui.item.GuiPortableTeleporter;
import mekanism.client.gui.item.GuiSeismicReader;
import mekanism.client.gui.machine.*;
import mekanism.client.gui.qio.*;
import mekanism.client.gui.robit.*;

@RegisterCompat(modId = "mekanism", configKey = "mekanismCompat")
public class MekanismConfig {
    // mekanism.client.gui.item.*
    @RegisterClass(GuiDictionary.class)
    public boolean guiDictionary;
    @RegisterClass(GuiPersonalChestItem.class)
    public boolean guiPersonalChestItem;
    @RegisterClass(GuiPortableTeleporter.class)
    public boolean guiPortableTeleporter;
    @RegisterClass(GuiSeismicReader.class)
    public boolean guiSeismicReader;

    // mekanism.client.gui.machine.*
    @RegisterClass(GuiAntiprotonicNucleosynthesizer.class)
    public boolean guiAntiprotonicNucleosynthesizer = true;
    @RegisterClass(GuiChemicalDissolutionChamber.class)
    public boolean guiChemicalDissolutionChamber = true;
    @RegisterClass(GuiChemicalInfuser.class)
    public boolean guiChemicalInfuser = true;
    @RegisterClass(GuiChemicalOxidizer.class)
    public boolean guiChemicalOxidizer = true;
    @RegisterClass(GuiChemicalWasher.class)
    public boolean guiChemicalWasher = true;
    @RegisterClass(GuiCombiner.class)
    public boolean guiCombiner = true;
    @RegisterClass(GuiDigitalMiner.class)
    public boolean guiDigitalMiner = true;
    @RegisterClass(GuiDigitalMinerConfig.class)
    public boolean guiDigitalMinerConfig = true;
    @RegisterClass(GuiElectricPump.class)
    public boolean guiElectricPump = true;
    @RegisterClass(GuiElectrolyticSeparator.class)
    public boolean guiElectrolyticSeparator = true;
    @RegisterClass(GuiFactory.class)
    public boolean guiFactory = true;
    @RegisterClass(GuiFluidicPlenisher.class)
    public boolean guiFluidicPlenisher = true;
    @RegisterClass(GuiFormulaicAssemblicator.class)
    public boolean guiFormulaicAssemblicator = true;
    @RegisterClass(GuiFuelwoodHeater.class)
    public boolean guiFuelwoodHeater = true;
    @RegisterClass(GuiIsotopicCentrifuge.class)
    public boolean guiIsotopicCentrifuge = true;
    @RegisterClass(GuiMetallurgicInfuser.class)
    public boolean guiMetallurgicInfuser = true;
    @RegisterClass(GuiNutritionalLiquifier.class)
    public boolean guiNutritionalLiquifier = true;
    @RegisterClass(GuiOredictionificator.class)
    public boolean guiOredictionificator = true;
    @RegisterClass(GuiPRC.class)
    public boolean guiPRC = true;
    @RegisterClass(GuiPrecisionSawmill.class)
    public boolean guiPrecisionSawmill = true;
    @RegisterClass(GuiResistiveHeater.class)
    public boolean guiResistiveHeater = true;
    @RegisterClass(GuiRotaryCondensentrator.class)
    public boolean guiRotaryCondensentrator = true;
    @RegisterClass(GuiSeismicVibrator.class)
    public boolean guiSeismicVibrator = true;
    @RegisterClass(GuiSolarNeutronActivator.class)
    public boolean guiSolarNeutronActivator = true;

    // mekanism.client.gui.qio.*
    @RegisterClass(GuiPortableQIODashboard.class)
    public boolean guiPortableQIODashboard = true;
    @RegisterClass(GuiQIODashboard.class)
    public boolean guiQIODashboard = true;
    @RegisterClass(GuiQIODriveArray.class)
    public boolean guiQIODriveArray = true;
    @RegisterClass(GuiQIOExporter.class)
    public boolean guiQIOExporter = true;
    @RegisterClass(GuiQIOImporter.class)
    public boolean guiQIOImporter = true;
    @RegisterClass(GuiQIOItemFrequencySelect.class)
    public boolean guiQIOItemFrequencySelect = true;
    @RegisterClass(GuiQIORedstoneAdapter.class)
    public boolean guiQIORedstoneAdapter = true;
    @RegisterClass(GuiQIOTileFrequencySelect.class)
    public boolean guiQIOTileFrequencySelect = true;

    // mekanism.client.gui.robit.*
    @RegisterClass(GuiRobitCrafting.class)
    public boolean guiRobitCrafting = true;
    @RegisterClass(GuiRobitInventory.class)
    public boolean guiRobitInventory = true;
    @RegisterClass(GuiRobitMain.class)
    public boolean guiRobitMain = true;
    @RegisterClass(GuiRobitRepair.class)
    public boolean guiRobitRepair = true;
    @RegisterClass(GuiRobitSmelting.class)
    public boolean guiRobitSmelting = false;

    //
    @RegisterClass(GuiBoilerStats.class)
    public boolean guiBoilerStats = true;
    @RegisterClass(GuiChemicalTank.class)
    public boolean guiChemicalTank = true;
    @RegisterClass(GuiDynamicTank.class)
    public boolean guiDynamicTank = true;
    @RegisterClass(GuiEnergyCube.class)
    public boolean guiEnergyCube = true;
    @RegisterClass(GuiFluidTank.class)
    public boolean guiFluidTank = true;
    @RegisterClass(GuiInductionMatrix.class)
    public boolean guiInductionMatrix = true;
    @RegisterClass(GuiLaserAmplifier.class)
    public boolean guiLaserAmplifier = true;
    @RegisterClass(GuiLaserTractorBeam.class)
    public boolean guiLaserTractorBeam = true;
    @RegisterClass(GuiLogisticalSorter.class)
    public boolean guiLogisticalSorter = true;
    @RegisterClass(GuiMatrixStats.class)
    public boolean guiMatrixStats = true;
    @RegisterClass(GuiModificationStation.class)
    public boolean guiModificationStation = true;
    @RegisterClass(GuiModuleTweaker.class)
    public boolean guiModuleTweaker = true;
    @RegisterClass(GuiPersonalChestTile.class)
    public boolean guiPersonalChestTile = true;
    @RegisterClass(GuiQuantumEntangloporter.class)
    public boolean guiQuantumEntangloporter = true;
    @RegisterClass(GuiSecurityDesk.class)
    public boolean guiSecurityDesk = true;
    @RegisterClass(GuiSPS.class)
    public boolean guiSPS = true;
    @RegisterClass(GuiTeleporter.class)
    public boolean guiTeleporter = true;
    @RegisterClass(GuiThermalEvaporationController.class)
    public boolean guiThermalEvaporationController = true;
    @RegisterClass(GuiThermoelectricBoiler.class)
    public boolean guiThermoelectricBoiler = true;
    @RegisterClass(GuiUpgradeManagement.class)
    public boolean guiUpgradeManagement = true;
}
