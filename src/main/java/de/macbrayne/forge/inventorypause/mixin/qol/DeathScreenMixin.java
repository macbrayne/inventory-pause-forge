// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin.qol;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.client.gui.screens.DeathScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Unique private static final Logger inventorypause$LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    @Shadow private int delayTicker;

    @Inject(method = "init", at = @At(value = "RETURN"))
    private void yourHandlerMethod(CallbackInfo ci) {
        if(MOD_CONFIG.isEnabled() && MOD_CONFIG.abilities.pauseDeath == PauseMode.SLOWMO) {
            this.delayTicker += Math.max((int) Math.floor(20f - 20f / MOD_CONFIG.modCompat.timeBetweenCompatTicks - 1), 0);
            inventorypause$LOGGER.debug("Forwarding death screen button delay by {} ticks", this.delayTicker);
        }
    }
}
