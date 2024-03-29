// SPDX-License-Identifier: EUPL-1.2 OR MIT

package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.events.ForgeEventBus;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ AbstractContainerScreen.class, DeathScreen.class, GameModeSwitcherScreen.class})
public class ContainerScreenMixin {
    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (ScreenHelper.isCompatScreen((Screen) (Object) this) && ForgeEventBus.timeUntilCompatTick == 1) {
            return;
        }
        if(ScreenHelper.isConfiguredScreen((Screen) (Object) this)) {
            cir.setReturnValue(true);
        }
    }
}
