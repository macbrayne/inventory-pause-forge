// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.api;

import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;

public class Api {
    private static final HashMap<String, String[]> coveredClasses = new HashMap<>();
    private static final Object2BooleanArrayMap<String> modLoaded = new Object2BooleanArrayMap<>();

    static {
        coveredClasses.put("citadel", new String[] { "com.github.alexthe666.citadel.client.gui.GuiBasicBook" });
        modLoaded.put("citadel", ModList.get().isLoaded("citadel"));
    }

    public static boolean canScreenBePausedByMod(Screen screen) {
        for(String mod : coveredClasses.keySet()) {
            if(modLoaded.getBoolean(mod)) {
                for(String clazz : coveredClasses.get(mod)) {
                    try {
                        if(Class.forName(clazz).isAssignableFrom(screen.getClass())) {
                            return true;
                        }
                    } catch (ClassNotFoundException ignored) {}
                }
            }
        }
        return screen instanceof AbstractContainerScreen ||
                screen instanceof DeathScreen ||
                screen instanceof GameModeSwitcherScreen;
    }
}
