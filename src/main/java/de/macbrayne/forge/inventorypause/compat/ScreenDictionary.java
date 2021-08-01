package de.macbrayne.forge.inventorypause.compat;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class ScreenDictionary {
    private Class<?>[] cachedClasses = new Class[0];
    private final Map<Class<?>, BooleanSupplier> configProviderMap = new HashMap<>();
    private boolean dirty;

    public void register(@Nonnull Class<?> aClass, @Nonnull BooleanSupplier configProvider) {
        configProviderMap.put(aClass, configProvider);
        dirty = true;
    }

    public void register(Class<?> aClass, @Nonnull BooleanSupplier configProvider, @Nonnull BooleanSupplier customConfigProvider) {
        register(aClass, () -> configProvider.getAsBoolean() && customConfigProvider.getAsBoolean());
    }

    public boolean handleScreen(@Nonnull Class<?> screenClass) {
        // Cache keySet to improve performance
        if(dirty || cachedClasses == null) {
            cachedClasses = configProviderMap.keySet().toArray(new Class[0]);
        }

        dirty = false;
        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        return registeredParentClass.filter(aClass -> configProviderMap.get(aClass).getAsBoolean()).isPresent();
    }

    private Optional<Class<?>> getRegisteredParentClass(@Nonnull Class<?> screenClass) {
        return Arrays.stream(cachedClasses).filter((aClass -> aClass.isAssignableFrom(screenClass))).findFirst();
    }
}
