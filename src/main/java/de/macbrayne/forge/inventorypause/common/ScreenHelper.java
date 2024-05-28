// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import de.macbrayne.forge.inventorypause.events.ForgeEventBus;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScreenHelper {
    private static final ScreenDictionary modDictionary = InventoryPause.getScreenDictionary();

    public static boolean isConfiguredScreen(@Nullable Screen screen) {
        return screen != null && InventoryPause.MOD_CONFIG.enabled &&
                (modDictionary.handleScreen(screen.getClass()) != PauseMode.OFF || isCustomMenu(screen) || isCompatScreen(screen));
    }


    public static boolean isPauseScreen(Screen caller) {
        if ((ScreenHelper.isCompatScreen(caller) || modDictionary.handleScreen(caller.getClass()) == PauseMode.SLOWMO) && ForgeEventBus.timeUntilCompatTick == 1) {
            return false;
        }
        if(ScreenHelper.isConfiguredScreen(caller)) {
            return true;
        }
        return false;
    }

    private static boolean isCustomMenu(@NotNull Screen screen) {
        for (String s : InventoryPause.MOD_CONFIG.modCompat.customScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCompatScreen(@NotNull Screen screen) {
        for (String s : InventoryPause.MOD_CONFIG.modCompat.compatScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
	}
}
