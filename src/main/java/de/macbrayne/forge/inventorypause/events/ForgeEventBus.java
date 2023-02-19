// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.events;

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
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;

public class ForgeEventBus {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    public static int timeUntilCompatTick = MOD_CONFIG.modCompat.timeBetweenCompatTicks;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onOpenGUI(ScreenEvent.Opening event) {
        if (MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getScreen())) {
            LOGGER.info(event.getScreen().getClass().getName());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        while (ModEventBus.COPY_CLASS_NAME.get().consumeClick()) {
            var name = screen.getClass().getName();
            if(!MOD_CONFIG.modCompat.customScreens.contains(name)) {
                MOD_CONFIG.modCompat.customScreens.add(name);
                ConfigHelper.serialize();
            }
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chat.inventorypause.addToList.action", name));
        }
        if (MOD_CONFIG.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                Minecraft.getInstance().font.drawShadow(new PoseStack(), cl.getName(), MOD_CONFIG.debugText.x, MOD_CONFIG.debugText.y + 10 * line, 0xffffffff);
                line++;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
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
