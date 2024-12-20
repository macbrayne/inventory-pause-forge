// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.mixin;

import de.macbrayne.inventorypause.Constants;
import de.macbrayne.inventorypause.events.CommonEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixinFabric {
    @Unique private static final Logger inventorypause$LOGGER = Constants.LOG;
    @Unique private boolean inventorypause$isSlowMotion = false;
    @Unique private float inventorypause$originalTickRate = 1f;
    @Shadow @Nullable public Screen screen;

    @Shadow
    public abstract boolean isSingleplayer();

    @Inject(method = "setScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", opcode = Opcodes.PUTFIELD, shift = At.Shift.BEFORE))
    private void setAndInitialisedScreen(Screen newScreen, CallbackInfo ci) {
        if (isSingleplayer()) {
            CommonEvents.onScreenChange(newScreen, screen, inventorypause$isSlowMotion, this::inventorypause$setSlowMotion, inventorypause$originalTickRate, this::inventorypause$setOriginalTickRate);
        }
    }

    @Unique
    private void inventorypause$setSlowMotion(boolean state) {
        inventorypause$isSlowMotion = state;
    }

    @Unique
    private void inventorypause$setOriginalTickRate(float tickRate) {
        inventorypause$originalTickRate = tickRate;
    }
}
