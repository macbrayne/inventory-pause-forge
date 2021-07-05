package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import de.macbrayne.forge.inventorypause.compat.VanillaScreenDictionary;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

public class ScreenHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public static boolean isConfiguredScreen(Screen screen) {
        return screen != null && config.enabled && (VanillaScreenDictionary.handleScreen(screen.getClass()) ||
                ModScreenDictionary.handleScreen(screen.getClass()) || isCustomMenu(screen));
    }

    private static boolean isCustomMenu(Screen screen) {
        for (String s : config.modCompat.customScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
