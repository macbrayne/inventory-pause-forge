// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.events;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import de.macbrayne.forge.inventorypause.gui.screens.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;
import static de.macbrayne.forge.inventorypause.InventoryPause.getScreenDictionary;

public class ForgeEventBus {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static int timeUntilCompatTick = MOD_CONFIG.modCompat.timeBetweenCompatTicks;

    public static void onOpenGUI(ScreenEvent.Opening event) {
        if (MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getScreen())) {
            LOGGER.info(event.getScreen().getClass().getName());
        }
    }

    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (MOD_CONFIG.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                Minecraft.getInstance().font.drawShadow(new PoseStack(), cl.getName(), MOD_CONFIG.debugText.x, MOD_CONFIG.debugText.y + 10 * line, 0xffffffff);
                line++;
            }
        }
    }

    public static void onScreenEvent(ScreenEvent.KeyReleased.Post event) {
        if(ModEventBus.COPY_CLASS_NAME.get().isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            Screen screen = event.getScreen();
            var name = screen.getClass().getName();

            if(getScreenDictionary().handleScreen(screen.getClass())) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.alreadyCovered"));
                return;
            }
            if(MOD_CONFIG.modCompat.customScreens.contains(name)) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.error.duplicate"));
                return;
            }
            MOD_CONFIG.modCompat.customScreens.add(name);
            ConfigHelper.serialize();
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.action"));
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            if (timeUntilCompatTick > 0 &&
                    --timeUntilCompatTick == 0) {
                timeUntilCompatTick = MOD_CONFIG.modCompat.timeBetweenCompatTicks;
            }
            while (ModEventBus.OPEN_SETTINGS.get().consumeClick()) {
                Minecraft.getInstance().setScreen(new ConfigScreen(Minecraft.getInstance().screen));
            }
        }
    }
}
