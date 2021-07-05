package de.macbrayne.forge.inventorypause.compat.mod;

import top.theillusivec4.curios.client.gui.CuriosScreen;

import java.util.function.Function;

public class CuriosCompat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(CuriosScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.curiosCompat;
    }
}
