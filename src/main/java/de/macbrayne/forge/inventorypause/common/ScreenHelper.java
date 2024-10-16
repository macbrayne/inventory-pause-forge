// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScreenHelper {
    private static final ScreenDictionary modDictionary = InventoryPause.getScreenDictionary();

    public static boolean isConfiguredScreen(@Nullable Screen screen) {
        return screen != null && InventoryPause.MOD_CONFIG.isEnabled() &&
                (modDictionary.handleScreen(screen.getClass()) != PauseMode.OFF || isCustomMenu(screen) || isCompatScreen(screen));
    }


    public static boolean isPauseScreen(Screen caller) {
        if ((ScreenHelper.isCompatScreen(caller) || modDictionary.handleScreen(caller.getClass()) == PauseMode.SLOWMO)) {
            return false;
        }
        return ScreenHelper.isConfiguredScreen(caller);
    }

    public static boolean isSlowmoScreen(Screen caller) {
        if ((ScreenHelper.isCustomMenu(caller) || modDictionary.handleScreen(caller.getClass()) == PauseMode.ON)) {
            return false;
        }
        return ScreenHelper.isConfiguredScreen(caller);
    }

    private static boolean isCustomMenu(@NotNull Screen screen) {
        for (String s : InventoryPause.MOD_CONFIG.modCompat.customScreens) {
            if (screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCompatScreen(@NotNull Screen screen) {
        for (String s : InventoryPause.MOD_CONFIG.modCompat.compatScreens) {
            if (screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
