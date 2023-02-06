// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Inject(method = "tickServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"), cancellable = true)
    public void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        if(InventoryPause.MOD_CONFIG.disableSaving) {
            ci.cancel();
        }
    }
}
