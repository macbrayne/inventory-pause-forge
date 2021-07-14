package de.macbrayne.forge.inventorypause.compat.mod;

import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

public class BotaniaCompat implements CustomModCompat {
    @Override
    public void register() {
        modDictionary.register(GuiFlowerBag.class, getConfig());
        modDictionary.register(GuiBaubleBox.class, getConfig());
    }

    @Override
    public boolean getConfigKey() {
        return config.modCompat.botaniaCompat;
    }
}
