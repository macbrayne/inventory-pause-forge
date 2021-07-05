package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import de.macbrayne.forge.inventorypause.compat.VanillaScreenDictionary;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

public class ScreenHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private static final ModScreenDictionary modDictionary = Reference.getModScreenDictionary();
    private static final VanillaScreenDictionary vanillaDictionary = Reference.getVanillaScreenDictionary();

    public static boolean isConfiguredScreen(Screen screen) {
        return screen != null && config.enabled && (vanillaDictionary.handleScreen(screen.getClass()) ||
                modDictionary.handleScreen(screen.getClass()) || isCustomMenu(screen));
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
