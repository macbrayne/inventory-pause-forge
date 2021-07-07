package de.macbrayne.forge.inventorypause.compat.mod;

import net.blay09.mods.waystones.client.gui.screen.SharestoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WarpPlateScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSettingsScreen;

public class WaystonesCompat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(WaystoneSelectionScreen.class, getConfig(),
                () -> config.modCompat.fineTuning.waystonesConfig.waystonesSelectionScreen);
        modDictionary.register(WarpPlateScreen.class, getConfig(),
                () -> config.modCompat.fineTuning.waystonesConfig.warpPlateScreen);
        modDictionary.register(SharestoneSelectionScreen.class, getConfig(),
                () -> config.modCompat.fineTuning.waystonesConfig.sharestoneSelectionScreen);
        modDictionary.register(WaystoneSettingsScreen.class, getConfig(),
                () -> config.modCompat.fineTuning.waystonesConfig.waystonesSettingsScreen);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.waystonesCompat;
    }

    public static class WaystonesConfig {
        boolean waystonesSelectionScreen = true;
        boolean warpPlateScreen = true;
        boolean sharestoneSelectionScreen = true;
        boolean waystonesSettingsScreen = true;
    }
}
