// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin.mojank;

import de.macbrayne.forge.inventorypause.gui.mojank.MutableTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Mixin to make Tooltips mutable
 * Pre 1.20.3 tooltips didn't hold any state making them safe to create on demand. With 1.20.3 this changed and tooltips
 * now hold logic for delayed tooltip display. This mixin makes tooltips mutable allowing you to update the message
 * while retaining the rest of the state.
 */
@Mixin(Tooltip.class)
public abstract class TooltipMixin implements MutableTooltip {
    @Shadow @Nullable private List<FormattedCharSequence> cachedTooltip;

    public void inventorypause$updateMessage(Minecraft minecraft, Component message) {
        cachedTooltip = Tooltip.splitTooltip(minecraft, message);
    }
}
