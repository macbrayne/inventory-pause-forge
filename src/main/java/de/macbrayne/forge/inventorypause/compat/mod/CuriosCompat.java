package de.macbrayne.forge.inventorypause.compat.mod;

import top.theillusivec4.curios.client.gui.CuriosScreen;

public class CuriosCompat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(CuriosScreen.class, getConfig());
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.curiosCompat;
    }
}
