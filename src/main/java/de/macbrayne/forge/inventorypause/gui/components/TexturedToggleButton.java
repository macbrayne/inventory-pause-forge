// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.gui.GuiUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class TexturedToggleButton extends ToggleButton {
    private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager2.png");
    private final ItemStack icon;

    public TexturedToggleButton(int x, int y, int width, int height, Component narratedComponent, ButtonInfo info) {
        this(x, y, width, height, info.itemStack(), narratedComponent, GuiUtils.getTogglePress(info.stateSupplier(), info.stateConsumer()), Tooltip.create(info.tooltipComponent()), info.stateSupplier());
    }

    private TexturedToggleButton(int x, int y, int width, int height, ItemStack icon, Component narratedComponent, OnPress onPress, Tooltip onTooltip, Supplier<Boolean> stateSupplier) {
        super(x, y, width, height, narratedComponent, onPress, onTooltip, stateSupplier);
        this.icon = icon;
    }


    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        GuiUtils.renderButtonItem(icon, this.getX(), this.getY(), this.width);
    }
}
