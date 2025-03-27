// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.mixin.qol;

import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.common.PauseMode;
import net.minecraft.client.gui.screens.DeathScreen;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.macbrayne.menupause.MenuPause.MOD_CONFIG;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Unique private static final Logger menupause$LOGGER = Constants.LOG;
    @Shadow private int delayTicker;

    @Inject(method = "init", at = @At(value = "RETURN"))
    private void forwardButtonDelay(CallbackInfo ci) {
        if(MOD_CONFIG.states.get("pauseDeath") == PauseMode.SLOWMO) {
            this.delayTicker += Math.max((int) Math.floor(20f - MOD_CONFIG.modCompat.slowmoTickSpeed - 1), 0);
            menupause$LOGGER.debug("Forwarding death screen button delay by {} ticks", this.delayTicker);
        }
    }
}
