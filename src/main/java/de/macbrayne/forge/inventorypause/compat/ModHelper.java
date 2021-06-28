package de.macbrayne.forge.inventorypause.compat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModHelper {
    private static Class<?>[] modClasses = new Class[0];
    private static final Map<Class<?>, Function<Class<?>, Boolean>> configProviderMap = new HashMap<>();

    public static void register(Class<?> modClass, Function<Class<?>, Boolean> configProvider) {
        configProviderMap.put(modClass, configProvider);
        // Cache keySet to improve performance
        modClasses = configProviderMap.keySet().toArray(new Class[0]);
    }

    public static boolean handleScreen(Class<?> screenClass) {
        List<Class<?>> applicableClasses = getApplicableClasses(screenClass);
        if(applicableClasses.isEmpty()) {
            return false;
        }
        return configProviderMap.get(applicableClasses.get(0)).apply(screenClass);
    }

    private static List<Class<?>> getApplicableClasses(Class<?> screenClass) {
        return Arrays.stream(modClasses).filter((vanillaClass -> vanillaClass.isAssignableFrom(screenClass))).collect(Collectors.toList());
    }
}
