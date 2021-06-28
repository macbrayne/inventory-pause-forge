package de.macbrayne.forge.inventorypause.compat.mod;

import appeng.client.gui.implementations.*;
import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;
import de.macbrayne.forge.inventorypause.utils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.function.Function;

public class AppliedEnergistics2Compat implements ModCompat {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Override
    public void register() {
        ModHelper.register(CellWorkbenchScreen.class, getConfig());
        ModHelper.register(ChestScreen.class, getConfig());
        ModHelper.register(CondenserScreen.class, getConfig());
        ModHelper.register(DriveScreen.class, getConfig());
        ModHelper.register(FormationPlaneScreen.class, getConfig());
        ModHelper.register(GrinderScreen.class, getConfig());
        ModHelper.register(InscriberScreen.class, getConfig());
        ModHelper.register(InterfaceScreen.class, getConfig());
        ModHelper.register(IOBusScreen.class, getConfig());
        ModHelper.register(IOPortScreen.class, getConfig());
        ModHelper.register(LevelEmitterScreen.class, getConfig());
        ModHelper.register(MolecularAssemblerScreen.class, getConfig());
        ModHelper.register(PriorityScreen.class, getConfig());
        ModHelper.register(QNBScreen.class, getConfig());
        ModHelper.register(QuartzKnifeScreen.class, getConfig());
        ModHelper.register(SecurityStationScreen.class, getConfig());
        ModHelper.register(SkyChestScreen.class, getConfig());
        ModHelper.register(SpatialAnchorScreen.class, getConfig());
        ModHelper.register(SpatialIOPortScreen.class, getConfig());
        ModHelper.register(StorageBusScreen.class, getConfig());
        ModHelper.register(UpgradeableScreen.class, getConfig());
        ModHelper.register(VibrationChamberScreen.class, getConfig());
        ModHelper.register(WirelessScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
