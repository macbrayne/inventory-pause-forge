package de.macbrayne.forge.inventorypause.compat.mod.custom;

import com.hrznstudio.titanium.client.screen.container.BasicAddonScreen;
import de.macbrayne.forge.inventorypause.compat.CustomCompat;

public class TitaniumCompat implements CustomCompat {
    @Override
    public void register() {
        SCREEN_DICTIONARY.register(BasicAddonScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.titaniumCompat;
    }
}
