// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import net.minecraft.resources.ResourceLocation;

public record TriStateSprites(ResourceLocation enabled, ResourceLocation disabled, ResourceLocation enabledFocused, ResourceLocation disabledFocused) {
    public TriStateSprites(ResourceLocation enabled, ResourceLocation disabled) {
        this(enabled, enabled, disabled, disabled);
    }

    public TriStateSprites(ResourceLocation enabled, ResourceLocation disabled, ResourceLocation enabledFocused) {
        this(enabled, disabled, enabledFocused, disabled);
    }

    public ResourceLocation get(boolean enabled, boolean focused) {
        if (enabled) {
            return focused ? this.enabledFocused : this.enabled;
        } else {
            return focused ? this.disabledFocused : this.disabled;
        }
    }
}