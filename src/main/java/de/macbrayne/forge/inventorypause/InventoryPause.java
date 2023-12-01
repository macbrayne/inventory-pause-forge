// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause;

import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;
import de.macbrayne.forge.inventorypause.events.ForgeEventBus;
import de.macbrayne.forge.inventorypause.events.ModEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static final String MOD_ID = "inventorypause";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final ScreenDictionary SCREEN_DICTIONARY = new ScreenDictionary();
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MOD_CONFIG = ConfigHelper.deserialize();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEventBus::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEventBus::registerBindings);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEventBus::clientReload);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onOpenGUI);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onGUIDrawPost);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onClientTick);
        } else {
            LOGGER.error("Not on client, disabling mod");
        }
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    public static ScreenDictionary getScreenDictionary() {
        return SCREEN_DICTIONARY;
    }
}
