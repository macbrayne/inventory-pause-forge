package de.macbrayne.forge.inventorypause.compat;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.HopperScreen;
import net.minecraft.client.gui.screen.inventory.*;

public class VanillaCompat implements CustomCompat {
    @Override
    public void register() {
        // Abilities Screen top layer
        SCREEN_DICTIONARY.register(InventoryScreen.class, () -> config.abilities.pauseInventory);
        SCREEN_DICTIONARY.register(CreativeScreen.class, () -> config.abilities.pauseCreativeInventory);
        SCREEN_DICTIONARY.register(DeathScreen.class, () -> config.abilities.pauseDeath);
        SCREEN_DICTIONARY.register(AbstractFurnaceScreen.class, () -> config.abilities.pauseFurnace);
        SCREEN_DICTIONARY.register(CraftingScreen.class, () -> config.abilities.pauseCraftingTable);
        SCREEN_DICTIONARY.register(ShulkerBoxScreen.class, () -> config.abilities.pauseShulkerBox);
        SCREEN_DICTIONARY.register(ChestScreen.class, () -> config.abilities.pauseChest);

        // Additional GUIs
        SCREEN_DICTIONARY.register(AnvilScreen.class, () -> config.abilities.additionalGUIs.pauseAnvil);
        SCREEN_DICTIONARY.register(BeaconScreen.class, () -> config.abilities.additionalGUIs.pauseBeacon);
        SCREEN_DICTIONARY.register(DispenserScreen.class, () -> config.abilities.additionalGUIs.pauseDispenser);
        SCREEN_DICTIONARY.register(BrewingStandScreen.class, () -> config.abilities.additionalGUIs.pauseBrewingStand);
        SCREEN_DICTIONARY.register(HopperScreen.class, () -> config.abilities.additionalGUIs.pauseHopper);
        SCREEN_DICTIONARY.register(CartographyTableScreen.class, () -> config.abilities.additionalGUIs.pauseCartographyTable);
        SCREEN_DICTIONARY.register(StonecutterScreen.class, () -> config.abilities.additionalGUIs.pauseStonecutter);

        // World GUIs
        SCREEN_DICTIONARY.register(HorseInventoryScreen.class, () -> config.abilities.worldGUIs.pauseHorse);
        SCREEN_DICTIONARY.register(MerchantScreen.class, () -> config.abilities.worldGUIs.pauseMerchant);
    }

    @Override
    public boolean getConfigKey() {
        throw new IllegalStateException("Unreachable Code");
    }
}
