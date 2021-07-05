package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;
import twilightforest.client.GuiTFGoblinCrafting;

import java.util.function.Function;

public class TheTwilightForestCompat implements ModCompat {
    @Override
    public void register() {
        ModHelper.register(GuiTFGoblinCrafting.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.theTwilightForestCompat;
    }
}
