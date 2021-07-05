package de.macbrayne.forge.inventorypause.compat.mod;

import com.progwml6.ironchest.client.screen.IronChestScreen;
import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;

import java.util.function.Function;

public class IronchestCompat implements ModCompat {
    @Override
    public void register() {
        ModScreenDictionary.register(IronChestScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
