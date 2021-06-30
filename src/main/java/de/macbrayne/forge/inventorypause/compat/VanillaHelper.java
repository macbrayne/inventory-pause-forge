package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.inventory.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class VanillaHelper {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private static final Class<?>[] vanillaClasses;
    private static final Map<Class<?>, Function<Class<?>, Boolean>> configProviderMap = new HashMap<>(14);

    static {
        // Abilities Screen top layer
        configProviderMap.put(InventoryScreen.class, (value) -> config.abilities.pauseInventory);
        configProviderMap.put(CreativeScreen.class, (value) -> config.abilities.pauseCreativeInventory);
        configProviderMap.put(AbstractFurnaceScreen.class, (value) -> config.abilities.pauseFurnace);
        configProviderMap.put(CraftingScreen.class, (value) -> config.abilities.pauseCraftingTable);
        configProviderMap.put(ShulkerBoxScreen.class, (value) -> config.abilities.pauseShulkerBox);
        configProviderMap.put(ChestScreen.class, (value) -> config.abilities.pauseChest);

        // Additional GUIs
        configProviderMap.put(AnvilScreen.class, (value) -> config.abilities.additionalGUIs.pauseAnvil);
        configProviderMap.put(BeaconScreen.class, (value) -> config.abilities.additionalGUIs.pauseBeacon);
        configProviderMap.put(DispenserScreen.class, (value) -> config.abilities.additionalGUIs.pauseDispenser);
        configProviderMap.put(BrewingStandScreen.class, (value) -> config.abilities.additionalGUIs.pauseBrewingStand);
        configProviderMap.put(CartographyTableScreen.class, (value) -> config.abilities.additionalGUIs.pauseCartographyTable);
        configProviderMap.put(StonecutterScreen.class, (value) -> config.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        configProviderMap.put(HorseInventoryScreen.class, (value) -> config.abilities.worldGUIs.pauseHorse);
        configProviderMap.put(MerchantScreen.class, (value) -> config.abilities.worldGUIs.pauseMerchant);

        // Cache keySet to improve performance
        vanillaClasses = configProviderMap.keySet().toArray(new Class[0]);
    }

    public static boolean handleScreen(Class<?> screenClass) {
        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        if(!registeredParentClass.isPresent()) {
            return false;
        }
        return configProviderMap.get(registeredParentClass.get()).apply(screenClass);
    }

    private static Optional<Class<?>> getRegisteredParentClass(Class<?> screenClass) {
        return Arrays.stream(vanillaClasses).filter((vanillaClass -> vanillaClass.isAssignableFrom(screenClass))).findFirst();
    }

}
