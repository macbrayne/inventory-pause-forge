// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin.compat;

import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.github.alexthe666.citadel.client.gui.GuiBasicBook")
public class CitadelCompatMixin extends Screen {
    protected CitadelCompatMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "Lnet/minecraft/client/gui/screens/Screen;isPauseScreen()Z", at = @At("HEAD"), cancellable = true)
    public void inventoryPause$pauseScreen(CallbackInfoReturnable<Boolean> cir) {
        ScreenHelper.isPauseScreen(this, cir);
    }
}
