package de.macbrayne.forge.inventorypause.compat.mod.custom;

import de.macbrayne.forge.inventorypause.compat.CustomCompat;
import top.theillusivec4.curios.client.gui.CuriosScreen;

public class CuriosCompat implements CustomCompat {
    @Override
    public void register() {
        SCREEN_DICTIONARY.register(CuriosScreen.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.curiosCompat;
    }
}
