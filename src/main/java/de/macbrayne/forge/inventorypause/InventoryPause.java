package de.macbrayne.forge.inventorypause;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.macbrayne.forge.inventorypause.utils.ForgeConfigHelper;
import de.macbrayne.forge.inventorypause.utils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        MOD_CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeConfigHelper::registerConfig);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGUIDrawPost(GuiScreenEvent.DrawScreenEvent.Post event){
        if(MOD_CONFIG.debug) {
            Screen screen = event.getGui();

            int line = 0;
            for(Class cl = screen.getClass(); cl.getSuperclass() != null; cl = cl.getSuperclass()) {
                Minecraft.getInstance().fontRenderer.drawStringWithShadow(new MatrixStack(), cl.getName(), 1f, 1f + 10 * line, 0xffffffff);
                line++;
            }
        }
    }
}
