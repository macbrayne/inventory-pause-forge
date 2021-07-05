package de.macbrayne.forge.inventorypause.compat;

import java.util.*;
import java.util.function.Function;

public class ModHelper {
    private static Class<?>[] modClasses = new Class[0];
    private static final Map<Class<?>, Function<Class<?>, Boolean>> configProviderMap = new HashMap<>();
    private static boolean dirty;

    public static void register(Class<?> modClass, Function<Class<?>, Boolean> configProvider) {
        configProviderMap.put(modClass, configProvider);
        dirty = true;
    }

    public static boolean handleScreen(Class<?> screenClass) {
        // Cache keySet to improve performance
        if(dirty || modClasses == null) {
            modClasses = configProviderMap.keySet().toArray(new Class[0]);
        }
        dirty = false;
        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        if(!registeredParentClass.isPresent()) {
            return false;
        }
        return configProviderMap.get(registeredParentClass.get()).apply(screenClass);
    }

    private static Optional<Class<?>> getRegisteredParentClass(Class<?> screenClass) {
        return Arrays.stream(modClasses).filter((modClass -> modClass.isAssignableFrom(screenClass))).findFirst();
    }
}
