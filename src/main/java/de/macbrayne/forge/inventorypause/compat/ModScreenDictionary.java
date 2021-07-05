package de.macbrayne.forge.inventorypause.compat;

import java.util.*;
import java.util.function.Function;

public class ModScreenDictionary {
    private Class<?>[] modClasses = new Class[0];
    private final Map<Class<?>, Function<Class<?>, Boolean>> configProviderMap = new HashMap<>();
    private boolean dirty;

    public void register(Class<?> modClass, Function<Class<?>, Boolean> configProvider) {
        configProviderMap.put(modClass, configProvider);
        dirty = true;
    }

    public boolean handleScreen(Class<?> screenClass) {
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

    private Optional<Class<?>> getRegisteredParentClass(Class<?> screenClass) {
        return Arrays.stream(modClasses).filter((modClass -> modClass.isAssignableFrom(screenClass))).findFirst();
    }
}
