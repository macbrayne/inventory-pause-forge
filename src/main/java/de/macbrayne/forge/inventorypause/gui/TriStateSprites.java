// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.resources.ResourceLocation;

public record TriStateSprites(ResourceLocation on, ResourceLocation slowmo, ResourceLocation off, ResourceLocation disabled, ResourceLocation enabledFocused) {
    public static TriStateSprites DEFAULT = new TriStateSprites(new ResourceLocation(InventoryPause.MOD_ID, "widget/button_green_border"),
            new ResourceLocation(InventoryPause.MOD_ID, "widget/button_yellow_border"),
            new ResourceLocation(InventoryPause.MOD_ID, "widget/button_red_border"),
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
