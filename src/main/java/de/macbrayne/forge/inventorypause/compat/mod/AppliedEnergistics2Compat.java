package de.macbrayne.forge.inventorypause.compat.mod;

import appeng.client.gui.implementations.*;
import de.macbrayne.forge.inventorypause.common.ModConfig;

public class AppliedEnergistics2Compat implements ModCompat {
    @Override
    public void register() {
        ModConfig.ModCompat.FineTuning fineTuning = config.modCompat.fineTuning;
        modDictionary.register(CellWorkbenchScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.cellWorkbenchScreen);
        modDictionary.register(ChestScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.chestScreen);
        modDictionary.register(CondenserScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.condenserScreen);
        modDictionary.register(DriveScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.driveScreen);
        modDictionary.register(FormationPlaneScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.formationPlaneScreen);
        modDictionary.register(GrinderScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.grinderScreen);
        modDictionary.register(InscriberScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.inscriberScreen);
        modDictionary.register(InterfaceScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.interfaceScreen);
        modDictionary.register(IOBusScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.IOBusScreen);
        modDictionary.register(IOPortScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.IOPortScreen);
        modDictionary.register(LevelEmitterScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.levelEmitterScreen);
        modDictionary.register(MolecularAssemblerScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.molecularAssemblerScreen);
        modDictionary.register(PriorityScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.priorityScreen);
        modDictionary.register(QNBScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.QNBScreen);
        modDictionary.register(QuartzKnifeScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.quartzKnifeScreen);
        modDictionary.register(SecurityStationScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.securityStationScreen);
        modDictionary.register(SkyChestScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.skyChestScreen);
        modDictionary.register(SpatialAnchorScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.spatialAnchorScreen);
        modDictionary.register(SpatialIOPortScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.spatialIOPortScreen);
        modDictionary.register(StorageBusScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.storageBusScreen);
        modDictionary.register(UpgradeableScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.upgradeableScreen);
        modDictionary.register(VibrationChamberScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.vibrationChamberScreen);
        modDictionary.register(WirelessScreen.class, getConfig(),
                () -> fineTuning.appliedEnergistics2Config.wirelessScreen);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.ironchestCompat;
    }

    public static class AppliedEnergistics2Config {
        public boolean cellWorkbenchScreen = true;
        public boolean chestScreen = true;
        public boolean condenserScreen = true;
        public boolean driveScreen = true;
        public boolean formationPlaneScreen = true;
        public boolean grinderScreen = true;
        public boolean inscriberScreen = true;
        public boolean interfaceScreen = true;
        public boolean IOBusScreen = true;
        public boolean IOPortScreen = true;
        public boolean levelEmitterScreen = true;
        public boolean molecularAssemblerScreen = true;
        public boolean priorityScreen = true;
        public boolean QNBScreen = true;
        public boolean quartzKnifeScreen = true;
        public boolean securityStationScreen = true;
        public boolean skyChestScreen = true;
        public boolean spatialAnchorScreen = true;
        public boolean spatialIOPortScreen = true;
        public boolean storageBusScreen = true;
        public boolean upgradeableScreen = true;
        public boolean vibrationChamberScreen = true;
        public boolean wirelessScreen = true;
    }
}
