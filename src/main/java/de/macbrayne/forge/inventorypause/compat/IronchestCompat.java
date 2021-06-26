package de.macbrayne.forge.inventorypause.compat;

import com.progwml6.ironchest.client.screen.IronChestScreen;
import de.macbrayne.forge.inventorypause.utils.ModConfig;
import de.macbrayne.forge.inventorypause.utils.ModHelper;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.function.Function;

public class IronchestCompat implements ModCompat {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Override
    public void register() {
        ModHelper.register(IronChestScreen.class, getConfig());
    }

    @Override
    public Function<Class<?>, Boolean> getConfig() {
        return (value) -> config.modCompat.ironchestCompat;
    }
}
