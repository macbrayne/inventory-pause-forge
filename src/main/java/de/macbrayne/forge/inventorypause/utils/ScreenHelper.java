package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

public class ScreenHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public static boolean isConfiguredScreen(Screen screen) {
        return screen != null && config.enabled && (VanillaHelper.handleScreen(screen.getClass()) || isCustomMenu(screen));
    }

    private static boolean isCustomMenu(Screen screen) {
        for (String s : config.customScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
