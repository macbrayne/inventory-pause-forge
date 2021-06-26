package de.macbrayne.forge.inventorypause.compat;

import java.util.function.Function;

public interface ModCompat {
    void register();

    Function<Class<?>, Boolean> getConfig();
}
