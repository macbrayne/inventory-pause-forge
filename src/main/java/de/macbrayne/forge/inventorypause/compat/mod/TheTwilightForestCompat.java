package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import twilightforest.client.GuiTFGoblinCrafting;

import java.util.function.Function;

public class TheTwilightForestCompat implements ModCompat {
    @Override
    public void register() {
        ModScreenDictionary.register(GuiTFGoblinCrafting.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.theTwilightForestCompat;
    }
}
