package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.utils.CompatTick;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ AbstractContainerScreen.class, DeathScreen.class})
public class ContainerScreenMixin {
    @Unique
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

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
