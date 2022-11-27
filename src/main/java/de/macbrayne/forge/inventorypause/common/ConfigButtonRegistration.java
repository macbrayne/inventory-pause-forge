// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;

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
            ModConfig.Abilities.AdditionalGUIs additionalGUIs = abilities.additionalGUIs;
            list.add(buildInfo(Items.ANVIL, "additionalGUIs.pauseAnvil", () -> additionalGUIs.pauseAnvil, t -> additionalGUIs.pauseAnvil = t));
            list.add(buildInfo(Items.BEACON, "additionalGUIs.pauseBeacon", () -> additionalGUIs.pauseBeacon, t -> additionalGUIs.pauseBeacon = t));
            list.add(buildInfo(Items.DISPENSER, "additionalGUIs.pauseDispenser", () -> additionalGUIs.pauseDispenser, t -> additionalGUIs.pauseDispenser = t));
            list.add(buildInfo(Items.BREWING_STAND, "additionalGUIs.pauseBrewingStand", () -> additionalGUIs.pauseBrewingStand, t -> additionalGUIs.pauseBrewingStand = t));
            list.add(buildInfo(Items.HOPPER, "additionalGUIs.pauseHopper", () -> additionalGUIs.pauseHopper, t -> additionalGUIs.pauseHopper = t));
            list.add(buildInfo(Items.CARTOGRAPHY_TABLE, "additionalGUIs.pauseCartographyTable", () -> additionalGUIs.pauseCartographyTable, t -> additionalGUIs.pauseCartographyTable = t));
            list.add(buildInfo(Items.STONECUTTER, "additionalGUIs.pauseStonecutter", () -> additionalGUIs.pauseStonecutter, t -> additionalGUIs.pauseStonecutter = t));
        }
        {
            ModConfig.Abilities.WorldGUIs worldGUIs = abilities.worldGUIs;
            list.add(buildInfo(Items.HORSE_SPAWN_EGG, "worldGUIs.pauseHorse", () -> worldGUIs.pauseHorse, t -> worldGUIs.pauseHorse = t));
            list.add(buildInfo(Items.VILLAGER_SPAWN_EGG, "worldGUIs.pauseMerchant", () -> worldGUIs.pauseMerchant, t -> worldGUIs.pauseMerchant = t));
        }
        return list;
    }

    private ButtonInfo buildInfo(Item item, String tooltip, Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        return new ButtonInfo(new ItemStack(item), Component.translatable("text.autoconfig.inventorypause.option.abilities." + tooltip), supplier, consumer);
    }
}
