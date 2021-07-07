package de.macbrayne.forge.inventorypause.compat.mod;

import net.blay09.mods.waystones.client.gui.screen.SharestoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WarpPlateScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSettingsScreen;

public class WaystonesCompat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(WaystoneSelectionScreen.class, getConfig());
        modDictionary.register(WarpPlateScreen.class, getConfig());
        modDictionary.register(SharestoneSelectionScreen.class, getConfig());
        modDictionary.register(WaystoneSettingsScreen.class, getConfig());
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.waystonesCompat;
    }
}
