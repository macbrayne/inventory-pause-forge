// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerTickRateManager;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;
import static de.macbrayne.forge.inventorypause.InventoryPause.getScreenDictionary;

public class ForgeEventBus {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static boolean slowmo = false;
    public static float originalTickRate;

    public static void onOpenGUI(ScreenEvent.Opening event) {
        if(Minecraft.getInstance().isSingleplayer()) {
            if(InventoryPause.getScreenDictionary().handleScreen(event.getNewScreen().getClass()) == PauseMode.SLOWMO) {
                ServerTickRateManager servertickratemanager = Minecraft.getInstance().getSingleplayerServer().tickRateManager();
                originalTickRate = servertickratemanager.tickrate();
                servertickratemanager.setTickRate(20f / MOD_CONFIG.modCompat.timeBetweenCompatTicks);
                LOGGER.error("Setting tickrate to " + 20f / MOD_CONFIG.modCompat.timeBetweenCompatTicks);
                slowmo = true;
            }
        }
        if (MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getScreen())) {
            LOGGER.info(event.getScreen().getClass().getName());
        }
    }

    public static void onCloseGUI(ScreenEvent.Closing event) {
        if(slowmo) {
            Minecraft.getInstance().getSingleplayerServer().tickRateManager().setTickRate(originalTickRate);
            slowmo = false;
        }
    }

    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (MOD_CONFIG.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                if(!Screen.class.isAssignableFrom(cl) || cl == Screen.class) {
                    continue;
                }
                event.getGuiGraphics().drawString(event.getScreen().getMinecraft().font, cl.getName(), (int) MOD_CONFIG.debugText.x, (int) (MOD_CONFIG.debugText.y + 10 * line), 0xffffffff);
                line++;
            }
        }
    }

    public static void onScreenEvent(ScreenEvent.KeyReleased.Post event) {
        if(ModEventBus.COPY_CLASS_NAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            Screen screen = event.getScreen();
            var name = screen.getClass().getName();

            if(getScreenDictionary().handleScreen(screen.getClass()) != PauseMode.OFF) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.alreadyCovered"));
                return;
            }
            if(MOD_CONFIG.modCompat.customScreens.contains(name)) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.duplicate"));
                return;
            }
            if(screen.isPauseScreen()) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.pausedScreen"));
                return;
            }
            MOD_CONFIG.modCompat.customScreens.add(name);
            ConfigHelper.serialize();
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.action"));
        }
    }
}
