package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.function.Function;

public interface ModCompat {
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    void register();

    Function<Class<?>, Boolean> getConfig();
}
