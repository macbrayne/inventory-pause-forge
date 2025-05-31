// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.events;

import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.common.PauseMode;
import de.macbrayne.menupause.common.ScreenHelper;
import de.macbrayne.menupause.common.ScreenUnpause;
import de.macbrayne.menupause.config.ConfigHelper;
import de.macbrayne.menupause.gui.TickrateController;
import de.macbrayne.menupause.gui.screens.DummyPauseScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerTickRateManager;
import org.slf4j.Logger;

import static de.macbrayne.menupause.MenuPause.MOD_CONFIG;

public class CommonEvents {
    private static final Logger LOGGER = Constants.LOG;
    public static void copyClassNameAction(Screen screen) {
        var name = screen.getClass().getName();
        if(!Minecraft.getInstance().isSingleplayer()) {
            return;
        }

        if (Constants.SCREEN_DICTIONARY.handleScreen(screen.getClass()) != PauseMode.OFF) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.menupause.addToList.error.alreadyCovered"));
            return;
        }
        if (MOD_CONFIG.modCompat.customScreens.contains(name)) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.menupause.addToList.error.duplicate"));
            return;
        }
        if (screen.isPauseScreen()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.menupause.addToList.error.pausedScreen"));
            return;
        }
        MOD_CONFIG.modCompat.customScreens.add(name);
        MOD_CONFIG.save();
        Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.menupause.addToList.action"));
    }

    public static void pauseGameAction(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isSingleplayer() && !(screen instanceof DummyPauseScreen) && !(screen instanceof KeyBindsScreen) &&
                !(screen instanceof PauseScreen)) {
            if (!screen.isPauseScreen() && !ScreenHelper.isPauseScreen(screen)) {
                minecraft.setScreen(new DummyPauseScreen(screen));
            } else if (!ScreenHelper.isSlowmoScreen(screen)) {
                ((ScreenUnpause) screen).menupause$invertForceUnpause();
            }
        }
    }

    public static void onScreenChange(Screen newScreen, Screen oldScreen) {
        TickrateController controller = (TickrateController) Minecraft.getInstance();
        boolean isSlowmo = controller.menupause$isSlowMotion();
        float originalTickRate = controller.menupause$getOriginalTickRate();
        if (newScreen != oldScreen) {
            if (newScreen != null && ScreenHelper.isSlowmoScreen(newScreen) && !isSlowmo) {
                ServerTickRateManager servertickratemanager = Minecraft.getInstance().getSingleplayerServer().tickRateManager();
                float newTickRate = MOD_CONFIG.modCompat.slowmoTickSpeed;
                controller.menupause$setOriginalTickRate(servertickratemanager.tickrate());
                servertickratemanager.setTickRate(newTickRate);
                LOGGER.debug("Opening {} (slow-motion)", newScreen);
                LOGGER.debug("Setting tickrate to {}", newTickRate);
                controller.menupause$setSlowMotion(true);
            } else if ((newScreen == null || !ScreenHelper.isSlowmoScreen(newScreen)) && isSlowmo) {
                Minecraft.getInstance().getSingleplayerServer().tickRateManager().setTickRate(originalTickRate);
                LOGGER.debug("Opening {} (not slow-motion)", newScreen);
                LOGGER.debug("Resetting tickrate to {}", originalTickRate);
                ((BooleanConsumer) controller::menupause$setSlowMotion).accept(false);
            }
        }
        if (MOD_CONFIG.debugText.debug && newScreen != null && !ScreenHelper.isConfiguredScreen(newScreen)) {
            LOGGER.info("Changing screen to {}", newScreen.getClass().getName());
        }
    }

    public static void onGuiPostDraw(Screen screen, GuiGraphics guiGraphics) {
        if (MOD_CONFIG.debugText.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                if (!Screen.class.isAssignableFrom(cl) || cl == Screen.class) {
                    continue;
                }
                guiGraphics.drawString(Minecraft.getInstance().font, cl.getName(), (int) MOD_CONFIG.debugText.x, (int) (MOD_CONFIG.debugText.y + 10 * line), 0xffffffff);
                line++;
            }
        }
        if (((ScreenUnpause) screen).menupause$getForceUnpause()) {
            guiGraphics.drawString(Minecraft.getInstance().font, "Force unpaused...", (int) MOD_CONFIG.debugText.x, (int) (MOD_CONFIG.debugText.y), 0xffffffff);
        }
    }
}
