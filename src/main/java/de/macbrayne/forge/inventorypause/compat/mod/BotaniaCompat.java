package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;
import vazkii.botania.client.gui.bag.GuiFlowerBag;
import vazkii.botania.client.gui.box.GuiBaubleBox;

import java.util.function.Function;

public class BotaniaCompat implements ModCompat {
    @Override
    public void register() {
        ModHelper.register(GuiFlowerBag.class, getConfig());
        ModHelper.register(GuiBaubleBox.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return value -> config.modCompat.botaniaCompat;
    }
}
