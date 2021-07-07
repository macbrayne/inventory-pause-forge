package de.macbrayne.forge.inventorypause.compat;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class ModScreenDictionary {
    private Class<?>[] modClasses = new Class[0];
    private final Map<Class<?>, BooleanSupplier> configProviderMap = new HashMap<>();
    private boolean dirty;

    public void register(@Nonnull Class<?> modClass, @Nonnull BooleanSupplier configProvider) {
        configProviderMap.put(modClass, configProvider);
        dirty = true;
    }

    public boolean handleScreen(@Nonnull Class<?> screenClass) {
        // Cache keySet to improve performance
        if(dirty || modClasses == null) {
            modClasses = configProviderMap.keySet().toArray(new Class[0]);
        }

        dirty = false;
        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        return registeredParentClass.filter(aClass -> configProviderMap.get(aClass).getAsBoolean()).isPresent();
    }

    private Optional<Class<?>> getRegisteredParentClass(@Nonnull Class<?> screenClass) {
        return Arrays.stream(modClasses).filter((modClass -> modClass.isAssignableFrom(screenClass))).findFirst();
    }
}
