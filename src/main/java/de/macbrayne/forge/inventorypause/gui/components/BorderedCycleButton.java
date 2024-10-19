// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.TriStateSprites;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.util.Mth;

public class BorderedCycleButton extends CycleButton<PauseMode> {
    public BorderedCycleButton(CycleButton<PauseMode> copy) {
        super(copy.getX(), copy.getY(), copy.getWidth(), copy.getHeight(),
                copy.getMessage(), copy.name, copy.index, copy.getValue(), copy.values, copy.valueStringifier, copy.narrationProvider, copy.onValueChange, copy.tooltipSupplier, copy.displayOnlyValue);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        guiGraphics.blitSprite(TriStateSprites.DEFAULT.get(this.active, this.isHoveredOrFocused(), getValue()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        renderString(guiGraphics, Minecraft.getInstance().font, getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}