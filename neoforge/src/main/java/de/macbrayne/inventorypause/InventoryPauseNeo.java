// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

import de.macbrayne.inventorypause.events.ForgeEventBus;
import de.macbrayne.inventorypause.events.ModEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = "inventorypause", dist = Dist.CLIENT)
public class InventoryPauseNeo {
    public InventoryPauseNeo(IEventBus modEventBus) {
        CommonClass.init();
        InventoryPause.MOD_CONFIG.states.registerScreens();
        modEventBus.addListener(ModEventBus::clientSetup);
        modEventBus.addListener(ModEventBus::registerBindings);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onGUIDrawPost);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onScreenEvent);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onClientTick);
    }
}
