package de.macbrayne.forge.inventorypause.compat.mod.custom;

import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

public class BotaniaCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(GuiFlowerBag.class, this::getConfigKey);
        modDictionary.register(GuiBaubleBox.class, this::getConfigKey);
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.botaniaCompat;
    }
}
