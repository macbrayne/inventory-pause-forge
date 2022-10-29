package de.macbrayne.forge.inventorypause.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeGuiEvents {
    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onOpenGUI(ScreenEvent.DrawScreenEvent.InitScreenEvent.Pre event) {
        if (MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getScreen())) {
            LOGGER.info(event.getScreen().getClass().getName());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGUIDrawPost(ScreenEvent.DrawScreenEvent.Post event) {
        Screen screen = event.getScreen();
        while (ForgeLifetimeEvents.COPY_CLASS_NAME.get().consumeClick()) {
            var name = screen.getClass().getName();
            Minecraft.getInstance().keyboardHandler.setClipboard(name);
            Minecraft.getInstance().player.sendMessage(new TranslatableComponent("chat.inventorypause.copyClassName.action", name), Util.NIL_UUID);
        }
        if (MOD_CONFIG.debug) {
            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                Minecraft.getInstance().font.drawShadow(new PoseStack(), cl.getName(), MOD_CONFIG.debugText.x, MOD_CONFIG.debugText.y + 10 * line, 0xffffffff);
                line++;
            }
        }
    }
}
