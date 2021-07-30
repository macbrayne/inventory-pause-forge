package de.macbrayne.forge.inventorypause.compat.mod.custom;

import com.teammetallurgy.aquaculture.client.gui.screen.TackleBoxScreen;

public class Aquaculture2Compat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(TackleBoxScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.aquaculture2Compat;
    }
}
