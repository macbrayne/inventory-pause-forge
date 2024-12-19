// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

import de.macbrayne.inventorypause.config.ConfigHelper;
import de.macbrayne.inventorypause.config.GuiEntries;
import de.macbrayne.inventorypause.config.ModConfig;
import de.macbrayne.inventorypause.events.ForgeEventBus;
import de.macbrayne.inventorypause.events.ModEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static final String MOD_ID = "inventorypause";
    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static GuiEntries GUI_ENTRIES = new GuiEntries();
    public static ModConfig MOD_CONFIG = ModConfig.getDefault();

    public InventoryPause(IEventBus modEventBus, ModContainer container) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CommonClass.init();
            ConfigHelper.ensureConfigDirExists();
            GUI_ENTRIES = GuiEntries.loadEntries(container);
            ConfigHelper.migrateConfigToJson();
            MOD_CONFIG = ModConfig.load();
            MOD_CONFIG.states.registerScreens();
            modEventBus.addListener(ModEventBus::clientSetup);
            modEventBus.addListener(ModEventBus::registerBindings);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onGUIDrawPost);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onScreenEvent);
            NeoForge.EVENT_BUS.addListener(ForgeEventBus::onClientTick);
        } else {
            LOGGER.error("Not on client, disabling mod");
        }
    }
}
