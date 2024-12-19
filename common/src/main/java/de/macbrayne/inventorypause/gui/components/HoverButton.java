// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.gui.components;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class HoverButton extends Button {
    final Component hoverComponent;

    public HoverButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration narration) {
        this(x, y, width, height, message, message.copy().withStyle(ChatFormatting.RED), onPress, narration);
    }

    public HoverButton(int x, int y, int width, int height, Component message, Component hoverComponent, OnPress onPress, CreateNarration narration) {
        super(x, y, width, height, message, onPress, narration);
        this.hoverComponent = hoverComponent;
    }

    @Override
    public @NotNull Component getMessage() {
        if (isHoveredOrFocused()) {
            return hoverComponent;
        }
        return super.getMessage();
    }
}
