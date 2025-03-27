// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.events.CommonEvents;
import de.macbrayne.menupause.gui.TickrateController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixinNeo implements TickrateController {
    @Unique private static final Logger menupause$LOGGER = Constants.LOG;
    @Unique private boolean menupause$isSlowMotion = false;
    @Unique private float menupause$originalTickRate = 1f;

    @Shadow
    public abstract boolean isSingleplayer();

    @Inject(method = "setScreen", at = @At(value = "RETURN"))
    private void setAndInitialisedScreen(Screen newScreen, CallbackInfo ci, @Local(ordinal = 1) Screen oldScreen) {
        if (isSingleplayer()) {
            CommonEvents.onScreenChange(newScreen, oldScreen);
        }
    }

    @Unique
    @Override
    public boolean menupause$isSlowMotion() {
        return menupause$isSlowMotion;
    }

    @Unique
    @Override
    public void menupause$setSlowMotion(boolean state) {
        menupause$isSlowMotion = state;
    }

    @Unique
    @Override
    public float menupause$getOriginalTickRate() {
        return menupause$originalTickRate;
    }

    @Unique
    @Override
    public void menupause$setOriginalTickRate(float tickRate) {
        menupause$originalTickRate = tickRate;
    }
}
