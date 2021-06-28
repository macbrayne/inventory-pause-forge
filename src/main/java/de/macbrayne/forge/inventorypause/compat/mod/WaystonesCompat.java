package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;
import de.macbrayne.forge.inventorypause.utils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.blay09.mods.waystones.client.gui.screen.*;

import java.util.function.Function;

public class WaystonesCompat implements ModCompat {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Override
    public void register() {
        ModHelper.register(WaystoneSelectionScreen.class, getConfig());
        ModHelper.register(WarpPlateScreen.class, getConfig());
        ModHelper.register(SharestoneSelectionScreen.class, getConfig());
        ModHelper.register(WaystoneSettingsScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.waystonesCompat;
    }
}
