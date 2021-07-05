package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

import java.util.function.Function;

public class BotaniaCompat implements ModCompat {
    @Override
    public void register() {
        ModScreenDictionary.register(GuiFlowerBag.class, getConfig());
        ModScreenDictionary.register(GuiBaubleBox.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.botaniaCompat;
    }
}
