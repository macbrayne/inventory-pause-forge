// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.events;

import de.macbrayne.inventorypause.Constants;
import de.macbrayne.inventorypause.common.PauseMode;
import de.macbrayne.inventorypause.common.ScreenHelper;
import de.macbrayne.inventorypause.config.ConfigHelper;
import de.macbrayne.inventorypause.gui.screens.DummyPauseScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerTickRateManager;
import org.slf4j.Logger;

import static de.macbrayne.inventorypause.InventoryPause.MOD_CONFIG;

public class CommonEvents {
    private static final Logger LOGGER = Constants.LOG;
    public static void copyClassNameAction(Screen screen) {
        var name = screen.getClass().getName();
        if(!Minecraft.getInstance().isSingleplayer()) {
            return;
        }

        if (Constants.SCREEN_DICTIONARY.handleScreen(screen.getClass()) != PauseMode.OFF) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.alreadyCovered"));
            return;
        }
        if (MOD_CONFIG.modCompat.customScreens.contains(name)) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.duplicate"));
            return;
        }
        if (screen.isPauseScreen()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.pausedScreen"));
            return;
        }
        MOD_CONFIG.modCompat.customScreens.add(name);
        ConfigHelper.serialize();
        Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.action"));
    }

    public static void pauseGameAction(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isSingleplayer() && !ScreenHelper.isPauseScreen(screen) && !screen.isPauseScreen() &&
                !(screen instanceof DummyPauseScreen) && !(screen instanceof KeyBindsScreen)) {
            minecraft.setScreen(new DummyPauseScreen(screen));
        }
    }

    public static void onScreenChange(Screen newScreen, Screen oldScreen, boolean isSlowmo, BooleanConsumer slowmoSetter, float originalTickRate, FloatConsumer originalTickRateSetter) {
        if (newScreen != oldScreen) {
            if (newScreen != null && ScreenHelper.isSlowmoScreen(newScreen) && !isSlowmo) {
                ServerTickRateManager servertickratemanager = Minecraft.getInstance().getSingleplayerServer().tickRateManager();
                float newTickRate = MOD_CONFIG.modCompat.slowmoTickSpeed;
                originalTickRateSetter.accept(servertickratemanager.tickrate());
                servertickratemanager.setTickRate(newTickRate);
                LOGGER.debug("Opening {} (slow-motion)", newScreen);
                LOGGER.debug("Setting tickrate to {}", newTickRate);
                slowmoSetter.accept(true);
            } else if ((newScreen == null || !ScreenHelper.isSlowmoScreen(newScreen)) && isSlowmo) {
                Minecraft.getInstance().getSingleplayerServer().tickRateManager().setTickRate(originalTickRate);
                LOGGER.debug("Opening {} (not slow-motion)", newScreen);
                LOGGER.debug("Resetting tickrate to {}", originalTickRate);
                slowmoSetter.accept(false);
            }
        }
        if (MOD_CONFIG.debugText.debug && newScreen != null && !ScreenHelper.isConfiguredScreen(newScreen)) {
            LOGGER.info("Changing screen to {}", newScreen.getClass().getName());
        }
    }
}
