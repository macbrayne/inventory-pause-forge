package de.macbrayne.forge.inventorypause.compat.mod.custom;

import top.theillusivec4.curios.client.gui.CuriosScreen;

public class CuriosCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(CuriosScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.curiosCompat;
    }
}
