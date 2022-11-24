// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScreenHelper {
    private static final ModConfig config;

    static {
        ModConfig tmpConfig;
        try {
            tmpConfig = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        } catch (RuntimeException e) {
            tmpConfig = new ModConfig();
            AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        }
        config = tmpConfig;
    }

    private static final ScreenDictionary modDictionary = Reference.getScreenDictionary();

    public static boolean isConfiguredScreen(@Nullable Screen screen) {
        return screen != null && config.enabled &&
                (modDictionary.handleScreen(screen.getClass()) || isCustomMenu(screen) || isCompatScreen(screen));
    }

    private static boolean isCustomMenu(@NotNull Screen screen) {
        for (String s : config.modCompat.customScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

	public static boolean isCompatScreen(@NotNull Screen screen) {
        for (String s : config.modCompat.compatScreens) {
            if(screen.getClass().getName().equals(s)) {
                return true;
            }
        }
        return false;
	}
}
