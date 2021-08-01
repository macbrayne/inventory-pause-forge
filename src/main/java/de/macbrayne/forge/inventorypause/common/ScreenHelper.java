package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ScreenHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private static final ScreenDictionary modDictionary = Reference.getScreenDictionary();

    public static boolean isConfiguredScreen(@Nullable Screen screen) {
        return screen != null && config.enabled && (modDictionary.handleScreen(screen.getClass()) || isCustomMenu(screen));
    }

    private static boolean isCustomMenu(@Nonnull Screen screen) {
        for (String s : config.modCompat.customScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
