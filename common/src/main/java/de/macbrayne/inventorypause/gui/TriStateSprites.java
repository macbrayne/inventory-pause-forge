// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.gui;

import de.macbrayne.inventorypause.Constants;
import de.macbrayne.inventorypause.common.PauseMode;
import net.minecraft.resources.ResourceLocation;

public record TriStateSprites(ResourceLocation on, ResourceLocation slowmo, ResourceLocation off,
                              ResourceLocation disabled, ResourceLocation enabledFocused) {
    public static final TriStateSprites DEFAULT = new TriStateSprites(new ResourceLocation(Constants.MOD_ID, "widget/button_green_border"),
            new ResourceLocation(Constants.MOD_ID, "widget/button_yellow_border"),
            new ResourceLocation(Constants.MOD_ID, "widget/button_red_border"),
            new ResourceLocation("widget/button_disabled"),
            new ResourceLocation("widget/button_highlighted"));

    public ResourceLocation get(boolean enabled, boolean focused, PauseMode mode) {
        if (enabled) {
            if (focused) {
                return this.enabledFocused;
            }
            return switch (mode) {
                case ON -> this.on;
                case SLOWMO -> this.slowmo;
                case OFF -> this.off;
            };
        }
        return this.disabled;
    }
}
