// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class HoverButton extends Button {
    final Component hoverComponent;

    public HoverButton(Builder builder) {
        this(builder, builder.build().getMessage().copy().withStyle(ChatFormatting.RED));
    }

    public HoverButton(Builder builder, Component hoverComponent) {
        super(builder);
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
