// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.mojank;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * See {@link de.macbrayne.forge.inventorypause.mixin.mojank.TooltipMixin} for more information
 */
public interface MutableTooltip {
    public void inventorypause$updateMessage(Minecraft minecraft, Component message);
}
