package de.macbrayne.forge.inventorypause.compat.mod;

import com.progwml6.ironchest.client.screen.IronChestScreen;
import de.macbrayne.forge.inventorypause.compat.ModCompat;
import de.macbrayne.forge.inventorypause.compat.ModHelper;

import java.util.function.Function;

public class IronchestCompat implements ModCompat {
    @Override
    public void register() {
        ModHelper.register(IronChestScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
