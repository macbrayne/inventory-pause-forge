package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import net.blay09.mods.waystones.client.gui.screen.SharestoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WarpPlateScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreen;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSettingsScreen;

import java.util.function.Function;

public class WaystonesCompat implements ModCompat {
    @Override
    public void register() {
        ModScreenDictionary.register(WaystoneSelectionScreen.class, getConfig());
        ModScreenDictionary.register(WarpPlateScreen.class, getConfig());
        ModScreenDictionary.register(SharestoneSelectionScreen.class, getConfig());
        ModScreenDictionary.register(WaystoneSettingsScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.waystonesCompat;
    }
}
