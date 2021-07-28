package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.compat.mod.*;
import de.macbrayne.forge.inventorypause.compat.mod.custom.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeLifetimeEvents {

    public static void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get());

        if (ModList.get().isLoaded("waystones")) {
            new WaystonesCompatGen().register();
        }
        if (ModList.get().isLoaded("ironchest")) {
            new IronchestCompat().register();
        }
        if (ModList.get().isLoaded("appliedenergistics2")) {
            new AppliedEnergistics2CompatGen().register();
        }
        if (ModList.get().isLoaded("twilightforest")) {
            new TheTwilightForestCompat().register();
        }
        if (ModList.get().isLoaded("botania")) {
            new BotaniaCompat().register();
        }
        if (ModList.get().isLoaded("curios")) {
            new CuriosCompat().register();
        }
        if (ModList.get().isLoaded("mekanism")) {
            new MekanismCompatGen().register();
        }
        if (ModList.get().isLoaded("refinedstorage")) {
            new RefinedStorageCompatGen().register();
        }
        if (ModList.get().isLoaded("titanium")) {
            new TitaniumCompat().register();
        }
        if (ModList.get().isLoaded("industrialforegoing")) {
            new IndustrialForegoingCompatGen().register();
        }
        if (ModList.get().isLoaded("projecte")) {
            new ProjectECompatGen().register();
        }
    }
}
