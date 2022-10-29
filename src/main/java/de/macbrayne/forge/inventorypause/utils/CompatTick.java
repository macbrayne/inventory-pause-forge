package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class CompatTick {
	public static int timeUntilCompatTick = AutoConfig.getConfigHolder(ModConfig.class).getConfig().modCompat.timeBetweenCompatTicks;
}
