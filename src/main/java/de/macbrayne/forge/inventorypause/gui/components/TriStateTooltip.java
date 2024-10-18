// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public record TriStateTooltip(Tooltip tooltipOn, Tooltip tooltipSlowmo, Tooltip tooltipOff) {
    static final Component TEXT_YES = CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.DARK_GREEN);
    static final Component TEXT_NO = CommonComponents.OPTION_OFF.plainCopy().withStyle(ChatFormatting.RED);
    static final Component TEXT_SLOWMO = Component.translatable("menu.inventorypause.slowmo").withStyle(ChatFormatting.YELLOW);

    public static TriStateTooltip withState(Component tooltipComponent) {
        return new TriStateTooltip(appendTooltipTo(tooltipComponent, PauseMode.ON),
                appendTooltipTo(tooltipComponent, PauseMode.SLOWMO),
                appendTooltipTo(tooltipComponent, PauseMode.OFF));
    }

    public static Tooltip appendTooltipTo(Component text, PauseMode mode) {
        String key = "menu.inventorypause.currentState." + mode.getSerialisation();
        Component addendum = switch (mode) {
            case OFF -> Component.translatable(key, TEXT_NO);
            case SLOWMO -> Component.translatable(key, TEXT_SLOWMO);
            case ON -> Component.translatable(key, TEXT_YES);
        };
        if (text.getString().isEmpty()) {
            return Tooltip.create(text.plainCopy().append(addendum), addendum);
        }
        return Tooltip.create(text.plainCopy().append("\n\n").append(addendum), addendum);
    }

    public Tooltip get(PauseMode mode) {
        return switch (mode) {
            case ON -> tooltipOn;
            case SLOWMO -> tooltipSlowmo;
            case OFF -> tooltipOff;
        };
    }
}
