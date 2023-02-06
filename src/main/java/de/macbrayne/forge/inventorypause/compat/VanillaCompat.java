// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.*;

public class VanillaCompat implements CustomCompat {
    @Override
    public void register() {
        // Abilities Screen top layer
        SCREEN_DICTIONARY.register(InventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseInventory);
        SCREEN_DICTIONARY.register(CreativeModeInventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory);
        SCREEN_DICTIONARY.register(DeathScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseDeath);
        SCREEN_DICTIONARY.register(GameModeSwitcherScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseGameModeSwitcher);
        SCREEN_DICTIONARY.register(AbstractFurnaceScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseFurnace);
        SCREEN_DICTIONARY.register(CraftingScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseCraftingTable);
        SCREEN_DICTIONARY.register(ShulkerBoxScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseShulkerBox);
        SCREEN_DICTIONARY.register(ContainerScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseChest);

        // Additional GUIs
        SCREEN_DICTIONARY.register(AnvilScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseAnvil);
        SCREEN_DICTIONARY.register(BeaconScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseBeacon);
        SCREEN_DICTIONARY.register(DispenserScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseDispenser);
        SCREEN_DICTIONARY.register(BrewingStandScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseBrewingStand);
        SCREEN_DICTIONARY.register(HopperScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseHopper);
        SCREEN_DICTIONARY.register(CartographyTableScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseCartographyTable);
        SCREEN_DICTIONARY.register(StonecutterScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        SCREEN_DICTIONARY.register(HorseInventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.worldGUIs.pauseHorse);
        SCREEN_DICTIONARY.register(MerchantScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.worldGUIs.pauseMerchant);
    }

    @Override
    public boolean getConfigKey() {
        throw new IllegalStateException("Unreachable Code");
    }
}
