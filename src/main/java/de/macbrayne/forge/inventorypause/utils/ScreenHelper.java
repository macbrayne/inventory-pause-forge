package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen;

public class ScreenHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public static boolean isConfiguredScreen(Screen screen) {
        return screen != null && config.enabled && (isInventory(screen) || isFurnace(screen) || isCraftingTable(screen) || isShulkerBox(screen) || isCustomMenu(screen));
    }

    private static boolean isInventory(Screen screen) {
        return config.abilities.pauseInventory && screen instanceof InventoryScreen;
    }

    private static boolean isFurnace(Screen screen) {
        return config.abilities.pauseFurnace && screen instanceof AbstractFurnaceScreen;
    }

    private static boolean isCraftingTable(Screen screen) {
        return config.abilities.pauseCraftingTable && screen instanceof CraftingScreen;
    }

    private static boolean isShulkerBox(Screen screen) {
        return config.abilities.pauseShulkerBox && screen instanceof ShulkerBoxScreen;
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
