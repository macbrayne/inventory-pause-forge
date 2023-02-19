// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.*;

public class VanillaCompat {
    public final ScreenDictionary dict = InventoryPause.getScreenDictionary();

    public void register() {
        // Abilities Screen top layer
        dict.register(InventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseInventory);
        dict.register(CreativeModeInventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory);
        dict.register(DeathScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseDeath);
        dict.register(GameModeSwitcherScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseGameModeSwitcher);
        dict.register(AbstractFurnaceScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseFurnace);
        dict.register(CraftingScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseCraftingTable);
        dict.register(ShulkerBoxScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseShulkerBox);
        dict.register(ContainerScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.pauseChest);

        // Additional GUIs
        dict.register(AnvilScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseAnvil);
        dict.register(BeaconScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseBeacon);
        dict.register(DispenserScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseDispenser);
        dict.register(BrewingStandScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseBrewingStand);
        dict.register(HopperScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseHopper);
        dict.register(CartographyTableScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseCartographyTable);
        dict.register(StonecutterScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        dict.register(HorseInventoryScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.worldGUIs.pauseHorse);
        dict.register(MerchantScreen.class, () -> InventoryPause.MOD_CONFIG.abilities.worldGUIs.pauseMerchant);
    }
}
