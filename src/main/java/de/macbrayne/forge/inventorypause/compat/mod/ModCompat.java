package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.function.BooleanSupplier;

public interface ModCompat {
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    ModScreenDictionary modDictionary = Reference.getModScreenDictionary();

    void register();

    default BooleanSupplier getConfig() {
        return this::getConfigKey;
    }

    boolean getConfigKey();
}
