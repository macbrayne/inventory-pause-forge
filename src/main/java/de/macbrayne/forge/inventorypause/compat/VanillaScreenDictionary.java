package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.inventory.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class VanillaScreenDictionary {
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private final Class<?>[] vanillaClasses;
    private final Map<Class<?>, BooleanSupplier> configProviderMap = new HashMap<>(14);

    public VanillaScreenDictionary() {
        // Abilities Screen top layer
        configProviderMap.put(InventoryScreen.class, () -> config.abilities.pauseInventory);
        configProviderMap.put(CreativeScreen.class, () -> config.abilities.pauseCreativeInventory);
        configProviderMap.put(AbstractFurnaceScreen.class, () -> config.abilities.pauseFurnace);
        configProviderMap.put(CraftingScreen.class, () -> config.abilities.pauseCraftingTable);
        configProviderMap.put(ShulkerBoxScreen.class, () -> config.abilities.pauseShulkerBox);
        configProviderMap.put(ChestScreen.class, () -> config.abilities.pauseChest);

        // Additional GUIs
        configProviderMap.put(AnvilScreen.class, () -> config.abilities.additionalGUIs.pauseAnvil);
        configProviderMap.put(BeaconScreen.class, () -> config.abilities.additionalGUIs.pauseBeacon);
        configProviderMap.put(DispenserScreen.class, () -> config.abilities.additionalGUIs.pauseDispenser);
        configProviderMap.put(BrewingStandScreen.class, () -> config.abilities.additionalGUIs.pauseBrewingStand);
        configProviderMap.put(CartographyTableScreen.class, () -> config.abilities.additionalGUIs.pauseCartographyTable);
        configProviderMap.put(StonecutterScreen.class, () -> config.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        configProviderMap.put(HorseInventoryScreen.class, () -> config.abilities.worldGUIs.pauseHorse);
        configProviderMap.put(MerchantScreen.class, () -> config.abilities.worldGUIs.pauseMerchant);

        // Cache keySet to improve performance
        vanillaClasses = configProviderMap.keySet().toArray(new Class[0]);

    }

    public boolean handleScreen(Class<?> screenClass) {
        Optional<Class<?>> registeredParentClass = getRegisteredParentClass(screenClass);
        if(!registeredParentClass.isPresent()) {
            return false;
        }

        return configProviderMap.get(registeredParentClass.get()).getAsBoolean();
    }

    private Optional<Class<?>> getRegisteredParentClass(Class<?> screenClass) {
        return Arrays.stream(vanillaClasses).filter((vanillaClass -> vanillaClass.isAssignableFrom(screenClass))).findFirst();
    }

}
