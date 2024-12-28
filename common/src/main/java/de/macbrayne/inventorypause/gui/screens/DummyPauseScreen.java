// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.gui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DummyPauseScreen extends GenericMessageScreen {
    public final Screen oldScreen;

    public DummyPauseScreen(Screen oldScreen) {
        super(Component.translatable("menu.inventorypause.pauseGameAnywhere.title"));
        this.oldScreen = oldScreen;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        if (oldScreen != null) {
            this.oldScreen.renderWithTooltip(guiGraphics, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTick);
        }
        guiGraphics.pose().popPose();
        this.renderBlurredBackground(partialTick);
        this.renderMenuBackground(guiGraphics);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        if (oldScreen != null) {
            oldScreen.resize(minecraft, width, height);
        }
        super.resize(minecraft, width, height);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_ESCAPE) {
            this.minecraft.setScreen(oldScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
