// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.inventorypause.events.CommonEvents;
import de.macbrayne.inventorypause.gui.screens.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class InventoryPauseFabric implements ClientModInitializer {
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
    
    @Override
    public void onInitializeClient() {
        CommonClass.init();
        KeyBindingHelper.registerKeyBinding(PAUSE_GAME);
        KeyBindingHelper.registerKeyBinding(COPY_CLASS_NAME);
        KeyBindingHelper.registerKeyBinding(OPEN_SETTINGS);
        ClientTickEvents.END_CLIENT_TICK.register(InventoryPauseFabric::endClientTick);
        ScreenEvents.BEFORE_INIT.register(InventoryPauseFabric::screenInit);
    }

    private static void screenInit(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        ScreenEvents.afterRender(screen).register((screen1, drawContext, mouseX, mouseY, tickDelta) -> {
            CommonEvents.onGuiPostDraw(screen1, drawContext);
        });
    }

    private static void endClientTick(Minecraft minecraft) {
        while (PAUSE_GAME.consumeClick()) {
            if(minecraft.screen != null) {
                CommonEvents.pauseGameAction(minecraft.screen);
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
}
