package de.macbrayne.forge.inventorypause.mixin;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.utils.CompatTick;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract SoundManager getSoundManager();
    @Shadow public abstract boolean isLocalServer();
    @Shadow private @Nullable IntegratedServer singleplayerServer;
    @Unique private static ModConfig config = null;

    @Inject(at = @At("TAIL"), method = "setScreen")
    public void openScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (ScreenHelper.isConfiguredScreen(screen)) {
            boolean canPauseGame = isLocalServer() && !this.singleplayerServer.isPublished();
            if(canPauseGame) {
                this.getSoundManager().pause();
            }
        }
    }

    @Unique
    private static ModConfig getConfig() {
        if(config == null) {
            config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        }
        return config;
    }
}
