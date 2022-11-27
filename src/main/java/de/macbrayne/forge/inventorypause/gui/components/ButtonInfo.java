// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record ButtonInfo(ItemStack itemStack, Component tooltipComponent, Supplier<Boolean> stateSupplier, Consumer<Boolean> stateConsumer) {
}
