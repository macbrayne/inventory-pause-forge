package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;

public interface GenericCompat {
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    ScreenDictionary modDictionary = Reference.getScreenDictionary();

    boolean getConfigKey();
}
