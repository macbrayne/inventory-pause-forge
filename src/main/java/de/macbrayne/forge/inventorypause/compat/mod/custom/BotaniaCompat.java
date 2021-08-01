package de.macbrayne.forge.inventorypause.compat.mod.custom;

import de.macbrayne.forge.inventorypause.compat.CustomCompat;
import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

public class BotaniaCompat implements CustomCompat {
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
