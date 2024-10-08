// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.gui.screens.DummyPauseScreen;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress(JIIII)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z", ordinal = 0), cancellable = true)
    void injectKeyCheck(long pWindowPointer, int pKey, int pScanCode, int pAction, int pModifiers, CallbackInfo ci) {
        if (pKey == GLFW.GLFW_KEY_ESCAPE && Minecraft.getInstance().screen instanceof DummyPauseScreen) {
            Minecraft.getInstance().popGuiLayer();
            ci.cancel();
        }
    }
}