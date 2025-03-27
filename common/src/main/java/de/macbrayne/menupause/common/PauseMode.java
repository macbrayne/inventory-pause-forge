// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.common;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PauseMode implements StringRepresentable {
    OFF("false", CommonComponents.OPTION_OFF.plainCopy().withStyle(ChatFormatting.RED)),
    SLOWMO("slowmo", Component.translatable("menu.inventorypause.slowmo").withStyle(ChatFormatting.YELLOW)),
    ON("true", CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.DARK_GREEN));

    public static final Codec<PauseMode> CODEC = StringRepresentable.fromEnum(PauseMode::values);
    private final String serialisation;
    private final Component displayName;

    PauseMode(String serialisation, Component displayName) {
        this.serialisation = serialisation;
        this.displayName = displayName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serialisation;
    }

    public static PauseMode fromBoolean(boolean value) {
        return value ? ON : OFF;
    }

    public Component getDisplayName() {
        return displayName;
    }
}