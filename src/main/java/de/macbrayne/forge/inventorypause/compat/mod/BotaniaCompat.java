package de.macbrayne.forge.inventorypause.compat.mod;

import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

import java.util.function.Function;

public class BotaniaCompat implements ModCompat {
    @Override
    public void register() {
        modDictionary.register(GuiFlowerBag.class, getConfig());
        modDictionary.register(GuiBaubleBox.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.botaniaCompat;
    }
}
