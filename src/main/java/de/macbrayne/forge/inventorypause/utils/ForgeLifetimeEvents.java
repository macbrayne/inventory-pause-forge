// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.compat.VanillaCompat;
import de.macbrayne.forge.inventorypause.gui.screens.ConfigScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeLifetimeEvents {

    public static void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        // ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> AutoConfig.getConfigScreen(ModConfig.class, screen).get()));
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(screen)));
        new VanillaCompat().register();
    }
}
