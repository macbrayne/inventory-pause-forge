package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract SoundHandler getSoundHandler();
    @Shadow public abstract boolean isIntegratedServerRunning();
    @Shadow private @Nullable IntegratedServer integratedServer;

    @Inject(at = @At("TAIL"), method = "displayGuiScreen")
    public void openScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (ScreenHelper.isConfiguredScreen(screen))
        {
            boolean canPauseGame = isIntegratedServerRunning() && !this.integratedServer.getPublic();
            if(canPauseGame) {
                this.getSoundHandler().pause();
            }
        }
    }

}
