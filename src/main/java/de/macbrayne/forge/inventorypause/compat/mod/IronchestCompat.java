package de.macbrayne.forge.inventorypause.compat.mod;

import com.progwml6.ironchest.client.screen.IronChestScreen;

public class IronchestCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(IronChestScreen.class, getConfig());
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.ironchestCompat;
    }
}
