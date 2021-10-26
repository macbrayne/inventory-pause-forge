package de.macbrayne.forge.inventorypause.compat.mod.custom;

import com.teammetallurgy.aquaculture.client.gui.screen.TackleBoxScreen;
import de.macbrayne.forge.inventorypause.compat.CustomCompat;

public class Aquaculture2Compat implements CustomCompat {
    @Override
    public void register() {
        SCREEN_DICTIONARY.register(TackleBoxScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.aquaculture2Compat;
    }
}
