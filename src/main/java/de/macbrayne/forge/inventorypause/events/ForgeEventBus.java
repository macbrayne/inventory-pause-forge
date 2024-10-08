// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.gui.screens.ConfigScreen;
import de.macbrayne.forge.inventorypause.gui.screens.DummyPauseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerTickRateManager;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;
import static de.macbrayne.forge.inventorypause.InventoryPause.getScreenDictionary;

public class ForgeEventBus {
    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (MOD_CONFIG.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                if (!Screen.class.isAssignableFrom(cl) || cl == Screen.class) {
                    continue;
                }
                event.getGuiGraphics().drawString(event.getScreen().getMinecraft().font, cl.getName(), (int) MOD_CONFIG.debugText.x, (int) (MOD_CONFIG.debugText.y + 10 * line), 0xffffffff);
                line++;
            }
        }
    }

    public static void onScreenEvent(ScreenEvent.KeyReleased.Post event) {
        if(!MOD_CONFIG.isEnabled()) {
            return;
        }
        if (ModEventBus.COPY_CLASS_NAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            Screen screen = event.getScreen();
            var name = screen.getClass().getName();

            if (getScreenDictionary().handleScreen(screen.getClass()) != PauseMode.OFF) {
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
        if (ModEventBus.PAUSE_GAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.isSingleplayer() && !ScreenHelper.isPauseScreen(minecraft.screen) && !minecraft.screen.isPauseScreen() &&
                    !(minecraft.screen instanceof DummyPauseScreen) && !(minecraft.screen instanceof KeyBindsScreen)) {
                minecraft.pushGuiLayer(new DummyPauseScreen());
            }
        }
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if(!minecraft.isSingleplayer()) {
            return;
        }
        while (ModEventBus.OPEN_SETTINGS.get().consumeClick()) {
            minecraft.setScreen(new ConfigScreen(minecraft.screen));
        }

        if(!MOD_CONFIG.isEnabled()) {
            return;
        }
        while (ModEventBus.PAUSE_GAME.get().consumeClick()) {
            if (!(minecraft.screen instanceof DummyPauseScreen)) {
                minecraft.setScreen(new DummyPauseScreen());
            }
        }
    }
}
