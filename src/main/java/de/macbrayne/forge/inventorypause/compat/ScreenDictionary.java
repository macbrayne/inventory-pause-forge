// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.common.PauseMode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ScreenDictionary {
    private Class<?>[] cachedClasses = new Class[0];
    private final Map<Class<?>, Supplier<PauseMode>> configProviderMap = new HashMap<>();
    private boolean dirty;
    private Class<?> lastScreen = null;
    private PauseMode lastResult = PauseMode.OFF;

    public void register(@NotNull Class<?> aClass, @NotNull Supplier<PauseMode> configProvider) {
        configProviderMap.put(aClass, configProvider);
        dirty = true;
    }

    public PauseMode handleScreen(@NotNull Class<?> screenClass) {
        // Cache keySet to improve performance
        if(dirty || cachedClasses == null) {
            cachedClasses = configProviderMap.keySet().toArray(new Class[0]);
            dirty = false;
        }

        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        lastScreen = screenClass;
        lastResult = registeredParentClass.map(aClass -> configProviderMap.get(aClass).get()).orElse(PauseMode.OFF);
        return lastResult;
    }

    public void setLastScreenDirty() {
        lastScreen = null;
    }

    private Optional<Class<?>> getRegisteredParentClass(@NotNull Class<?> screenClass) {
        return Arrays.stream(cachedClasses).filter((aClass -> aClass.isAssignableFrom(screenClass))).findFirst();
    }
}