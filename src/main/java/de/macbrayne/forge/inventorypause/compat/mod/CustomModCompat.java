package de.macbrayne.forge.inventorypause.compat.mod;

import java.util.function.BooleanSupplier;

public interface CustomModCompat extends GenericModCompat {
    void register();

    default BooleanSupplier getConfig() {
        return this::getConfigKey;
    }
}
