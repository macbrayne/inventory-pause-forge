// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.GuiUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TexturedToggleButton extends ToggleButton {
    private final ItemStack icon;

    public TexturedToggleButton(int x, int y, int width, int height, Component narratedComponent, ButtonInfo info) {
        this(x, y, width, height, info.itemStack(), narratedComponent, GuiUtils.getTogglePress(info.stateSupplier(), info.stateConsumer()), TriStateTooltip.withState(info.tooltipComponent()), info.stateSupplier());
    }

    private TexturedToggleButton(int x, int y, int width, int height, ItemStack icon, Component narratedComponent, OnPress onPress, TriStateTooltip onTooltip, Supplier<PauseMode> stateSupplier) {
        super(x, y, width, height, narratedComponent, onPress, onTooltip, stateSupplier);
        this.icon = icon;
    }


    @Override
    public void renderString(@NotNull GuiGraphics guiGraphics, @NotNull Font font, int color) {
        GuiUtils.renderButtonItem(guiGraphics, icon, this.getX(), this.getY(), this.width);
    }
}
