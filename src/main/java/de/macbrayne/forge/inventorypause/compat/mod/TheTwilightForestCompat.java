package de.macbrayne.forge.inventorypause.compat.mod;

import twilightforest.client.GuiTFGoblinCrafting;

public class TheTwilightForestCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(GuiTFGoblinCrafting.class, getConfig());
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.theTwilightForestCompat;
    }
}
