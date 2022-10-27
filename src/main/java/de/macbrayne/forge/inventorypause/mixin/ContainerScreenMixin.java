package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.utils.CompatTick;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.gui.screen.inventory.ContainerScreen.class)
public class ContainerScreenMixin {
    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (ScreenHelper.isCompatScreen((Screen) (Object) this) && CompatTick.timeUntilCompatTick == 1) {
            return;
        }
        if(ScreenHelper.isConfiguredScreen((Screen) (Object) this)) {
            cir.setReturnValue(true);
        }
    }
}
