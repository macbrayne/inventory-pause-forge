// SPDX-License-Identifier: EUPL-1.2 OR MIT

package de.macbrayne.forge.inventorypause.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract SoundManager getSoundManager();
    @Shadow public abstract boolean isLocalServer();
    @Shadow private @Nullable IntegratedServer singleplayerServer;

    @Inject(at = @At("TAIL"), method = "setScreen")
    public void openScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (InventoryPause.MOD_CONFIG.enabled && InventoryPause.MOD_CONFIG.pauseSounds && ScreenHelper.isConfiguredScreen(screen)) {
            boolean canPauseGame = isLocalServer() && !this.singleplayerServer.isPublished();
            if(canPauseGame) {
                this.getSoundManager().pause();
            }
        }
    }

    @WrapOperation(method = "runTick(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;isPauseScreen()Z"))
    private boolean pauseGame(Screen instance, Operation<Boolean> original) {
        if(InventoryPause.MOD_CONFIG.enabled && ScreenHelper.isPauseScreen(instance)) {
            return true;
        }
        return original.call(instance);
    }
}
