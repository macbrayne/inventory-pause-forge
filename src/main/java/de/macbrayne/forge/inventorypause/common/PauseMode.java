// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public enum PauseMode {
    OFF("false", CommonComponents.OPTION_OFF.plainCopy().withStyle(ChatFormatting.RED)),
    SLOWMO("slowmo", Component.translatable("menu.inventorypause.slowmo").withStyle(ChatFormatting.YELLOW)),
    ON("true", CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.DARK_GREEN));

    private final String serialisation;
    private final Component displayName;

    PauseMode(String serialisation, Component displayName) {
        this.serialisation = serialisation;
        this.displayName = displayName;
    }

    public String getSerialisation() {
        return serialisation;
    }

    public static PauseMode fromBoolean(boolean value) {
        return value ? ON : OFF;
    }

    public Component getDisplayName() {
        return displayName;
    }
}