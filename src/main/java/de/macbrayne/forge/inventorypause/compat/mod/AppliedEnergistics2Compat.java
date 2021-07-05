package de.macbrayne.forge.inventorypause.compat.mod;

import appeng.client.gui.implementations.*;
import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;

import java.util.function.Function;

public class AppliedEnergistics2Compat implements ModCompat {
    @Override
    public void register() {
        ModScreenDictionary.register(CellWorkbenchScreen.class, getConfig());
        ModScreenDictionary.register(ChestScreen.class, getConfig());
        ModScreenDictionary.register(CondenserScreen.class, getConfig());
        ModScreenDictionary.register(DriveScreen.class, getConfig());
        ModScreenDictionary.register(FormationPlaneScreen.class, getConfig());
        ModScreenDictionary.register(GrinderScreen.class, getConfig());
        ModScreenDictionary.register(InscriberScreen.class, getConfig());
        ModScreenDictionary.register(InterfaceScreen.class, getConfig());
        ModScreenDictionary.register(IOBusScreen.class, getConfig());
        ModScreenDictionary.register(IOPortScreen.class, getConfig());
        ModScreenDictionary.register(LevelEmitterScreen.class, getConfig());
        ModScreenDictionary.register(MolecularAssemblerScreen.class, getConfig());
        ModScreenDictionary.register(PriorityScreen.class, getConfig());
        ModScreenDictionary.register(QNBScreen.class, getConfig());
        ModScreenDictionary.register(QuartzKnifeScreen.class, getConfig());
        ModScreenDictionary.register(SecurityStationScreen.class, getConfig());
        ModScreenDictionary.register(SkyChestScreen.class, getConfig());
        ModScreenDictionary.register(SpatialAnchorScreen.class, getConfig());
        ModScreenDictionary.register(SpatialIOPortScreen.class, getConfig());
        ModScreenDictionary.register(StorageBusScreen.class, getConfig());
        ModScreenDictionary.register(UpgradeableScreen.class, getConfig());
        ModScreenDictionary.register(VibrationChamberScreen.class, getConfig());
        ModScreenDictionary.register(WirelessScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
