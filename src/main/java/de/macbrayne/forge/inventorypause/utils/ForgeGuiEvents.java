package de.macbrayne.forge.inventorypause.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
    public static void onOpenGUI(ScreenEvent.Opening event) {
        if (MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getScreen())) {
            LOGGER.info(event.getScreen().getClass().getName());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGUIDrawPost(ScreenEvent.Render.Post event) {
        if (MOD_CONFIG.debug) {
            Screen screen = event.getScreen();

            int line = 0;
            for (Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                Minecraft.getInstance().font.drawShadow(new PoseStack(), cl.getName(), MOD_CONFIG.debugText.x, MOD_CONFIG.debugText.y + 10 * line, 0xffffffff);
                line++;
            }
        }
    }
}
