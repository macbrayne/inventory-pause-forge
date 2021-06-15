package de.macbrayne.forge.inventorypause.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.inventorypause.utils.ScreenHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.gui.screen.inventory.ContainerScreen.class)
public class ContainerScreenMixin {

    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable cir) {
        if(ScreenHelper.isConfiguredScreen(this)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
