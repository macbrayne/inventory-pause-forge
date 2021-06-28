package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModHelper {
    private static Class<?>[] modClasses = new Class[0];
    private static final Map<Class<?>, Function<Class<?>, Boolean>> configProviderMap = new HashMap<>();

    public static void register(Class<?> modClass, Function<Class<?>, Boolean> configProvider) {
        configProviderMap.put(modClass, configProvider);
        // Cache keySet to improve performance
        modClasses = configProviderMap.keySet().toArray(new Class[0]);
    }

    public static boolean handleScreen(Class<?> screenClass) {
        if(Arrays.stream(modClasses).noneMatch(screenClass::equals)) {
            return false;
        }
        return configProviderMap.get(screenClass).apply(screenClass);
    }
}
