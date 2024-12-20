// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.inventorypause.gui.screens.ConfigScreen;
import de.macbrayne.inventorypause.gui.screens.DummyPauseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

import static de.macbrayne.inventorypause.InventoryPause.MOD_CONFIG;

public class ForgeEventBus {
    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (MOD_CONFIG.debugText.debug) {
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
        if (ModEventBus.COPY_CLASS_NAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            CommonEvents.copyClassNameAction(event.getScreen());
        }
        if (ModEventBus.PAUSE_GAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            CommonEvents.pauseGameAction(event.getScreen());
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
        while (ModEventBus.PAUSE_GAME.get().consumeClick()) {
            if (!(minecraft.screen instanceof DummyPauseScreen)) {
                minecraft.setScreen(new DummyPauseScreen(minecraft.screen));
            }
        }
    }
}
