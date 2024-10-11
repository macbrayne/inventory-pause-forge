// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.GuiUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TexturedCycleButton extends BorderedCycleButton {
    private final ItemStack icon;

    public TexturedCycleButton(CycleButton<PauseMode> copy, ItemStack icon) {
        super(copy);
        this.icon = icon;
    }

    public static TexturedCycleButton fromButtonInfo(int x, int y, int width, int height, Component narratedComponent, ButtonInfo info) {
        return new TexturedCycleButton(CycleButton.<PauseMode>builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.SLOWMO, PauseMode.ON)
                .withTooltip(value -> TriStateTooltip.withState(info.tooltipComponent()).get(value))
                .withInitialValue(info.stateSupplier().get())
                .create(x, y, width, height, narratedComponent, (button, value) -> info.stateConsumer().accept(value)), info.itemStack());
    }

    @Override
    public void renderString(@NotNull GuiGraphics guiGraphics, @NotNull Font font, int color) {
        GuiUtils.renderButtonItem(guiGraphics, icon, this.getX(), this.getY(), this.width);
    }
}
