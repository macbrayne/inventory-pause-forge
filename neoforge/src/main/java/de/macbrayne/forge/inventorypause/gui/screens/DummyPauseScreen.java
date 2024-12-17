// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.network.chat.Component;

public class DummyPauseScreen extends GenericMessageScreen {
    public DummyPauseScreen() {
        super(Component.translatable("menu.inventorypause.pauseGameAnywhere.title"));
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBlurredBackground(pPartialTick);
        this.renderMenuBackground(pGuiGraphics);
    }
}
