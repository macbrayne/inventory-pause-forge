// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.datagen;

import de.macbrayne.menupause.common.PauseMode;
import de.macbrayne.menupause.config.GuiEntries;
import de.macbrayne.menupause.config.GuiEntry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MenuPauseDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GuiEntriesData::new);
    }

    public static class GuiEntriesData extends GuiEntriesDataProvider {
        protected GuiEntriesData(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(dataOutput, registriesFuture);
        }

        @Override
        public void accept(List<GuiEntry<?>> guiEntries) {
            guiEntries.add(new GuiEntry.Text("pauseInventory", "inventory", PauseMode.ON, InventoryScreen.class));
            guiEntries.add(new GuiEntry.Text("pauseCreativeInventory", "creativeInventory", PauseMode.ON, CreativeModeInventoryScreen.class));
            guiEntries.add(new GuiEntry.Text("pauseDeath", "death", PauseMode.OFF, DeathScreen.class));
            guiEntries.add(new GuiEntry.Text("pauseGameModeSwitcher", "gameModeSwitcher", PauseMode.OFF, GameModeSwitcherScreen.class));

            guiEntries.add(new GuiEntry.Icon("pauseCraftingTable", new ItemStack(Items.CRAFTING_TABLE), PauseMode.OFF, CraftingScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseFurnace", new ItemStack(Items.FURNACE), PauseMode.OFF, FurnaceScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseShulkerBox", new ItemStack(Items.SHULKER_BOX), PauseMode.OFF, ShulkerBoxScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseChest", new ItemStack(Items.CHEST), PauseMode.OFF, ContainerScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseAnvil", new ItemStack(Items.ANVIL), PauseMode.OFF, AnvilScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseBeacon", new ItemStack(Items.BEACON), PauseMode.OFF, BeaconScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseDispenser", new ItemStack(Items.DISPENSER), PauseMode.OFF, DispenserScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseBrewingStand", new ItemStack(Items.BREWING_STAND), PauseMode.OFF, BrewingStandScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseHopper", new ItemStack(Items.HOPPER), PauseMode.OFF, HopperScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseCartographyTable", new ItemStack(Items.CARTOGRAPHY_TABLE), PauseMode.OFF, CartographyTableScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseStonecutter", new ItemStack(Items.STONECUTTER), PauseMode.OFF, StonecutterScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseGrindstone", new ItemStack(Items.GRINDSTONE), PauseMode.OFF, GrindstoneScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseCrafter", new ItemStack(Items.CRAFTER), PauseMode.OFF, CrafterScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseSignEdit", new ItemStack(Items.OAK_SIGN), PauseMode.OFF, AbstractSignEditScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseSmithing", new ItemStack(Items.SMITHING_TABLE), PauseMode.OFF, SmithingScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseLectern", new ItemStack(Items.LECTERN), PauseMode.OFF, LecternScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseLoom", new ItemStack(Items.LOOM), PauseMode.OFF, LoomScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseEnchantmentScreen", new ItemStack(Items.ENCHANTING_TABLE), PauseMode.OFF, EnchantmentScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseHorse", new ItemStack(Items.HORSE_SPAWN_EGG), PauseMode.OFF, HorseInventoryScreen.class));
            guiEntries.add(new GuiEntry.Icon("pauseMerchant", new ItemStack(Items.VILLAGER_SPAWN_EGG), PauseMode.OFF, MerchantScreen.class));
        }
    }
}