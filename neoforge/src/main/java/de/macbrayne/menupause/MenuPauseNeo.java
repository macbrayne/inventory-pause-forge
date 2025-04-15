// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause;

import de.macbrayne.menupause.datagen.MenuPauseDataGenerator;
import de.macbrayne.menupause.events.ForgeEventBus;
import de.macbrayne.menupause.events.ModEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class MenuPauseNeo {
    public MenuPauseNeo(IEventBus modEventBus) {
        MenuPause.init();
        modEventBus.addListener(ModEventBus::clientSetup);
        modEventBus.addListener(ModEventBus::registerBindings);
        modEventBus.addListener(MenuPauseDataGenerator::onInitializeDataGenerator);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onGUIDrawPost);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onScreenEvent);
        NeoForge.EVENT_BUS.addListener(ForgeEventBus::onClientTick);
    }
}
