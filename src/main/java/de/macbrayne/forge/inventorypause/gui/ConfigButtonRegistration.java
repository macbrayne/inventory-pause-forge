// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigButtonRegistration {

    public List<ButtonInfo> run(ModConfig config) {
        List<ButtonInfo> list = new ArrayList<>();
        ModConfig.Abilities abilities = config.abilities;
        {
            list.add(buildInfo(Items.CRAFTING_TABLE, "pauseCraftingTable", () -> abilities.pauseCraftingTable, t -> abilities.pauseCraftingTable = t));
            list.add(buildInfo(Items.FURNACE, "pauseFurnace", () -> abilities.pauseFurnace, t -> abilities.pauseFurnace = t));
            list.add(buildInfo(Items.SHULKER_BOX, "pauseShulkerBox", () -> abilities.pauseShulkerBox, t -> abilities.pauseShulkerBox = t));
            list.add(buildInfo(Items.CHEST, "pauseChest", () -> abilities.pauseChest, t -> abilities.pauseChest = t));
        }
        {
            list.add(buildInfo(Items.ANVIL, "pauseAnvil", () -> abilities.pauseAnvil, t -> abilities.pauseAnvil = t));
            list.add(buildInfo(Items.BEACON, "pauseBeacon", () -> abilities.pauseBeacon, t -> abilities.pauseBeacon = t));
            list.add(buildInfo(Items.DISPENSER, "pauseDispenser", () -> abilities.pauseDispenser, t -> abilities.pauseDispenser = t));
            list.add(buildInfo(Items.BREWING_STAND, "pauseBrewingStand", () -> abilities.pauseBrewingStand, t -> abilities.pauseBrewingStand = t));
            list.add(buildInfo(Items.HOPPER, "pauseHopper", () -> abilities.pauseHopper, t -> abilities.pauseHopper = t));
            list.add(buildInfo(Items.CARTOGRAPHY_TABLE, "pauseCartographyTable", () -> abilities.pauseCartographyTable, t -> abilities.pauseCartographyTable = t));
            list.add(buildInfo(Items.STONECUTTER, "pauseStonecutter", () -> abilities.pauseStonecutter, t -> abilities.pauseStonecutter = t));
        }
        {
            list.add(buildInfo(Items.HORSE_SPAWN_EGG, "pauseHorse", () -> abilities.pauseHorse, t -> abilities.pauseHorse = t));
            list.add(buildInfo(Items.VILLAGER_SPAWN_EGG, "pauseMerchant", () -> abilities.pauseMerchant, t -> abilities.pauseMerchant = t));
        }
        return list;
    }

    private ButtonInfo buildInfo(Item item, String tooltip, Supplier<PauseMode> supplier, Consumer<PauseMode> consumer) {
        return new ButtonInfo(new ItemStack(item), Component.translatable("menu.inventorypause.settings.tooltip." + tooltip), supplier, consumer);
    }
}
