// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.inventorypause.gui.screens.ConfigScreen;
import de.macbrayne.inventorypause.gui.screens.DummyPauseScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class ForgeEventBus {
    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        CommonEvents.onGuiPostDraw(event.getScreen(), event.getGuiGraphics());
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
            if (minecraft.screen == null) {
                minecraft.setScreen(new DummyPauseScreen(null));
            }
        }
    }
}
