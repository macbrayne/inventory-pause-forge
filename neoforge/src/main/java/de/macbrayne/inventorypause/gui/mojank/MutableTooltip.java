// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.gui.mojank;

import de.macbrayne.inventorypause.mixin.mojank.TooltipMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * See {@link TooltipMixin} for more information
 */
public interface MutableTooltip {
    void inventorypause$updateMessage(Minecraft minecraft, Component message);
}
