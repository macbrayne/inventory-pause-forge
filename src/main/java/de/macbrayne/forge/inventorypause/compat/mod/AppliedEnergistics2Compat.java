package de.macbrayne.forge.inventorypause.compat.mod;

import appeng.client.gui.implementations.*;
import de.macbrayne.forge.inventorypause.annotation.RegisterClass;

public class AppliedEnergistics2Compat implements GenericModCompat {

    @Override
    public boolean getConfigKey() {
        return config.modCompat.appliedEnergistics2Compat;
    }

    public static class AppliedEnergistics2Config {
        @RegisterClass(CellWorkbenchScreen.class)
        public boolean cellWorkbenchScreen = true;
        @RegisterClass(ChestScreen.class)
        public boolean chestScreen = true;
        @RegisterClass(CondenserScreen.class)
        public boolean condenserScreen = true;
        @RegisterClass(DriveScreen.class)
        public boolean driveScreen = true;
        @RegisterClass(FormationPlaneScreen.class)
        public boolean formationPlaneScreen = true;
        @RegisterClass(GrinderScreen.class)
        public boolean grinderScreen = true;
        @RegisterClass(InscriberScreen.class)
        public boolean inscriberScreen = true;
        @RegisterClass(InterfaceScreen.class)
        public boolean interfaceScreen = true;
        @RegisterClass(IOBusScreen.class)
        public boolean IOBusScreen = true;
        @RegisterClass(IOPortScreen.class)
        public boolean IOPortScreen = true;
        @RegisterClass(LevelEmitterScreen.class)
        public boolean levelEmitterScreen = true;
        @RegisterClass(MolecularAssemblerScreen.class)
        public boolean molecularAssemblerScreen = true;
        @RegisterClass(PriorityScreen.class)
        public boolean priorityScreen = true;
        @RegisterClass(QNBScreen.class)
        public boolean QNBScreen = true;
        @RegisterClass(QuartzKnifeScreen.class)
        public boolean quartzKnifeScreen = true;
        @RegisterClass(SecurityStationScreen.class)
        public boolean securityStationScreen = true;
        @RegisterClass(SkyChestScreen.class)
        public boolean skyChestScreen = true;
        @RegisterClass(SpatialAnchorScreen.class)
        public boolean spatialAnchorScreen = true;
        @RegisterClass(SpatialIOPortScreen.class)
        public boolean spatialIOPortScreen = true;
        @RegisterClass(StorageBusScreen.class)
        public boolean storageBusScreen = true;
        @RegisterClass(VibrationChamberScreen.class)
        public boolean vibrationChamberScreen = true;
        @RegisterClass(WirelessScreen.class)
        public boolean wirelessScreen = true;
    }
}
