// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.compat;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class ScreenDictionary {
    private Class<?>[] cachedClasses = new Class[0];
    private final Map<Class<?>, BooleanSupplier> configProviderMap = new HashMap<>();
    private boolean dirty;

    private Class lastScreen = null;
    private boolean lastResult = false;

    public void register(@NotNull Class<?> aClass, @NotNull BooleanSupplier configProvider) {
        configProviderMap.put(aClass, configProvider);
        dirty = true;
    }

    public void register(Class<?> aClass, @NotNull BooleanSupplier configProvider, @NotNull BooleanSupplier customConfigProvider) {
        register(aClass, () -> configProvider.getAsBoolean() && customConfigProvider.getAsBoolean());
    }

    public boolean handleScreen(@NotNull Class<?> screenClass) {
        // Cache keySet to improve performance
        if(dirty || cachedClasses == null) {
            cachedClasses = configProviderMap.keySet().toArray(new Class[0]);
        }
        dirty = false;

        // Cache last screen & result to avoid the stream operation
        if(screenClass == lastScreen) {
            return lastResult;
        }

        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        lastScreen = screenClass;
        lastResult = registeredParentClass.filter(aClass -> configProviderMap.get(aClass).getAsBoolean()).isPresent();
        return lastResult;
    }

    public void setLastScreenDirty() {
        lastScreen = null;
    }

    private Optional<Class<?>> getRegisteredParentClass(@NotNull Class<?> screenClass) {
        return Arrays.stream(cachedClasses).filter((aClass -> aClass.isAssignableFrom(screenClass))).findFirst();
    }
}