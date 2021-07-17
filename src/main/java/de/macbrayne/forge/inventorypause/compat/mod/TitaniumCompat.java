package de.macbrayne.forge.inventorypause.compat.mod;

import com.hrznstudio.titanium.client.screen.container.BasicAddonScreen;

public class TitaniumCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(BasicAddonScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.titaniumCompat;
    }
}
