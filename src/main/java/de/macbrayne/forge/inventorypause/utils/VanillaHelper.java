package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.inventory.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class VanillaHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private static final Class[] vanillaClasses;
    private static Map<Class, Function<Class, Boolean>> configProviderMap = new HashMap<>();

    static {
        configProviderMap.put(InventoryScreen.class, (value) -> config.abilities.pauseInventory);
        configProviderMap.put(CreativeScreen.class, (value) -> config.abilities.pauseCreativeInventory);
        configProviderMap.put(AbstractFurnaceScreen.class, (value) -> config.abilities.pauseFurnace);
        configProviderMap.put(CraftingScreen.class, (value) -> config.abilities.pauseCraftingTable);
        configProviderMap.put(ShulkerBoxScreen.class, (value) -> config.abilities.pauseShulkerBox);
        vanillaClasses = configProviderMap.keySet().toArray(new Class[0]);
    }

    public static boolean handleScreen(Class vanillaClass) {
        if(Arrays.stream(vanillaClasses).noneMatch(vanillaClass::equals)) {
            return false;
        }
        return configProviderMap.get(vanillaClass).apply(vanillaClass);
    }

}
