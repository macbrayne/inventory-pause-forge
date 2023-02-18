// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause;

import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.utils.ForgeGuiEvents;
import de.macbrayne.forge.inventorypause.utils.ForgeLifetimeEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static final Logger LOGGER = LogManager.getLogger("inventorypause");
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MOD_CONFIG = ConfigHelper.deserialize();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ForgeLifetimeEvents::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ForgeLifetimeEvents::registerBindings);
            MinecraftForge.EVENT_BUS.addListener(ForgeGuiEvents::onOpenGUI);
            MinecraftForge.EVENT_BUS.addListener(ForgeGuiEvents::onGUIDrawPost);
            MinecraftForge.EVENT_BUS.addListener(ForgeGuiEvents::onClientTick);
        } else {
            LOGGER.error("Not on client, disabling mod");
        }
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
