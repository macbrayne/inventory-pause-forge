package de.macbrayne.forge.inventorypause.compat;

import net.minecraft.client.gui.screen.HopperScreen;
import net.minecraft.client.gui.screen.inventory.*;

public class VanillaCompat implements CustomCompat {
    @Override
    public void register() {
        // Abilities Screen top layer
        modDictionary.register(InventoryScreen.class, () -> config.abilities.pauseInventory);
        modDictionary.register(CreativeScreen.class, () -> config.abilities.pauseCreativeInventory);
        modDictionary.register(AbstractFurnaceScreen.class, () -> config.abilities.pauseFurnace);
        modDictionary.register(CraftingScreen.class, () -> config.abilities.pauseCraftingTable);
        modDictionary.register(ShulkerBoxScreen.class, () -> config.abilities.pauseShulkerBox);
        modDictionary.register(ChestScreen.class, () -> config.abilities.pauseChest);

        // Additional GUIs
        modDictionary.register(AnvilScreen.class, () -> config.abilities.additionalGUIs.pauseAnvil);
        modDictionary.register(BeaconScreen.class, () -> config.abilities.additionalGUIs.pauseBeacon);
        modDictionary.register(DispenserScreen.class, () -> config.abilities.additionalGUIs.pauseDispenser);
        modDictionary.register(BrewingStandScreen.class, () -> config.abilities.additionalGUIs.pauseBrewingStand);
        modDictionary.register(HopperScreen.class, () -> config.abilities.additionalGUIs.pauseHopper);
        modDictionary.register(CartographyTableScreen.class, () -> config.abilities.additionalGUIs.pauseCartographyTable);
        modDictionary.register(StonecutterScreen.class, () -> config.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        modDictionary.register(HorseInventoryScreen.class, () -> config.abilities.worldGUIs.pauseHorse);
        modDictionary.register(MerchantScreen.class, () -> config.abilities.worldGUIs.pauseMerchant);
    }

    @Override
    public boolean getConfigKey() {
        throw new IllegalStateException("Unreachable Code");
    }
}
