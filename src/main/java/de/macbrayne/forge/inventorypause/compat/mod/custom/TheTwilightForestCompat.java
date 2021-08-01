package de.macbrayne.forge.inventorypause.compat.mod.custom;

import de.macbrayne.forge.inventorypause.compat.CustomCompat;
import twilightforest.client.GuiTFGoblinCrafting;

public class TheTwilightForestCompat implements CustomCompat {
    @Override
    public void register() {
        modDictionary.register(GuiTFGoblinCrafting.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.theTwilightForestCompat;
    }
}
