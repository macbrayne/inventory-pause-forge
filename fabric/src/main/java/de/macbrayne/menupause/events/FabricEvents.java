// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.MenuPause;
import de.macbrayne.menupause.gui.screens.ConfigScreen;
import de.macbrayne.menupause.gui.screens.DummyPauseScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class FabricEvents {

    public static final KeyMapping PAUSE_GAME = new KeyMapping(
            "key.inventorypause.pauseGame", // Localisation
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    );

    public static final KeyMapping COPY_CLASS_NAME = new KeyMapping(
            "key.inventorypause.addToList", // Localisation
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    );

    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
            "key.inventorypause.openSettings", // Localisation
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    );

    public static void screenInit(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        ScreenEvents.afterRender(screen).register((screen1, drawContext, mouseX, mouseY, tickDelta) -> {
            CommonEvents.onGuiPostDraw(screen1, drawContext);
        });
    }

    public static void endClientTick(Minecraft minecraft) {
        while (PAUSE_GAME.consumeClick()) {
            if(minecraft.screen != null) {
                CommonEvents.pauseGameAction(minecraft.screen);
            } else {
                minecraft.setScreen(new DummyPauseScreen(null));
            }
        }

        while (COPY_CLASS_NAME.consumeClick()) {
            if(minecraft.screen != null) {
                CommonEvents.copyClassNameAction(minecraft.screen);
            }
        }

        while (OPEN_SETTINGS.consumeClick() && minecraft.screen == null) {
            minecraft.setScreen(new ConfigScreen(null));
        }
    }

    public static void registerKeybindings() {
        if (MenuPause.MOD_CONFIG.settingsForModpacks.registerKeybinds) {
            Constants.LOG.info("Registering key mappings");
            KeyBindingHelper.registerKeyBinding(FabricEvents.PAUSE_GAME);
            KeyBindingHelper.registerKeyBinding(FabricEvents.COPY_CLASS_NAME);
            KeyBindingHelper.registerKeyBinding(FabricEvents.OPEN_SETTINGS);
        }
    }
}
