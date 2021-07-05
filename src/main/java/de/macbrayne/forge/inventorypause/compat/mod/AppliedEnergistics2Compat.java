package de.macbrayne.forge.inventorypause.compat.mod;

import appeng.client.gui.implementations.*;

import java.util.function.Function;

public class AppliedEnergistics2Compat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(CellWorkbenchScreen.class, getConfig());
        modDictionary.register(ChestScreen.class, getConfig());
        modDictionary.register(CondenserScreen.class, getConfig());
        modDictionary.register(DriveScreen.class, getConfig());
        modDictionary.register(FormationPlaneScreen.class, getConfig());
        modDictionary.register(GrinderScreen.class, getConfig());
        modDictionary.register(InscriberScreen.class, getConfig());
        modDictionary.register(InterfaceScreen.class, getConfig());
        modDictionary.register(IOBusScreen.class, getConfig());
        modDictionary.register(IOPortScreen.class, getConfig());
        modDictionary.register(LevelEmitterScreen.class, getConfig());
        modDictionary.register(MolecularAssemblerScreen.class, getConfig());
        modDictionary.register(PriorityScreen.class, getConfig());
        modDictionary.register(QNBScreen.class, getConfig());
        modDictionary.register(QuartzKnifeScreen.class, getConfig());
        modDictionary.register(SecurityStationScreen.class, getConfig());
        modDictionary.register(SkyChestScreen.class, getConfig());
        modDictionary.register(SpatialAnchorScreen.class, getConfig());
        modDictionary.register(SpatialIOPortScreen.class, getConfig());
        modDictionary.register(StorageBusScreen.class, getConfig());
        modDictionary.register(UpgradeableScreen.class, getConfig());
        modDictionary.register(VibrationChamberScreen.class, getConfig());
        modDictionary.register(WirelessScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
