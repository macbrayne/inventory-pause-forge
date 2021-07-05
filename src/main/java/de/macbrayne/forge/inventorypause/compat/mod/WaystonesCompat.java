package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;
import net.blay09.mods.waystones.client.gui.screen.SharestoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WarpPlateScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSettingsScreen;

import java.util.function.Function;

public class WaystonesCompat implements ModCompat {
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
