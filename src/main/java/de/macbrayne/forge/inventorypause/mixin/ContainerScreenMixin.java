package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.utils.ModConfig;
import de.macbrayne.forge.inventorypause.utils.ScreenHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.gui.screen.inventory.ContainerScreen.class)
public class ContainerScreenMixin {
    @Unique
    private static final Logger LOGGER = LogManager.getLogger("inventorypause");
    @Unique
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable cir) {
        if(ScreenHelper.isConfiguredScreen((Screen) (Object) this)) {
            cir.setReturnValue(true);
            cir.cancel();
        } else if(config.debug) {
            LOGGER.info(this.getClass().getName());
        }
    }
}
