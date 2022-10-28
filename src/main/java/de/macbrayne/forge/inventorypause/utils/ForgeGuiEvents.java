package de.macbrayne.forge.inventorypause.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.macbrayne.forge.inventorypause.common.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.macbrayne.forge.inventorypause.InventoryPause.MOD_CONFIG;

@Mod.EventBusSubscriber(modid = "inventorypause", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeGuiEvents {
    private static final Logger LOGGER = LogManager.getLogger("inventorypause");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onOpenGUI(GuiScreenEvent.DrawScreenEvent.InitGuiEvent.Pre event) {
        if(MOD_CONFIG.debug && !ScreenHelper.isConfiguredScreen(event.getGui())) {
            LOGGER.info(event.getGui().getClass().getName());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGUIDrawPost(GuiScreenEvent.DrawScreenEvent.Post event){
        Screen screen = event.getGui();
        while (ForgeLifetimeEvents.COPY_CLASS_NAME.get().consumeClick()) {
            String name = screen.getClass().getName();
            Minecraft.getInstance().keyboardHandler.setClipboard(name);
            Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("chat.inventorypause.copyClassName.action", name), Util.NIL_UUID);
        }
        if(MOD_CONFIG.debug) {
            int line = 0;
            for(Class<?> cl = screen.getClass(); cl.getSuperclass() != null && line < MOD_CONFIG.debugText.maxDepth; cl = cl.getSuperclass()) {
                Minecraft.getInstance().font.drawShadow(new MatrixStack(), cl.getName(), MOD_CONFIG.debugText.x, MOD_CONFIG.debugText.y + 10 * line, 0xffffffff);
                line++;
            }
        }
    }
}
