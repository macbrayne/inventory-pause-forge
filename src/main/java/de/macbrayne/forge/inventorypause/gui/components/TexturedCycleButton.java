// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.config.GuiEntry;
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

    public static TexturedCycleButton fromButtonInfo(int x, int y, int width, int height, GuiEntry info) {
        var states = InventoryPause.MOD_CONFIG.states;
        Component buttonInfoComponent = Component.translatable("menu.inventorypause.settings.tooltip." + info.configEntry());
        Component narration = Component.translatable("menu.inventorypause.settings.tooltip.pause", buttonInfoComponent);
        TriStateTooltip tooltip = TriStateTooltip.withState(Component.translatable("menu.inventorypause.settings.tooltip.ellipsis", buttonInfoComponent));
        return new TexturedCycleButton(CycleButton.builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.SLOWMO, PauseMode.ON)
                .withTooltip(tooltip::get)
                .withInitialValue(states.get(info))
                .create(x, y, width, height, narration, (button, value) -> states.put(info, value)), info.icon());
    }

    @Override
    public void renderString(@NotNull GuiGraphics guiGraphics, @NotNull Font font, int color) {
        GuiUtils.renderButtonItem(guiGraphics, icon, this.getX(), this.getY(), this.width);
    }
}