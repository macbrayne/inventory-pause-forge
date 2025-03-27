// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui.mojank;

import de.macbrayne.menupause.mixin.mojank.TooltipMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * See {@link TooltipMixin} for more information
 */
public interface MutableTooltip {
    void menupause$updateMessage(Minecraft minecraft, Component message);
}
