// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Inject(method = "tickServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"), cancellable = true)
    public void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        if(config.disableSaving) {
            ci.cancel();
        }
    }
}
