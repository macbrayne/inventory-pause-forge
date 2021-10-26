package de.macbrayne.forge.inventorypause.compat.mod.custom;

import com.progwml6.ironchest.client.screen.IronChestScreen;
import de.macbrayne.forge.inventorypause.compat.CustomCompat;

public class IronchestCompat implements CustomCompat {
    @Override
    public void register() {
        SCREEN_DICTIONARY.register(IronChestScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.ironchestCompat;
    }
}
