package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import net.blay09.mods.waystones.client.gui.screen.SharestoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WarpPlateScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSettingsScreen;

public class WaystonesCompat implements ModCompat {
    @Override
    public void register() {
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.waystonesCompat;
    }

    public static class WaystonesConfig {
        @RegisterClass(WaystoneSelectionScreen.class)
        boolean waystonesSelectionScreen = true;
        @RegisterClass(WarpPlateScreen.class)
        boolean warpPlateScreen = true;
        @RegisterClass(SharestoneSelectionScreen.class)
        boolean sharestoneSelectionScreen = true;
        @RegisterClass(WaystoneSettingsScreen.class)
        boolean waystonesSettingsScreen = true;
    }
}
