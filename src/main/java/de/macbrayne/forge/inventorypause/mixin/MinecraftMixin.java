// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.server.ServerTickRateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Unique private static final Logger inventorypause$LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    @Unique private boolean inventorypause$isSlowMotion = false;
    @Unique private float inventorypause$originalTickRate = 20f;
    @Shadow
    public abstract SoundManager getSoundManager();

    @Shadow
    public abstract boolean isLocalServer();

    @Shadow private @Nullable IntegratedServer singleplayerServer;

    @Shadow public abstract boolean isSingleplayer();

    @Shadow @Nullable public abstract IntegratedServer getSingleplayerServer();

    @Inject(at = @At("TAIL"), method = "setScreen")
    public void openScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (MOD_CONFIG.isEnabled() && MOD_CONFIG.pauseSounds && ScreenHelper.isConfiguredScreen(screen)) {
            boolean canPauseGame = isLocalServer() && !this.singleplayerServer.isPublished();
            if (canPauseGame) {
                this.getSoundManager().pause();
            }
        }
    }

    @WrapOperation(method = "runTick(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;isPauseScreen()Z"))
    private boolean pauseGame(Screen instance, Operation<Boolean> original) {
        if (MOD_CONFIG.isEnabled() && ScreenHelper.isPauseScreen(instance)) {
            return true;
        }
        return original.call(instance);
    }

    @Inject(method = "setScreen", at = @At(value = "RETURN"))
    private void setAndInitialisedScreen(Screen newScreen, CallbackInfo ci, @Local(ordinal = 1) Screen oldScreen) {
        boolean isSlowmo = inventorypause$isSlowMotion;
        if (MOD_CONFIG.isEnabled() && this.isSingleplayer() && newScreen != oldScreen) {
            if (newScreen != null && ScreenHelper.isSlowmoScreen(newScreen) && !isSlowmo) {
                ServerTickRateManager servertickratemanager = getSingleplayerServer().tickRateManager();
                float newTickRate = 20f / MOD_CONFIG.modCompat.timeBetweenCompatTicks;
                inventorypause$originalTickRate = servertickratemanager.tickrate();
                servertickratemanager.setTickRate(newTickRate);
                inventorypause$LOGGER.debug("Opening {} (slow-motion)", newScreen);
                inventorypause$LOGGER.debug("Setting tickrate to {}", newTickRate);
                inventorypause$isSlowMotion = true;
            } else if ((newScreen == null || !ScreenHelper.isSlowmoScreen(newScreen)) && isSlowmo) {
                getSingleplayerServer().tickRateManager().setTickRate(inventorypause$originalTickRate);
                inventorypause$LOGGER.debug("Opening {} (not slow-motion)", newScreen);
                inventorypause$LOGGER.debug("Resetting tickrate to {}", inventorypause$originalTickRate);
                inventorypause$isSlowMotion = false;
            }
        }
        if (MOD_CONFIG.debug && newScreen != null && !ScreenHelper.isConfiguredScreen(newScreen)) {
            inventorypause$LOGGER.info("Changing screen to {}", newScreen.getClass().getName());
        }
    }
}
